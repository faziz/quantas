package com.faziz.quantas.challenge.service;

import com.jayway.jsonpath.Criteria;
import static com.jayway.jsonpath.Criteria.where;
import com.jayway.jsonpath.Filter;
import static com.jayway.jsonpath.Filter.filter;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.valueOf;
import static java.util.Arrays.asList;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class FilterBuilder {
    
    /**
     * @param parameters parameter key maps to attribute name in the json data.
     * @return prepares an array of filters based on provided parameters.
     */
    public Filter[] buildfilters(final Map<String, String[]> parameters) {
        return parameters.
            entrySet().stream().
                map(e -> filter(newFilter(e))).
                    toArray(Filter[]::new);
    }

    /**
     * @param entry Entry describes a single filter meta-data.
     * @return 
     */
    private Criteria newFilter(Map.Entry<String, String[]> entry) {

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
