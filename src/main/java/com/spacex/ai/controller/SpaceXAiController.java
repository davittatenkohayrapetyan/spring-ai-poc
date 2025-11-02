package com.spacex.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
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
    public Map<String, Object> ask(@RequestBody AskRequest request) {
        // Use Spring AI ChatClient chaining API to get the content string
        String answer = chatClient
                .prompt()
                .user(request.question())
                .call()
                .content();
        return Map.of(
                "question", request.question(),
                "answer", answer
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    public record AskRequest(String question) {}
}
