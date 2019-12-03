package com.faziz.quantas.challenge.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Airports {

    public Airports() {
    }

    public Airports(List<Airport> airports) {
        this.airports = new ArrayList<>(airports);
    }
    
    private List<Airport> airports;
}
