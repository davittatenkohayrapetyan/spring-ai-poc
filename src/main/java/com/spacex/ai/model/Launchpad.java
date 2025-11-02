package com.spacex.ai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Launchpad(
    String id,
    String name,
    @JsonProperty("full_name") String fullName,
    String locality,
    String region,
    String timezone,
    Double latitude,
    Double longitude,
    @JsonProperty("launch_attempts") Integer launchAttempts,
    @JsonProperty("launch_successes") Integer launchSuccesses,
    String status,
    String details
) {}
