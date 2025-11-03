#!/bin/bash

# Test script for SpaceX AI REST service
# Make sure the service is running before executing this script

BASE_URL="${BASE_URL:-http://localhost:8080}"

echo "Testing SpaceX AI REST Service"
echo "================================"
echo

# Test 1: Health check
echo "1. Health Check"
echo "   curl $BASE_URL/api/health"
curl -s "$BASE_URL/api/health" | jq .
echo
echo

# Test 2: Ask about latest launch
echo "2. Ask about latest SpaceX launch"
echo "   curl -X POST $BASE_URL/api/ask ..."
curl -s -X POST "$BASE_URL/api/ask" \
  -H "Content-Type: application/json" \
  -d '{"question": "What was the latest SpaceX launch?"}' | jq .
echo
echo

# Test 3: Ask about Falcon 9
echo "3. Ask about Falcon 9 rocket"
echo "   curl -X POST $BASE_URL/api/ask ..."
curl -s -X POST "$BASE_URL/api/ask" \
  -H "Content-Type: application/json" \
  -d '{"question": "Tell me about the Falcon 9 rocket"}' | jq .
echo
echo

# Test 4: Ask about upcoming launches
echo "4. Ask about upcoming launches"
echo "   curl -X POST $BASE_URL/api/ask ..."
curl -s -X POST "$BASE_URL/api/ask" \
  -H "Content-Type: application/json" \
  -d '{"question": "What are the upcoming SpaceX launches?"}' | jq .
echo
echo

echo "Tests completed!"
