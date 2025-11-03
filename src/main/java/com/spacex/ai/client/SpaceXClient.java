package com.spacex.ai.client;

import com.spacex.ai.model.Launch;
import com.spacex.ai.model.Launchpad;
import com.spacex.ai.model.Rocket;
import com.spacex.ai.model.Ship;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class SpaceXClient {

    private final WebClient webClient;

    public SpaceXClient(@Value("${spacex.api.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public List<Launch> getAllLaunches() {
        return webClient.get()
                .uri("/launches")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Launch>>() {})
                .block();
    }

    public Launch getLaunchById(String id) {
        return webClient.get()
                .uri("/launches/{id}", id)
                .retrieve()
                .bodyToMono(Launch.class)
                .block();
    }

    public List<Launch> getUpcomingLaunches() {
        return webClient.get()
                .uri("/launches/upcoming")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Launch>>() {})
                .block();
    }

    public List<Launch> getPastLaunches() {
        return webClient.get()
                .uri("/launches/past")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Launch>>() {})
                .block();
    }

    public Launch getLatestLaunch() {
        return webClient.get()
                .uri("/launches/latest")
                .retrieve()
                .bodyToMono(Launch.class)
                .block();
    }

    public Launch getNextLaunch() {
        return webClient.get()
                .uri("/launches/next")
                .retrieve()
                .bodyToMono(Launch.class)
                .block();
    }

    public List<Rocket> getAllRockets() {
        return webClient.get()
                .uri("/rockets")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Rocket>>() {})
                .block();
    }

    public Rocket getRocketById(String id) {
        return webClient.get()
                .uri("/rockets/{id}", id)
                .retrieve()
                .bodyToMono(Rocket.class)
                .block();
    }

    public List<Ship> getAllShips() {
        return webClient.get()
                .uri("/ships")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Ship>>() {})
                .block();
    }

    public Ship getShipById(String id) {
        return webClient.get()
                .uri("/ships/{id}", id)
                .retrieve()
                .bodyToMono(Ship.class)
                .block();
    }

    public List<Launchpad> getAllLaunchpads() {
        return webClient.get()
                .uri("/launchpads")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Launchpad>>() {})
                .block();
    }

    public Launchpad getLaunchpadById(String id) {
        return webClient.get()
                .uri("/launchpads/{id}", id)
                .retrieve()
                .bodyToMono(Launchpad.class)
                .block();
    }
}
