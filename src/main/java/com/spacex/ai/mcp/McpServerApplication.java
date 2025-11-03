package com.spacex.ai.mcp;

import com.spacex.ai.client.SpaceXClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.spacex.ai")
public class McpServerApplication {

    public static void main(String[] args) {
        // Disable web server for MCP mode
        System.setProperty("spring.main.web-application-type", "none");
        SpringApplication.run(McpServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner mcpRunner(SpaceXClient spaceXClient, ObjectMapper objectMapper) {
        return args -> {
            McpToolServer server = new McpToolServer(spaceXClient, objectMapper);
            server.start();
        };
    }
}
