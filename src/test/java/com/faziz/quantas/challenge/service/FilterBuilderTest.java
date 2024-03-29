package com.faziz.quantas.challenge.service;

import com.jayway.jsonpath.Filter;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FilterBuilderTest {

    /**
     * Test of build method, of class FilterBuilder.
     */
    @Test
    public void testBuildfilters() {
        FilterBuilder builder = new FilterBuilder();
        Filter[] filters = builder.build(new HashMap<>());
        assertTrue(filters.length == 0);

        Map<String, String[]> parameters = new HashMap<>();
        parameters.put("test", new String[]{"123"});
        filters = builder.build(parameters);
        assertTrue(filters.length == 1);
    }
    
}
