package com.spacex.ai.config;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.anthropic.api.AnthropicApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnthropicConfiguration {

    @Value("${spring.ai.anthropic.api-key}")
    private String apiKey;

    @Bean
    public AnthropicApi anthropicApi() {
        return AnthropicApi.builder().apiKey(apiKey).build();
    }

    @Bean
    public AnthropicChatModel anthropicChatModel(AnthropicApi anthropicApi) {
        AnthropicChatOptions options = AnthropicChatOptions.builder()
                .model(AnthropicApi.ChatModel.CLAUDE_3_5_SONNET.getValue())
                .temperature(0.7)
                .maxTokens(4096)
                .build();

        return AnthropicChatModel.builder()
                .anthropicApi(anthropicApi)
                .defaultOptions(options)
                .build();
    }
}
