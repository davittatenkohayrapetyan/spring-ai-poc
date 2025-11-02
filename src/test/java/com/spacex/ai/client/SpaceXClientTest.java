package com.spacex.ai.client;

import com.spacex.ai.model.Launch;
import com.spacex.ai.model.Rocket;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpaceXClientTest {

    private MockWebServer mockWebServer;
    private SpaceXClient spaceXClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        String baseUrl = mockWebServer.url("/").toString();
        spaceXClient = new SpaceXClient(baseUrl);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testGetAllLaunches() {
        String json = "[{\"id\":\"5eb87cd9ffd86e000604b32a\",\"name\":\"FalconSat\",\"flight_number\":1}]";
        mockWebServer.enqueue(new MockResponse()
                .setBody(json)
                .addHeader("Content-Type", "application/json"));

        List<Launch> launches = spaceXClient.getAllLaunches();

        assertNotNull(launches);
        assertEquals(1, launches.size());
        assertEquals("FalconSat", launches.get(0).name());
    }

    @Test
    void testGetLatestLaunch() {
        String json = "{\"id\":\"latest\",\"name\":\"Latest Launch\",\"flight_number\":100}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(json)
                .addHeader("Content-Type", "application/json"));

        Launch launch = spaceXClient.getLatestLaunch();

        assertNotNull(launch);
        assertEquals("Latest Launch", launch.name());
    }

    @Test
    void testGetAllRockets() {
        String json = "[{\"id\":\"5e9d0d95eda69973a809d1ec\",\"name\":\"Falcon 9\",\"type\":\"rocket\"}]";
        mockWebServer.enqueue(new MockResponse()
                .setBody(json)
                .addHeader("Content-Type", "application/json"));

        List<Rocket> rockets = spaceXClient.getAllRockets();

        assertNotNull(rockets);
        assertEquals(1, rockets.size());
        assertEquals("Falcon 9", rockets.get(0).name());
    }

    @Test
    void testGetUpcomingLaunches() {
        String json = "[{\"id\":\"upcoming1\",\"name\":\"Upcoming Launch\",\"upcoming\":true}]";
        mockWebServer.enqueue(new MockResponse()
                .setBody(json)
                .addHeader("Content-Type", "application/json"));

        List<Launch> launches = spaceXClient.getUpcomingLaunches();

        assertNotNull(launches);
        assertEquals(1, launches.size());
        assertEquals("Upcoming Launch", launches.get(0).name());
    }
}
