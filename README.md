# SpaceX AI - Spring Boot REST Service

A Spring Boot 3.4 REST service that uses Spring AI with a locally hosted LLM (via Ollama) to query the public SpaceX API (https://docs.spacexdata.com/) through natural language prompts. The service exposes REST endpoints and includes an MCP (Model Context Protocol) tool server for AI agents.

## Features

- 🚀 Spring Boot 3.4 with Java 17
- 🤖 Spring AI integration with a local Ollama model (default: phi3:mini)
- 📡 SpaceX API Client for launches, rockets, ships, and launchpads
- 💬 Natural Language Interface via `/ask` endpoint
- 🔌 MCP Tool Server for AI agent integration
- 🐳 Docker & Docker Compose support
- ✅ Unit Tests

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
│  │ /ask        │───▶│ Ollama via   │  │
│  │ endpoint    │    │ Spring AI    │  │
│  └─────────────┘    └──────┬───────┘  │
│                             │          │
│                             ▼          │
│                     ┌────────────────┐ │
│                     │ Tools/Services │ │
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
- Docker & Docker Compose (for containerized deployment and local LLM)

## Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/davittatenkohayrapetyan/spring-ai-poc.git
cd spring-ai-poc
```

### 2. (Optional) Configure Ollama Endpoint

If you're running the Java application outside of Docker you can optionally set a custom Ollama endpoint (defaults to `http://localhost:11434`):

```bash
export OLLAMA_BASE_URL=http://localhost:11434
```

### 3. Run with Maven

```bash
# Build the project
mvn clean package

# Make sure your Ollama server is running (see Docker instructions below)
mvn spring-boot:run

# OR use the convenience script
./run.sh
```

### 4. Run with Docker Compose

```bash
# Build and run (first run will download the phi3:mini model ~2.2 GB)
docker-compose up --build

# Run in detached mode
docker-compose up -d

# Stop the services
docker-compose down
```

Once the containers are healthy you can verify the Ollama service separately:

```bash
curl http://localhost:11434/api/tags | jq
```

## API Endpoints

### Quick Test

You can run the included test script to try out the API:

```bash
# Make sure the application is running first
./test-api.sh
```

### Health Check

```bash
curl http://localhost:8080/api/health
```

Response:
```json
{ "status": "UP" }
```

### Ask Endpoint (Natural Language Queries)

Send natural language questions about SpaceX to get AI-powered responses:

```bash
curl -X POST http://localhost:8080/api/ask \
  -H "Content-Type: application/json" \
  -d '{
    "question": "What is the latest SpaceX launch?"
  }'
```

Example response:
```json
{
  "question": "What is the latest SpaceX launch?",
  "answer": "The latest SpaceX launch was..."
}
```

## Configuration

Application properties (`src/main/resources/application.yml`):

```yaml
spring:
  ai:
    ollama:
      base-url: ${OLLAMA_BASE_URL:http://localhost:11434}
      chat:
        options:
          model: phi3:mini
          temperature: 0.7
          num-predict: 1024

spacex:
  api:
    base-url: https://api.spacexdata.com/v4
```

## SpaceX API Integration

The application provides a REST client that fetches data from the public SpaceX API (https://api.spacexdata.com/v4).

- Launches: all, upcoming, past, latest, next, by ID
- Rockets: all or by ID
- Ships: all or by ID
- Launchpads: all or by ID

## MCP Tool Server

Run the MCP server separately if needed:

```bash
java -jar target/spacex-ai-1.0.0-SNAPSHOT.jar \
  --spring.main.web-application-type=none
```

The server communicates via JSON-RPC over stdin/stdout.

## Project Structure

```
spring-ai-poc/
├── src/
│   ├── main/
│   │   ├── java/com/spacex/ai/
│   │   │   ├── SpacexAiApplication.java
│   │   │   ├── client/
│   │   │   │   └── SpaceXClient.java
│   │   │   ├── config/
│   │   │   │   └── ChatClientConfiguration.java
│   │   │   ├── controller/
│   │   │   │   └── SpaceXAiController.java
│   │   │   ├── mcp/
│   │   │   │   ├── McpServerApplication.java
│   │   │   │   └── McpToolServer.java
│   │   │   ├── model/
│   │   │   └── service/
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/com/spacex/ai/
│           ├── client/
│           │   └── SpaceXClientTest.java
│           └── controller/
│               └── SpaceXAiControllerTest.java
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── .env.example
├── .gitignore
└── README.md
```

## Technologies Used

- Spring Boot 3.4
- Spring AI 1.0.3
- Ollama (phi3:mini)
- Spring WebFlux
- Jackson
- JUnit 5, MockWebServer
- Maven, Docker

## Troubleshooting

- Verify the Ollama service is running: `curl http://localhost:11434/api/version`
- Check connectivity to SpaceX API: `curl https://api.spacexdata.com/v4/launches/latest`
- Use `docker-compose logs` for container logs
- Apple Silicon (M1/M2/M3): If you see a "no match for platform in manifest" error when building images, update to the latest Docker Desktop. This project now uses multi-arch base images. As a workaround, you can also force the platform:

```bash
# Build locally forcing linux/arm64
DOCKER_DEFAULT_PLATFORM=linux/arm64 docker-compose build --no-cache

# Or for x86 emulation (slower):
DOCKER_DEFAULT_PLATFORM=linux/amd64 docker-compose build --no-cache
```

## Resources

- Spring AI Documentation: https://docs.spring.io/spring-ai/reference/
- Ollama: https://ollama.com/
- SpaceX API: https://github.com/r-spacex/SpaceX-API
- Model Context Protocol: https://modelcontextprotocol.io/

## License

This project is open source and available under the MIT License.
