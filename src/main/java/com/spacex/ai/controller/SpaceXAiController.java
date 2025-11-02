package com.spacex.ai.controller;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SpaceXAiController {

    private final AnthropicChatModel chatModel;

    public SpaceXAiController(AnthropicChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/ask")
    public Map<String, Object> ask(@RequestBody AskRequest request) {
        // Create a prompt with the user's natural language query
        Prompt prompt = new Prompt(new UserMessage(request.question()));

        // Call Claude
        ChatResponse response = chatModel.call(prompt);

        return Map.of(
                "question", request.question(),
                "answer", response.getResult().getOutput().getText(),
                "metadata", Map.of(
                        "model", response.getMetadata().getModel(),
                        "usage", response.getMetadata().getUsage()
                )
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    public record AskRequest(String question) {}
}
