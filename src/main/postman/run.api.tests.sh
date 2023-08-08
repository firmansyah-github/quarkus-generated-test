#!/bin/bash
set -x

#SCRIPTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

# in Mac Os please make sure to use or to install coreutils: 
# https://apple.stackexchange.com/questions/135742/time-in-milliseconds-since-epoch-in-the-terminal
# PATH="/opt/homebrew/opt/coreutils/libexec/gnubin:$PATH"
LocalDateTimeNow=$(date +'%Y-%m-%dT%H:%M:%S.%6N')
# UNIQUE=$(date +'%Y%m%dT%H%M%S%6N')

# debug mode
# npx --node-options --inspect-brk ./target/reports run ...
# Connect debugger: Open Google Chrome or any Chromium-based browser and enter chrome://inspect in the address bar. Click on the "Open dedicated DevTools for Node" link
# Click the "Configure..." button in the top-right corner of the DevTools window that opens.


# HTML reporter
# npm install -g ./target/reports-reporter-html
# npm install -g ./target/reports-reporter-htmlextra
# issues: https://stackoverflow.com/questions/58760974/i-am-receiving-could-not-find-htmlextra-reporter-when-i-run-it-on-my-local-ma
# issues: https://github.com/DannyDainton/newman-reporter-htmlextra/issues/145

#APIURLVAR=${APIURL:-http://localhost:8080/api}
SCRIPTDIR=$(dirname "$(readlink -f "$0")")
if [ -z "$APIURL" ]; then
    APIURLVAR="http://localhost:8080/api"
else
    APIURLVAR="$APIURL"
fi

# Cleanup
rm -f ./target/postman/postman_complete

npx newman run $SCRIPTDIR/all.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/all.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/all.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/all.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/all.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/user.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/user.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/user.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/user.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/user.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/articles.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/articles.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/articles.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/articles.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/articles.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/comments.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/comments.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/comments.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/comments.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/comments.$LocalDateTimeNow.xml" 


npx newman run $SCRIPTDIR/follow_relationship.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/follow_relationship.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/follow_relationship.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/follow_relationship.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/follow_relationship.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/school.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/school.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/school.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/school.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/school.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/tag_relationship.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/tag_relationship.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/tag_relationship.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/tag_relationship.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/tag_relationship.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/tags.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/tags.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/tags.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/tags.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/tags.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/users.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/users.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/users.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/users.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/users.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/favorite_relationship.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/favorite_relationship.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/favorite_relationship.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/favorite_relationship.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/favorite_relationship.$LocalDateTimeNow.xml" 



touch ./target/postman/postman_complete
