package com.faziz.quantas.challenge.service;

import com.faziz.quantas.challenge.AirportLocationApplicationConfiguration;
import com.faziz.quantas.challenge.model.Airport;
import com.faziz.quantas.challenge.model.Airports;
import com.jayway.jsonpath.DocumentContext;
import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = { AirportLocationApplicationConfiguration.class })
public class AirportLocationServiceTest {
    
    @Mock
    private DocumentContext documentContext;
    @InjectMocks
    private AirportLocationService airportLocationService;
    
    @Test
    public void testGetAirports() {
        Airport airport1 = newAirport("BZD", "AUD");
        Airport airport2 = newAirport("BZE", "AUD");
        List<Airport> airports = asList(airport1, airport2);

        when(documentContext.read(anyString(), any(Class.class))).thenReturn(asList());
        when(documentContext.read(anyString(), any(Class.class), any())).
            thenReturn(airports);

        Map<String, String[]> parameters = new HashMap();
        parameters.put("code", new String[] {"CHB"});
        Integer uniqueKey = -131241;

        final Airports result = airportLocationService.getAirports(parameters, uniqueKey);
        final Airports expResult = new Airports(asList(airport1, airport2));

        assertAll("Lists are not comparable", 
            () -> assertEquals(expResult.getAirports().get(0).getCode(), result.getAirports().get(0).getCode()),
            () -> assertEquals(expResult.getAirports().get(0).getCurrencyCode(), result.getAirports().get(0).getCurrencyCode()),
            () -> assertEquals(expResult.getAirports().get(1).getCode(), result.getAirports().get(1).getCode()),
            () -> assertEquals(expResult.getAirports().get(1).getCurrencyCode(), result.getAirports().get(1).getCurrencyCode())
        );
    }
    
    private Airport newAirport(String code, String currency) {
        Airport airport = new Airport();
        airport.setCode(code);
        airport.setCurrencyCode(currency);
        return airport;
    }
}
