package com.spacex.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SpaceXAiController {

    private final ChatClient chatClient;

    public SpaceXAiController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/ask")
    public ResponseEntity<Map<String, Object>> ask(@RequestBody AskRequest request) {
        if (request == null || request.question() == null || request.question().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "error", "Question must be provided",
                            "status", 400
                    ));
        }
        try {
            // Use Spring AI ChatClient chaining API to get the content string
            String answer = chatClient
                    .prompt()
                    .user(request.question())
                    .call()
                    .content();
            return ResponseEntity.ok(Map.of(
                    "question", request.question(),
                    "answer", answer
            ));
        } catch (Exception e) {
            // Map common issues (e.g., missing/invalid API key, network) to 502
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of(
                            "error", "Failed to get response from AI provider",
                            "details", e.getMessage(),
                            "status", 502
                    ));
        }
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    public record AskRequest(String question) {}
}
