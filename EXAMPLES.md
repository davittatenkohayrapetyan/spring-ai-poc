# SpaceX AI - Usage Examples

This document provides detailed examples of how to use the SpaceX AI REST service.

## Table of Contents

- [Basic Queries](#basic-queries)
- [Advanced Queries](#advanced-queries)
- [Using with curl](#using-with-curl)
- [Using with Python](#using-with-python)
- [Using with JavaScript](#using-with-javascript)

## Basic Queries

### Latest Launch Information

```bash
curl -X POST http://localhost:8080/api/ask \
  -H "Content-Type: application/json" \
  -d '{
    "question": "What was the latest SpaceX launch?"
  }'
```

**Expected Response:**
```json
{
  "question": "What was the latest SpaceX launch?",
  "answer": "The latest SpaceX launch was..."
}
```

### Rocket Information

```bash
curl -X POST http://localhost:8080/api/ask \
  -H "Content-Type: application/json" \
  -d '{
    "question": "Tell me about the Falcon 9 rocket"
  }'
```

### Upcoming Launches

```bash
curl -X POST http://localhost:8080/api/ask \
  -H "Content-Type: application/json" \
  -d '{
    "question": "What are the next 3 upcoming SpaceX launches?"
  }'
```

## Advanced Queries

### Comparative Analysis

```bash
curl -X POST http://localhost:8080/api/ask \
  -H "Content-Type: application/json" \
  -d '{
    "question": "Compare the capabilities of Falcon 9 and Falcon Heavy"
  }'
```

### Success Rate Analysis

```bash
curl -X POST http://localhost:8080/api/ask \
  -H "Content-Type: application/json" \
  -d '{
    "question": "What is the success rate of SpaceX launches in the last year?"
  }'
```

### Historical Questions

```bash
curl -X POST http://localhost:8080/api/ask \
  -H "Content-Type: application/json" \
  -d '{
    "question": "When was the first successful landing of a Falcon 9 first stage?"
  }'
```

## Using with curl

### Save Response to File

```bash
curl -X POST http://localhost:8080/api/ask \
  -H "Content-Type: application/json" \
  -d '{
    "question": "List all SpaceX rockets"
  }' > response.json
```

### Pretty Print with jq

```bash
curl -X POST http://localhost:8080/api/ask \
  -H "Content-Type: application/json" \
  -d '{
    "question": "What ships does SpaceX have?"
  }' | jq .
```

### Multiple Queries in Sequence

```bash
#!/bin/bash

questions=(
  "What was the latest launch?"
  "What are the active launchpads?"
  "Tell me about Starship"
)

for question in "${questions[@]}"; do
  echo "Question: $question"
  curl -s -X POST http://localhost:8080/api/ask \
    -H "Content-Type: application/json" \
    -d "{\"question\": \"$question\"}" | jq .answer
  echo ""
done
```

## Using with Python

### Basic Python Client

```python
import requests
import json

def ask_spacex_ai(question):
    url = "http://localhost:8080/api/ask"
    headers = {"Content-Type": "application/json"}
    data = {"question": question}
    
    response = requests.post(url, headers=headers, json=data)
    return response.json()

# Example usage
result = ask_spacex_ai("What was the latest SpaceX launch?")
print(f"Question: {result['question']}")
print(f"Answer: {result['answer']}")
```

### Advanced Python Client with Error Handling

```python
import requests
import json
from typing import Dict, Any

class SpaceXAIClient:
    def __init__(self, base_url: str = "http://localhost:8080"):
        self.base_url = base_url
        self.session = requests.Session()
    
    def ask(self, question: str) -> Dict[str, Any]:
        """
        Send a question to the SpaceX AI service
        
        Args:
            question: Natural language question about SpaceX
            
        Returns:
            Dictionary containing the answer and metadata
        """
        url = f"{self.base_url}/api/ask"
        headers = {"Content-Type": "application/json"}
        data = {"question": question}
        
        try:
            response = self.session.post(url, headers=headers, json=data)
            response.raise_for_status()
            return response.json()
        except requests.exceptions.RequestException as e:
            return {"error": str(e)}
    
    def health_check(self) -> Dict[str, str]:
        """Check if the service is healthy"""
        url = f"{self.base_url}/api/health"
        try:
            response = self.session.get(url)
            response.raise_for_status()
            return response.json()
        except requests.exceptions.RequestException as e:
            return {"error": str(e)}

# Example usage
if __name__ == "__main__":
    client = SpaceXAIClient()
    
    # Check service health
    health = client.health_check()
    print(f"Service status: {health}")
    
    # Ask questions
    questions = [
        "What was the latest SpaceX launch?",
        "Tell me about the Falcon 9 rocket",
        "What are the upcoming launches?"
    ]
    
    for question in questions:
        print(f"\n🚀 Question: {question}")
        result = client.ask(question)
        if "error" in result:
            print(f"❌ Error: {result['error']}")
        else:
            print(f"✅ Answer: {result['answer']}")
```

## Using with JavaScript

### Node.js Client

```javascript
const axios = require('axios');

class SpaceXAIClient {
  constructor(baseUrl = 'http://localhost:8080') {
    this.baseUrl = baseUrl;
    this.client = axios.create({
      baseURL: baseUrl,
      headers: { 'Content-Type': 'application/json' }
    });
  }

  async ask(question) {
    try {
      const response = await this.client.post('/api/ask', { question });
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }

  async healthCheck() {
    try {
      const response = await this.client.get('/api/health');
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
}

// Example usage
(async () => {
  const client = new SpaceXAIClient();
  
  // Check health
  const health = await client.healthCheck();
  console.log('Service status:', health);
  
  // Ask a question
  const result = await client.ask('What was the latest SpaceX launch?');
  console.log('Question:', result.question);
  console.log('Answer:', result.answer);
})();
```

### Browser JavaScript (Fetch API)

```javascript
async function askSpaceXAI(question) {
  try {
    const response = await fetch('http://localhost:8080/api/ask', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ question })
    });
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    return await response.json();
  } catch (error) {
    console.error('Error:', error);
    return { error: error.message };
  }
}

// Example usage
askSpaceXAI('Tell me about the Falcon 9 rocket')
  .then(result => {
    console.log('Question:', result.question);
    console.log('Answer:', result.answer);
  });
```

## Response Format

All `/ask` endpoint responses follow this format:

```json
{
  "question": "The original question",
  "answer": "The model's response based on SpaceX data"
}
```

## Error Handling

The API may return errors in these scenarios:

### Invalid Request

```json
{
  "timestamp": "2025-11-02T13:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Required request body is missing"
}
```

### Service Unavailable

```json
{
  "timestamp": "2025-11-02T13:00:00.000+00:00",
  "status": 503,
  "error": "Service Unavailable",
  "message": "Unable to connect to Ollama service"
}
```

## Best Practices

1. **Be Specific**: Ask clear, specific questions for better results
   - ❌ "Tell me about SpaceX"
   - ✅ "What was the payload of the latest Falcon 9 launch?"

2. **Use Context**: Provide context when asking follow-up questions
   - ✅ "Compare the payload capacity of Falcon 9 and Falcon Heavy"

3. **Handle Errors**: Always implement proper error handling in your client code

4. **Rate Limiting**: Be mindful of API rate limits when making multiple requests

5. **Caching**: Consider caching responses for frequently asked questions

## Troubleshooting

### Connection Refused

If you get "Connection refused" errors:
```bash
# Check if the service is running
curl http://localhost:8080/api/health

# Check Docker containers
docker ps | grep spacex-ai
```

### Slow Responses

Local model responses may take a few seconds, especially on the first request while the model loads into memory. For better UX:
- Show a loading indicator
- Set appropriate timeouts (30-60 seconds)
- Implement retry logic for transient failures

### Invalid Responses

If responses don't make sense:
- Verify the Ollama service is healthy (`curl http://localhost:11434/api/version`)
- Check the question is clear and specific
- Review the SpaceX API data availability
