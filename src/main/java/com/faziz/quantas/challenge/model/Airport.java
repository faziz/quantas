package com.faziz.quantas.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {
    @JsonProperty("code")
    private String code;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("international_airport")
    private Boolean internationalAirport;
    @JsonProperty("regional_airport")
    private Boolean regionalAirport;
    @JsonProperty("location")
    private Coordinate location;
    @JsonProperty("currency_code")
    private String currencyCode;
    @JsonProperty("timeZone")
    private String timeZone;
    @JsonProperty("country")
    private Country country;
}