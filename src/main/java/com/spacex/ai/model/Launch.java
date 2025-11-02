package com.spacex.ai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Launch(
    String id,
    String name,
    @JsonProperty("flight_number") Integer flightNumber,
    @JsonProperty("date_utc") LocalDateTime dateUtc,
    @JsonProperty("date_local") String dateLocal,
    Boolean upcoming,
    Boolean success,
    String details,
    List<String> crew,
    List<String> ships,
    List<String> capsules,
    List<String> payloads,
    String launchpad,
    @JsonProperty("rocket") String rocket,
    Links links
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Links(
        Patch patch,
        String webcast,
        String article,
        String wikipedia
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Patch(
            String small,
            String large
        ) {}
    }
}
