package com.faziz.quantas.challenge.service;

import com.faziz.quantas.challenge.model.Airports;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Filter;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "airports")
public class AirportLocationService {

    //Airport Json document we are going to be  parsing over.
    @Autowired
    private DocumentContext context;

    @Autowired
    private FilterBuilder filterBuilder;

    /**
     * @param parameters Parameters contain meta-data for the attributes we want to filter on in Json data.
     * @param uniqueKey Used or caching the results.
     * @return All airports matching the parameters.
     */
    @Cacheable(key = "#uniqueKey")
    public Airports getAirports(final Map<String, String[]> parameters, Integer uniqueKey) {

        Filter[] filters = filterBuilder.build(parameters);
        List airports = filters.length == 0 ? 
            context.read("$.airports[*]", List.class):
                context.read("$.airports[?]", List.class, filters);
        return new Airports(airports);
    }
}
