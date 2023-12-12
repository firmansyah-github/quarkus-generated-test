# created by the factor : Dec 11, 2023, 6:10:51 PM  
#!/bin/sh
set -x

#!/bin/bash

# Set the necessary variables
API_KEY="xmOo8Z5tp6.d9DQNq6nhbC9vxy2tDr32tfVBxUl6wKhpxSAiHtE"
FILE_PATH="/var/jenkins_home/workspace/visium/Simple_Notepad_2.0.1_Apkpure.apk"
API_URL="https://farmdemo.visiumlabs.com/api/v1/apps"

# Make the POST request
curl -X POST "$API_URL" \
-H "X-VisiumFarm-Api-Key: $API_KEY" \
-F "file=@$FILE_PATH"
