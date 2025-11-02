package com.spacex.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacex.ai.client.SpaceXClient;
import com.spacex.ai.model.Launch;
import com.spacex.ai.model.Launchpad;
import com.spacex.ai.model.Rocket;
import com.spacex.ai.model.Ship;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class SpaceXToolService {

    private final SpaceXClient spaceXClient;
    private final ObjectMapper objectMapper;

    public SpaceXToolService(SpaceXClient spaceXClient, ObjectMapper objectMapper) {
        this.spaceXClient = spaceXClient;
        this.objectMapper = objectMapper;
    }

    public Function<GetAllLaunchesRequest, String> getAllLaunches() {
        return request -> {
            try {
                List<Launch> launches = spaceXClient.getAllLaunches();
                return objectMapper.writeValueAsString(launches);
            } catch (JsonProcessingException e) {
                return "Error retrieving launches: " + e.getMessage();
            }
        };
    }

    public Function<GetLaunchByIdRequest, String> getLaunchById() {
        return request -> {
            try {
                Launch launch = spaceXClient.getLaunchById(request.id());
                return objectMapper.writeValueAsString(launch);
            } catch (JsonProcessingException e) {
                return "Error retrieving launch: " + e.getMessage();
            }
        };
    }

    public Function<GetUpcomingLaunchesRequest, String> getUpcomingLaunches() {
        return request -> {
            try {
                List<Launch> launches = spaceXClient.getUpcomingLaunches();
                return objectMapper.writeValueAsString(launches);
            } catch (JsonProcessingException e) {
                return "Error retrieving upcoming launches: " + e.getMessage();
            }
        };
    }

    public Function<GetPastLaunchesRequest, String> getPastLaunches() {
        return request -> {
            try {
                List<Launch> launches = spaceXClient.getPastLaunches();
                return objectMapper.writeValueAsString(launches);
            } catch (JsonProcessingException e) {
                return "Error retrieving past launches: " + e.getMessage();
            }
        };
    }

    public Function<GetLatestLaunchRequest, String> getLatestLaunch() {
        return request -> {
            try {
                Launch launch = spaceXClient.getLatestLaunch();
                return objectMapper.writeValueAsString(launch);
            } catch (JsonProcessingException e) {
                return "Error retrieving latest launch: " + e.getMessage();
            }
        };
    }

    public Function<GetNextLaunchRequest, String> getNextLaunch() {
        return request -> {
            try {
                Launch launch = spaceXClient.getNextLaunch();
                return objectMapper.writeValueAsString(launch);
            } catch (JsonProcessingException e) {
                return "Error retrieving next launch: " + e.getMessage();
            }
        };
    }

    public Function<GetAllRocketsRequest, String> getAllRockets() {
        return request -> {
            try {
                List<Rocket> rockets = spaceXClient.getAllRockets();
                return objectMapper.writeValueAsString(rockets);
            } catch (JsonProcessingException e) {
                return "Error retrieving rockets: " + e.getMessage();
            }
        };
    }

    public Function<GetRocketByIdRequest, String> getRocketById() {
        return request -> {
            try {
                Rocket rocket = spaceXClient.getRocketById(request.id());
                return objectMapper.writeValueAsString(rocket);
            } catch (JsonProcessingException e) {
                return "Error retrieving rocket: " + e.getMessage();
            }
        };
    }

    public Function<GetAllShipsRequest, String> getAllShips() {
        return request -> {
            try {
                List<Ship> ships = spaceXClient.getAllShips();
                return objectMapper.writeValueAsString(ships);
            } catch (JsonProcessingException e) {
                return "Error retrieving ships: " + e.getMessage();
            }
        };
    }

    public Function<GetShipByIdRequest, String> getShipById() {
        return request -> {
            try {
                Ship ship = spaceXClient.getShipById(request.id());
                return objectMapper.writeValueAsString(ship);
            } catch (JsonProcessingException e) {
                return "Error retrieving ship: " + e.getMessage();
            }
        };
    }

    public Function<GetAllLaunchpadsRequest, String> getAllLaunchpads() {
        return request -> {
            try {
                List<Launchpad> launchpads = spaceXClient.getAllLaunchpads();
                return objectMapper.writeValueAsString(launchpads);
            } catch (JsonProcessingException e) {
                return "Error retrieving launchpads: " + e.getMessage();
            }
        };
    }

    public Function<GetLaunchpadByIdRequest, String> getLaunchpadById() {
        return request -> {
            try {
                Launchpad launchpad = spaceXClient.getLaunchpadById(request.id());
                return objectMapper.writeValueAsString(launchpad);
            } catch (JsonProcessingException e) {
                return "Error retrieving launchpad: " + e.getMessage();
            }
        };
    }

    // Request records
    public record GetAllLaunchesRequest() {}
    public record GetLaunchByIdRequest(String id) {}
    public record GetUpcomingLaunchesRequest() {}
    public record GetPastLaunchesRequest() {}
    public record GetLatestLaunchRequest() {}
    public record GetNextLaunchRequest() {}
    public record GetAllRocketsRequest() {}
    public record GetRocketByIdRequest(String id) {}
    public record GetAllShipsRequest() {}
    public record GetShipByIdRequest(String id) {}
    public record GetAllLaunchpadsRequest() {}
    public record GetLaunchpadByIdRequest(String id) {}
}
