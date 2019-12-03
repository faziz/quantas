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

    //Airport Json document we are going to be  parsing over.
    @Autowired
    private DocumentContext context;

    /**
     * @param parameters Parameters contain meta-data for the attributes we want to filter on in Json data.
     * @param uniqueKey Used or caching the results.
     * @return All airports matching the parameters.
     */
    @Cacheable(key = "#uniqueKey")
    public Airports getAirports(final Map<String, String[]> parameters, Integer uniqueKey) {

        Filter[] filters = filters(parameters);
        List airports = filters.length == 0 ? 
            context.read("$.airports[*]", List.class):
                context.read("$.airports[?]", List.class, filters);
        return new Airports(airports);
    }

    /**
     * @param parameters parameter key maps to attribute name in the json data.
     * @return prepares an array of filters based on provided parameters.
     */
    private Filter[] filters(final Map<String, String[]> parameters) {
        return parameters.
            entrySet().stream().
                map(e -> filter(newFilter(e))).
                    toArray(Filter[]::new);
    }

    /**
     * @param entry Entry describes a single filter meta-data.
     * @return 
     */
    private static Criteria newFilter(Entry<String, String[]> entry) {

        String attributeName = entry.getKey();
        String[] value = entry.getValue();
        String firstValue = value[0];

        //Prepares data filter based on attribute types. 
        //
        //Boolean data like international_airport and regional_airport and also numeric data like location has to be
        //treated differently than the text based attributes.
        //
        return asList("international_airport", "regional_airport").contains(attributeName) ? 
            //Transform the value to Boolean type for comparision.
            where(attributeName).is(parseBoolean(firstValue)): 
                asList("location.latitude", "location.longitude").contains(attributeName) ?
                    //Transform the value to Double type for comparision.
                    where(attributeName).is(valueOf(firstValue)) : 
                        //It's text so no need for transformation.
                        where(attributeName).in(value);
    }
}
