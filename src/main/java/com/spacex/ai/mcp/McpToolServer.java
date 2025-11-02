package com.spacex.ai.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacex.ai.client.SpaceXClient;
import com.spacex.ai.model.Launch;
import com.spacex.ai.model.Launchpad;
import com.spacex.ai.model.Rocket;
import com.spacex.ai.model.Ship;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MCP (Model Context Protocol) Tool Server
 * Exposes SpaceX API methods as MCP tools that can be called by AI agents
 */
@Component
public class McpToolServer {

    private final SpaceXClient spaceXClient;
    private final ObjectMapper objectMapper;

    public McpToolServer(SpaceXClient spaceXClient, ObjectMapper objectMapper) {
        this.spaceXClient = spaceXClient;
        this.objectMapper = objectMapper;
    }

    /**
     * Start the MCP server and listen for tool requests on stdin/stdout
     */
    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out, true);

        // Send initial capabilities
        sendCapabilities(writer);

        String line;
        while ((line = reader.readLine()) != null) {
            try {
                Map<String, Object> request = objectMapper.readValue(line, Map.class);
                Map<String, Object> response = handleRequest(request);
                writer.println(objectMapper.writeValueAsString(response));
            } catch (Exception e) {
                Map<String, Object> error = Map.of(
                        "error", e.getMessage(),
                        "type", "processing_error"
                );
                writer.println(objectMapper.writeValueAsString(error));
            }
        }
    }

    private void sendCapabilities(PrintWriter writer) throws IOException {
        Map<String, Object> capabilities = Map.of(
                "protocolVersion", "1.0",
                "serverInfo", Map.of(
                        "name", "spacex-mcp-server",
                        "version", "1.0.0"
                ),
                "capabilities", Map.of(
                        "tools", List.of(
                                createToolDefinition("getAllLaunches", "Get all SpaceX launches", Map.of()),
                                createToolDefinition("getLaunchById", "Get a specific launch by ID", Map.of("id", "string")),
                                createToolDefinition("getUpcomingLaunches", "Get upcoming launches", Map.of()),
                                createToolDefinition("getPastLaunches", "Get past launches", Map.of()),
                                createToolDefinition("getLatestLaunch", "Get the latest launch", Map.of()),
                                createToolDefinition("getNextLaunch", "Get the next launch", Map.of()),
                                createToolDefinition("getAllRockets", "Get all SpaceX rockets", Map.of()),
                                createToolDefinition("getRocketById", "Get a specific rocket by ID", Map.of("id", "string")),
                                createToolDefinition("getAllShips", "Get all SpaceX ships", Map.of()),
                                createToolDefinition("getShipById", "Get a specific ship by ID", Map.of("id", "string")),
                                createToolDefinition("getAllLaunchpads", "Get all SpaceX launchpads", Map.of()),
                                createToolDefinition("getLaunchpadById", "Get a specific launchpad by ID", Map.of("id", "string"))
                        )
                )
        );
        writer.println(objectMapper.writeValueAsString(capabilities));
    }

    private Map<String, Object> createToolDefinition(String name, String description, Map<String, String> parameters) {
        Map<String, Object> tool = new HashMap<>();
        tool.put("name", name);
        tool.put("description", description);
        tool.put("inputSchema", Map.of(
                "type", "object",
                "properties", parameters,
                "required", parameters.keySet()
        ));
        return tool;
    }

    private Map<String, Object> handleRequest(Map<String, Object> request) throws Exception {
        String method = (String) request.get("method");
        Map<String, Object> params = (Map<String, Object>) request.getOrDefault("params", Map.of());

        Object result = switch (method) {
            case "getAllLaunches" -> spaceXClient.getAllLaunches();
            case "getLaunchById" -> spaceXClient.getLaunchById((String) params.get("id"));
            case "getUpcomingLaunches" -> spaceXClient.getUpcomingLaunches();
            case "getPastLaunches" -> spaceXClient.getPastLaunches();
            case "getLatestLaunch" -> spaceXClient.getLatestLaunch();
            case "getNextLaunch" -> spaceXClient.getNextLaunch();
            case "getAllRockets" -> spaceXClient.getAllRockets();
            case "getRocketById" -> spaceXClient.getRocketById((String) params.get("id"));
            case "getAllShips" -> spaceXClient.getAllShips();
            case "getShipById" -> spaceXClient.getShipById((String) params.get("id"));
            case "getAllLaunchpads" -> spaceXClient.getAllLaunchpads();
            case "getLaunchpadById" -> spaceXClient.getLaunchpadById((String) params.get("id"));
            default -> throw new IllegalArgumentException("Unknown method: " + method);
        };

        return Map.of(
                "jsonrpc", "2.0",
                "id", request.getOrDefault("id", null),
                "result", result
        );
    }
}
