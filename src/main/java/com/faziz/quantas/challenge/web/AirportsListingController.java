package com.faziz.quantas.challenge.web;

import com.faziz.quantas.challenge.model.Airports;
import com.faziz.quantas.challenge.service.AirportLocationService;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AirportsListingController {

    @Autowired
    private AirportLocationService airportLocationService;

    @GetMapping("/api/airports/search")
    public Airports getAirports(HttpServletRequest request) 
            throws IOException {

        int uniqueKey = request.getQueryString().toLowerCase().hashCode();
        Map<String, String[]> parameters = request.getParameterMap();
        return airportLocationService.getAirports(parameters, uniqueKey);
    }
}
