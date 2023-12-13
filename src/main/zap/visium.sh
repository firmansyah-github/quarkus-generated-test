# created by the factor : Dec 11, 2023, 6:10:51 PM  
#!/bin/bash
set -x
# Set the necessary variables
API_KEY="xmOo8Z5tp6.d9DQNq6nhbC9vxy2tDr32tfVBxUl6wKhpxSAiHtE"
FILE_PATH="/var/jenkins_home/workspace/visium/Simple_Notepad_2.0.1_Apkpure.apk"
API_URL="https://farmdemo.visiumlabs.com/api/v1/apps"

# Make the POST request and capture the response
response=$(curl -s -X POST "$API_URL" \
-H "X-VisiumFarm-Api-Key: $API_KEY" \
-F "file=@$FILE_PATH")

# Extract the 'id' attribute using jq
#appId=$(echo "$response" | jq -r '.id')
# Extract the 'appId' attribute using grep, cut, and tr
#appId=$(echo "$response" | grep -o '"id":[0-9]*' | cut -d':' -f2 | tr -d '"')
appId=$(echo "$response" | grep -o '"id" : [0-9]*' | cut -d':' -f2 | tr -d ' ')


# Output the extracted 'appId'
echo "The extracted appId is: $appId"

export appId=$appId

curl -X POST https://farmdemo.visiumlabs.com/api/apk/install \
-H "Content-Type: application/json" \
-H "X-VisiumFarm-Api-Key: xmOo8Z5tp6.d9DQNq6nhbC9vxy2tDr32tfVBxUl6wKhpxSAiHtE" \
-d '{
  "appList": [
    {
      "appId": '"$appId"', 
      "type": "Android"
    }
  ],
  "deviceList": [
    "9321f298"
  ]
}'
