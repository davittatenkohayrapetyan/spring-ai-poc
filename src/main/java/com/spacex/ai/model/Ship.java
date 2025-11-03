package com.spacex.ai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Ship(
    String id,
    String name,
    String type,
    Boolean active,
    @JsonProperty("home_port") String homePort,
    String link,
    String image,
    List<String> launches,
    @JsonProperty("year_built") Integer yearBuilt,
    @JsonProperty("mass_kg") Integer massKg,
    @JsonProperty("class") Integer shipClass,
    String model
) {}
