# Contributing to SpaceX AI

Thank you for your interest in contributing to the SpaceX AI project!

## Development Setup

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- An Anthropic API key for testing

### Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/davittatenkohayrapetyan/spring-ai-poc.git
   cd spring-ai-poc
   ```

2. Set up your environment:
   ```bash
   cp .env.example .env
   # Edit .env and add your Anthropic API key
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   export ANTHROPIC_API_KEY=your-api-key
   mvn spring-boot:run
   ```

## Project Structure

```
spring-ai-poc/
├── src/
│   ├── main/
│   │   ├── java/com/spacex/ai/
│   │   │   ├── SpacexAiApplication.java      # Main Spring Boot application
│   │   │   ├── client/                        # SpaceX API client
│   │   │   ├── config/                        # Spring configuration
│   │   │   ├── controller/                    # REST controllers
│   │   │   ├── mcp/                          # MCP server
│   │   │   ├── model/                        # Data models
│   │   │   └── service/                      # Business services
│   │   └── resources/
│   │       └── application.yml               # Application configuration
│   └── test/                                 # Unit and integration tests
├── Dockerfile                                # Docker container configuration
├── docker-compose.yml                        # Docker Compose setup
└── pom.xml                                   # Maven build configuration
```

## Code Style

- Follow standard Java coding conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public APIs
- Keep methods focused and concise

## Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=SpaceXClientTest

# Run with coverage
mvn test jacoco:report
```

### Writing Tests

- Write unit tests for all new functionality
- Use MockWebServer for testing HTTP clients
- Mock external dependencies
- Aim for high test coverage

## Making Changes

1. Create a new branch for your feature:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit them:
   ```bash
   git add .
   git commit -m "Add your commit message"
   ```

3. Push your changes:
   ```bash
   git push origin feature/your-feature-name
   ```

4. Create a Pull Request on GitHub

## Pull Request Guidelines

- Provide a clear description of the changes
- Include relevant issue numbers
- Ensure all tests pass
- Update documentation as needed
- Follow the existing code style

## Adding New Features

### Adding a New SpaceX API Endpoint

1. Add the method to `SpaceXClient.java`:
   ```java
   public NewDataType getNewData() {
       return webClient.get()
               .uri("/new-endpoint")
               .retrieve()
               .bodyToMono(NewDataType.class)
               .block();
   }
   ```

2. Create a model class in `model/` package:
   ```java
   @JsonIgnoreProperties(ignoreUnknown = true)
   public record NewDataType(
       String id,
       String name
       // ... other fields
   ) {}
   ```

3. Add tests in `test/java/com/spacex/ai/client/`

### Updating Configuration

Configuration can be updated in `src/main/resources/application.yml`:

```yaml
spring:
  ai:
    anthropic:
      api-key: ${ANTHROPIC_API_KEY}
      chat:
        options:
          model: claude-3-5-sonnet
          temperature: 0.7
          max-tokens: 4096
```

## Troubleshooting

### Build Issues

If you encounter build issues:

1. Clean the Maven cache:
   ```bash
   mvn clean install -U
   ```

2. Verify Java version:
   ```bash
   java -version
   # Should be 17 or higher
   ```

### Runtime Issues

If the application fails to start:

1. Check that the Anthropic API key is set:
   ```bash
   echo $ANTHROPIC_API_KEY
   ```

2. Verify the SpaceX API is accessible:
   ```bash
   curl https://api.spacexdata.com/v4/launches/latest
   ```

3. Check application logs for error messages

## Questions or Issues?

If you have questions or encounter issues:

1. Check the [README](README.md) for documentation
2. Search existing GitHub issues
3. Create a new issue with a clear description

## License

By contributing to this project, you agree that your contributions will be licensed under the project's license.
