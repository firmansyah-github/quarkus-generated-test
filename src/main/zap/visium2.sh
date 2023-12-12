# created by the factor : Dec 11, 2023, 6:10:51 PM  
#!/bin/sh
set -x

#!/bin/bash

curl -X POST \
-H "Content-Type: application/json" \
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
}' \
https://farmdemo.visiumlabs.com/api/apk/install

