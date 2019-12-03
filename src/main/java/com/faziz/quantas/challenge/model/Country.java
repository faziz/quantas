package com.faziz.quantas.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Country {
    @JsonProperty("code")
    private String code;
    @JsonProperty("display_name")
    private String displayName;
}