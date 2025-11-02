# SpaceX AI - Spring Boot REST Service

A Spring Boot 3.4 REST service that uses Spring AI with Anthropic Claude to query the public SpaceX API (https://docs.spacexdata.com/) through natural language prompts. The service exposes REST endpoints with @Tool annotated methods for function calling and includes an MCP (Model Context Protocol) tool server for AI agents.

## Features

- 🚀 **Spring Boot 3.4** with Java 17
- 🤖 **Spring AI** integration with Anthropic Claude (claude-3-5-sonnet)
- 📡 **SpaceX API Client** for fetching launches, rockets, ships, and launchpads
- 💬 **Natural Language Interface** via `/ask` endpoint
- 🔌 **MCP Tool Server** for AI agent integration
- 🐳 **Docker & Docker Compose** support
- ✅ **Unit Tests**

## Architecture

```
┌─────────────────┐
│   User/Agent    │
└────────┬────────┘
         │
         ▼
┌─────────────────────────────────────────┐
│          Spring Boot Application        │
│                                         │
│  ┌─────────────┐    ┌──────────────┐  │
│  │ /ask        │───▶│ Claude AI    │  │
│  │ endpoint    │    │ (Anthropic)  │  │
│  └─────────────┘    └──────┬───────┘  │
│                             │          │
│                             ▼          │
│                     ┌────────────────┐ │
│                     │ @Tool methods  │ │
│                     │ (Functions)    │ │
│                     └────────┬───────┘ │
│                              │         │
│                              ▼         │
│                     ┌────────────────┐ │
│                     │ SpaceX Client  │ │
│                     └────────┬───────┘ │
└──────────────────────────────┼─────────┘
                               │
                               ▼
                    ┌─────────────────┐
                    │  SpaceX API     │
                    │  (public)       │
                    └─────────────────┘
```

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- Docker & Docker Compose (optional, for containerized deployment)
- Anthropic API Key (get one from https://console.anthropic.com/)

## Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/davittatenkohayrapetyan/spring-ai-poc.git
cd spring-ai-poc
```

### 2. Set Up Environment Variables

Create a `.env` file from the template:

```bash
cp .env.example .env
```

Edit `.env` and add your Anthropic API key:

```
ANTHROPIC_API_KEY=your-actual-api-key-here
```

### 3. Run with Maven

```bash
# Build the project
mvn clean package

# Run the application
export ANTHROPIC_API_KEY=your-api-key
mvn spring-boot:run
```

### 4. Run with Docker Compose

```bash
# Build and run
docker-compose up --build

# Run in detached mode
docker-compose up -d

# Stop the services
docker-compose down
```

## API Endpoints

### Health Check

```bash
curl http://localhost:8080/api/health
```

**Response:**
```json
{
  "status": "UP"
}
```

### Ask Endpoint (Natural Language Queries)

Send natural language questions about SpaceX to get AI-powered responses with real data:

```bash
curl -X POST http://localhost:8080/api/ask \
  -H "Content-Type: application/json" \
  -d '{
    "question": "What is the latest SpaceX launch?"
  }'
```

**Response:**
```json
{
  "question": "What is the latest SpaceX launch?",
  "answer": "The latest SpaceX launch was...",
  "metadata": {...}
}
```

## Sample Queries

Here are some example natural language questions you can ask:

### Launch Queries

1. **Latest Launch**
   ```bash
   curl -X POST http://localhost:8080/api/ask \
     -H "Content-Type: application/json" \
     -d '{"question": "What was the latest SpaceX launch?"}'
   ```

2. **Upcoming Launches**
   ```bash
   curl -X POST http://localhost:8080/api/ask \
     -H "Content-Type: application/json" \
     -d '{"question": "Tell me about upcoming SpaceX launches"}'
   ```

3. **Launch Success Rate**
   ```bash
   curl -X POST http://localhost:8080/api/ask \
     -H "Content-Type: application/json" \
     -d '{"question": "How many successful launches has SpaceX had?"}'
   ```

### Rocket Queries

4. **Falcon 9 Information**
   ```bash
   curl -X POST http://localhost:8080/api/ask \
     -H "Content-Type: application/json" \
     -d '{"question": "Tell me about the Falcon 9 rocket"}'
   ```

5. **All Rockets**
   ```bash
   curl -X POST http://localhost:8080/api/ask \
     -H "Content-Type: application/json" \
     -d '{"question": "What rockets does SpaceX have?"}'
   ```

6. **Starship Details**
   ```bash
   curl -X POST http://localhost:8080/api/ask \
     -H "Content-Type: application/json" \
     -d '{"question": "What are the specifications of Starship?"}'
   ```

### Ship Queries

7. **Active Ships**
   ```bash
   curl -X POST http://localhost:8080/api/ask \
     -H "Content-Type: application/json" \
     -d '{"question": "What ships does SpaceX have?"}'
   ```

8. **Drone Ships**
   ```bash
   curl -X POST http://localhost:8080/api/ask \
     -H "Content-Type: application/json" \
     -d '{"question": "Tell me about SpaceX drone ships"}'
   ```

### Launchpad Queries

9. **All Launchpads**
   ```bash
   curl -X POST http://localhost:8080/api/ask \
     -H "Content-Type: application/json" \
     -d '{"question": "Where does SpaceX launch from?"}'
   ```

10. **Kennedy Space Center**
    ```bash
    curl -X POST http://localhost:8080/api/ask \
      -H "Content-Type: application/json" \
      -d '{"question": "Tell me about Kennedy Space Center launch pad"}'
    ```

### Complex Queries

11. **Comparative Analysis**
    ```bash
    curl -X POST http://localhost:8080/api/ask \
      -H "Content-Type: application/json" \
      -d '{"question": "Compare Falcon 9 and Falcon Heavy rockets"}'
    ```

12. **Statistical Query**
    ```bash
    curl -X POST http://localhost:8080/api/ask \
      -H "Content-Type: application/json" \
      -d '{"question": "What is the success rate of SpaceX launches in 2024?"}'
    ```

## SpaceX API Integration

The application provides a REST client that fetches data from the public SpaceX API (https://api.spacexdata.com/v4). 

### Available Data
- **Launches**: Get all launches, upcoming launches, past launches, latest launch, next launch, or a specific launch by ID
- **Rockets**: Get all SpaceX rockets or a specific rocket by ID (Falcon 9, Falcon Heavy, Starship, etc.)
- **Ships**: Get all SpaceX ships or a specific ship by ID (drone ships, support vessels, etc.)
- **Launchpads**: Get all SpaceX launchpads or a specific launchpad by ID

Claude can understand natural language questions about SpaceX data and provide intelligent responses based on the data available from the SpaceX API.

## MCP Tool Server

The application includes an MCP (Model Context Protocol) tool server that can be used by AI agents to access SpaceX data.

### Running the MCP Server

```bash
# Run separately from the REST API
docker-compose up spacex-ai-mcp
```

Or with Maven:

```bash
java -jar target/spacex-ai-1.0.0-SNAPSHOT.jar \
  --spring.main.web-application-type=none
```

### MCP Protocol

The server communicates via JSON-RPC over stdin/stdout:

**Request:**
```json
{
  "jsonrpc": "2.0",
  "id": 1,
  "method": "getAllLaunches",
  "params": {}
}
```

**Response:**
```json
{
  "jsonrpc": "2.0",
  "id": 1,
  "result": [...]
}
```

## Configuration

### Application Properties

The application can be configured via `src/main/resources/application.yml`:

```yaml
spring:
  ai:
    anthropic:
      api-key: ${ANTHROPIC_API_KEY}
      chat:
        options:
          model: claude-3-5-sonnet-20241022
          temperature: 0.7
          max-tokens: 4096

spacex:
  api:
    base-url: https://api.spacexdata.com/v4
```

### Environment Variables

- `ANTHROPIC_API_KEY` - Your Anthropic API key (required)
- `SPRING_PROFILES_ACTIVE` - Active Spring profile (default: none)

## Development

### Building the Project

```bash
mvn clean install
```

### Running Tests

```bash
mvn test
```

### Running Specific Tests

```bash
# Run client tests
mvn test -Dtest=SpaceXClientTest

# Run controller tests
mvn test -Dtest=SpaceXAiControllerTest
```

### Code Coverage

```bash
mvn test jacoco:report
```

## Project Structure

```
spring-ai-poc/
├── src/
│   ├── main/
│   │   ├── java/com/spacex/ai/
│   │   │   ├── SpacexAiApplication.java      # Main application
│   │   │   ├── client/
│   │   │   │   └── SpaceXClient.java         # SpaceX API client
│   │   │   ├── config/
│   │   │   │   └── SpaceXFunctionConfiguration.java  # Function definitions
│   │   │   ├── controller/
│   │   │   │   └── SpaceXAiController.java   # REST endpoints
│   │   │   ├── mcp/
│   │   │   │   ├── McpServerApplication.java # MCP server main
│   │   │   │   └── McpToolServer.java        # MCP tool server
│   │   │   ├── model/
│   │   │   │   ├── Launch.java               # Launch model
│   │   │   │   ├── Rocket.java               # Rocket model
│   │   │   │   ├── Ship.java                 # Ship model
│   │   │   │   └── Launchpad.java            # Launchpad model
│   │   │   └── service/
│   │   │       └── SpaceXToolService.java    # Tool service
│   │   └── resources/
│   │       └── application.yml               # Configuration
│   └── test/
│       └── java/com/spacex/ai/
│           ├── client/
│           │   └── SpaceXClientTest.java     # Client tests
│           └── controller/
│               └── SpaceXAiControllerTest.java  # Controller tests
├── Dockerfile                                 # Docker configuration
├── docker-compose.yml                         # Docker Compose configuration
├── pom.xml                                    # Maven configuration
├── .env.example                              # Environment template
├── .gitignore                                # Git ignore rules
└── README.md                                 # This file
```

## Technologies Used

- **Spring Boot 3.4** - Application framework
- **Spring AI 1.0.3** - AI integration framework
- **Anthropic Claude** - AI model (claude-3-5-sonnet)
- **Spring WebFlux** - Reactive web client for SpaceX API
- **Jackson** - JSON processing
- **JUnit 5** - Testing framework
- **MockWebServer** - HTTP client testing
- **Maven** - Build tool
- **Docker** - Containerization

## Troubleshooting

### API Key Issues

If you get authentication errors:
1. Verify your API key is correct in `.env` or environment variables
2. Check that the environment variable is being loaded: `echo $ANTHROPIC_API_KEY`
3. Ensure the API key has sufficient credits

### Connection Issues

If you can't connect to the SpaceX API:
1. Check your internet connection
2. Verify the SpaceX API is up: `curl https://api.spacexdata.com/v4/launches/latest`
3. Check for any firewall or proxy issues

### Docker Issues

If Docker containers fail to start:
1. Ensure Docker is running: `docker ps`
2. Check logs: `docker-compose logs`
3. Verify ports 8080 is available: `lsof -i :8080`

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is open source and available under the MIT License.

## Resources

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [Anthropic Claude API](https://docs.anthropic.com/)
- [SpaceX API Documentation](https://github.com/r-spacex/SpaceX-API)
- [Model Context Protocol](https://modelcontextprotocol.io/)

## Support

For issues, questions, or contributions, please open an issue on GitHub.
