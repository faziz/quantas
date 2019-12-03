package com.faziz.quantas.challenge.service;

import com.faziz.quantas.challenge.model.Airports;
import com.jayway.jsonpath.Criteria;
import static com.jayway.jsonpath.Criteria.*;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Filter;
import static com.jayway.jsonpath.Filter.*;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.valueOf;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "airports")
public class AirportLocationService {
    
    @Autowired
    private DocumentContext context;

    @Cacheable(key = "#uniqueKey")
    public Airports getAirports(final Map<String, String[]> parameters, Integer uniqueKey) {
        Filter[] filters = filters(parameters);
        List airports = filters.length == 0 ? 
            context.read("$.airports[*]", List.class):
                context.read("$.airports[?]", List.class, filters);
        return new Airports(airports);
    }

    private Filter[] filters(final Map<String, String[]> parameters) {
        return parameters.
            entrySet().stream().
                map(e -> filter(newFilter(e, parameters))).
                    toArray(Filter[]::new);
    }

    private static Criteria newFilter(Entry<String, String[]> entry, 
            Map<String, String[]> parameters) {

        return asList("international_airport", "regional_airport").contains(entry.getKey()) ? 
            where(entry.getKey()).is(parseBoolean(parameters.get(entry.getKey())[0])): 
                asList("location.latitude", "location.longitude").contains(entry.getKey()) ?
                    where(entry.getKey()).is(valueOf(parameters.get(entry.getKey())[0])) : 
                        where(entry.getKey()).in(parameters.get(entry.getKey()));
    }
}
