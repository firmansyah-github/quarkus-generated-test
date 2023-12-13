#!/bin/bash
set -x

# step 1: check and install jq
# Check if jq is installed
if ! command -v jq &> /dev/null; then
    echo "jq is not installed. Installing..."
    
    # Update package lists and install jq
    apt-get update
    apt-get install -y jq

    # Check installation success
    if command -v jq &> /dev/null; then
        echo "jq has been successfully installed."
    else
        echo "Failed to install jq. Please install it manually."
    fi
else
    echo "jq is already installed."
fi


# step 2: get all devide id of Android
#!/bin/bash

# Your JSON data containing the array of objects
json_data='[
  {
    "id": 779978,
    "groups": [],
    "deviceId": "52faf304d5147795cac1003572fb3b50d74ef5d4",
    "deviceModel": "iPhone 8",
    "brand": "Apple",
    "os": "IOS",
    "oSVersion": "15.0.2",
    "screenSize": "4.7\"",
    "screenResolution": "750x1334",
    "googleService": false,
    "macAddress": "d0:2b:20:c3:9a:5d",
    "clientIp": "192.168.1.62",
    "marketName": "",
    "specificName": "",
    "categories": [],
    "deviceStatus": 0,
    "status": {
      "Automation": false,
      "Development": false,
      "Manual": false,
      "Reserved": false
    },
    "users": [],
    "offlineReservedInfo": null,
    "reservableGroups": [],
    "bookable": true,
    "favorite": false
  },
  {
    "id": 820,
    "groups": [],
    "deviceId": "9321f298",
    "deviceModel": "SM-J610F",
    "brand": "samsung",
    "os": "ANDROID",
    "oSVersion": "8.1.0",
    "screenSize": "6\"",
    "screenResolution": "720x1480",
    "googleService": true,
    "macAddress": "08:ae:d6:9c:0a:6b",
    "clientIp": "192.168.1.54",
    "marketName": "",
    "specificName": "",
    "categories": [],
    "deviceStatus": 0,
    "status": {
      "Automation": false,
      "Development": false,
      "Manual": true,
      "Reserved": false
    },
    "users": [
      {
        "fullName": "Brian Rakhmat Aji",
        "userName": "brian",
        "usageType": [
          "Manual"
        ]
      }
    ],
    "offlineReservedInfo": null,
    "reservableGroups": [],
    "bookable": true,
    "favorite": false
  },
  {
    "id": 222,
    "groups": [],
    "deviceId": "3b910746a893bfff0ffb98026b47239f0776bcee",
    "deviceModel": "iPhone 6",
    "brand": "Apple",
    "os": "IOS",
    "oSVersion": "12.3.1",
    "screenSize": "4.7\"",
    "screenResolution": "750x1334",
    "googleService": false,
    "macAddress": "4c:7c:5f:0c:b9:28",
    "clientIp": "192.168.1.62",
    "marketName": "",
    "specificName": "",
    "categories": [],
    "deviceStatus": 0,
    "status": {
      "Automation": false,
      "Development": false,
      "Manual": false,
      "Reserved": false
    },
    "users": [],
    "offlineReservedInfo": null,
    "reservableGroups": [],
    "bookable": true,
    "favorite": false
  },
  {
    "id": 706449,
    "groups": [],
    "deviceId": "52003cffeced945f",
    "deviceModel": "SM-A520F",
    "brand": "samsung",
    "os": "ANDROID",
    "oSVersion": "8.0.0",
    "screenSize": "5.2\"",
    "screenResolution": "1080x1920",
    "googleService": true,
    "macAddress": "unknown",
    "clientIp": "192.168.1.54",
    "marketName": "",
    "specificName": "",
    "categories": [],
    "deviceStatus": 0,
    "status": {
      "Automation": false,
      "Development": false,
      "Manual": true,
      "Reserved": false
    },
    "users": [
      {
        "fullName": "Mehmet DAGLI",
        "userName": "mdagli",
        "usageType": [
          "Manual"
        ]
      }
    ],
    "offlineReservedInfo": null,
    "reservableGroups": [],
    "bookable": true,
    "favorite": false
  }
]'

# Extract the 'id' attribute from each object in the array
#ids=$(echo "$json_data" | jq -r '.[] | .id')
# Extract 'id' attribute from each JSON object and format output
ids=$(echo "$json_data" | jq -r '.[].id' | paste -sd "," -)

# Output the extracted 'id' attributes in the required format
echo "$ids"

# Output the extracted IDs
echo "Extracted IDs:"
echo "$ids"
