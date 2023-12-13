#!/bin/bash

# Make the GET request and capture the JSON response
response=$(curl -s -X GET https://farmdemo.visiumlabs.com/api/devices?os=Android \
-H "Content-Type: application/json" \
-H "X-VisiumFarm-Api-Key: xmOo8Z5tp6.d9DQNq6nhbC9vxy2tDr32tfVBxUl6wKhpxSAiHtE")

# Extract the 'id' attributes from the JSON response and format output with double quotes
ids=$(echo "$response" | jq -r '.[].id' | awk '{ printf("\"%d\", ", $1) }' | sed 's/, $//')

# Output the extracted 'id' attributes in the required format
echo "$ids"