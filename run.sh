#!/bin/bash

# Startup script for SpaceX AI REST service

# Determine Ollama endpoint
OLLAMA_BASE_URL=${OLLAMA_BASE_URL:-http://localhost:11434}

echo "Using Ollama endpoint: $OLLAMA_BASE_URL"
if ! curl -sSf "$OLLAMA_BASE_URL/api/version" >/dev/null 2>&1; then
    cat <<'EOF'
Warning: could not reach the Ollama service. Make sure it is running.
You can start it via Docker Compose:
  docker compose up ollama
EOF
fi

# Check if jar exists
JAR_FILE="target/spacex-ai-1.0.0-SNAPSHOT.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "Building application..."
    mvn clean package -DskipTests
    if [ $? -ne 0 ]; then
        echo "Build failed!"
        exit 1
    fi
fi

# Start the application
echo "Starting SpaceX AI REST service..."
echo "API will be available at http://localhost:8080"
echo "Press Ctrl+C to stop"
echo

java -jar "$JAR_FILE"
