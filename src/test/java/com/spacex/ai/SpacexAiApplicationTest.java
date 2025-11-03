package com.spacex.ai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.ai.anthropic.api-key=test-key"
})
class SpacexAiApplicationTest {

    @Test
    void contextLoads() {
        // This test ensures the Spring context loads successfully
    }
}
