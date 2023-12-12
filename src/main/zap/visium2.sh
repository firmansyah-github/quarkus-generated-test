# created by the factor : Dec 11, 2023, 6:10:51 PM  
#!/bin/sh
set -x

#!/bin/bash

curl -X POST https://farmdemo.visiumlabs.com/api/apk/install \
-H "Content-Type: application/json" \
-H "X-VisiumFarm-Api-Key: xmOo8Z5tp6.d9DQNq6nhbC9vxy2tDr32tfVBxUl6wKhpxSAiHtE" \
-d '{
  "appList": [
    {
      "appId": 3623201,
      "type": "Android"
    }
  ],
  "deviceList": [
    "9321f298"
  ]
}'

