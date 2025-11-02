package com.spacex.ai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Rocket(
    String id,
    String name,
    String type,
    Boolean active,
    Integer stages,
    Integer boosters,
    @JsonProperty("cost_per_launch") Long costPerLaunch,
    @JsonProperty("success_rate_pct") Integer successRatePct,
    @JsonProperty("first_flight") String firstFlight,
    String country,
    String company,
    String wikipedia,
    String description,
    Height height,
    Diameter diameter,
    Mass mass,
    List<Engine> engines
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Height(
        Double meters,
        Double feet
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Diameter(
        Double meters,
        Double feet
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Mass(
        Integer kg,
        Integer lb
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Engine(
        String type,
        String version,
        Integer number
    ) {}
}
