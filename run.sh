#!/bin/bash

# Startup script for SpaceX AI REST service

# Check if API key is set
if [ -z "$ANTHROPIC_API_KEY" ]; then
    echo "Error: ANTHROPIC_API_KEY environment variable is not set"
    echo "Please set it with: export ANTHROPIC_API_KEY=your-api-key"
    echo "Or create a .env file with your API key"
    exit 1
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
