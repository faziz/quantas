package com.faziz.quantas.challenge.web;

import com.faziz.quantas.challenge.model.Airport;
import com.faziz.quantas.challenge.service.AirportLocationService;
import com.jayway.jsonpath.DocumentContext;
import static java.util.Arrays.asList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AirportsListingControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @Mock
    private DocumentContext documentContext;

    @InjectMocks
    private AirportLocationService airportLocationService;

    /**
     * Test of getAirports method, of class AirportsListingController.
     */
    @Test
    public void testGetAirports() throws Exception {
        Airport airport1 = newAirport("BZD", "AUD");
        Airport airport2 = newAirport("BZE", "AUD");
        List<Airport> airports = asList(airport1, airport2);

        when(documentContext.read(anyString(), any(Class.class))).thenReturn(asList());
        when(documentContext.read(anyString(), any(Class.class), any())).
            thenReturn(airports);

        mvc.perform(get("/api/airports/search?code={code}", "BZD").
            contentType(APPLICATION_JSON)).
                andExpect(status().isOk()).
                    andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON)).
                        andExpect(jsonPath("airports.[0].code", is(airport1.getCode()))).
                            andExpect(jsonPath("airports.[0].currency_code", is(airport1.getCurrencyCode())));
        
        mvc.perform(get("/api/airports/search").
            contentType(APPLICATION_JSON)).
                andExpect(status().isOk()).
                    andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON)).
                        andExpect(jsonPath("airports.[0].code", is(airport1.getCode()))).
                            andExpect(jsonPath("airports.[0].currency_code", is(airport1.getCurrencyCode())));
    }

    private Airport newAirport(String code, String currency) {
        Airport airport = new Airport();
        airport.setCode(code);
        airport.setCurrencyCode(currency);
        return airport;
    }
}
