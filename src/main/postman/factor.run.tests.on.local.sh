# created by the factor : Dec 9, 2023, 9:19:14 AM  
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
rm -f ./target/postman/factor.postman_complete

npx newman run $SCRIPTDIR/factor.all.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/factor.all.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/factor.all.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/factor.all.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/factor.all.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/factor.user.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/factor.user.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/factor.user.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/factor.user.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/factor.user.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/factor.articles.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/factor.articles.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/factor.articles.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/factor.articles.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/factor.articles.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/factor.comments.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/factor.comments.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/factor.comments.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/factor.comments.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/factor.comments.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/factor.favorite_relationship.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/factor.favorite_relationship.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/factor.favorite_relationship.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/factor.favorite_relationship.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/factor.favorite_relationship.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/factor.follow_relationship.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/factor.follow_relationship.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/factor.follow_relationship.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/factor.follow_relationship.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/factor.follow_relationship.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/factor.tag_relationship.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/factor.tag_relationship.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/factor.tag_relationship.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/factor.tag_relationship.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/factor.tag_relationship.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/factor.tags.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/factor.tags.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/factor.tags.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/factor.tags.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/factor.tags.$LocalDateTimeNow.xml" 

npx newman run $SCRIPTDIR/factor.users.postman.collection.json \
  --global-var "APIURL=$APIURLVAR" \
  --delay-request 250 --timeout-request 2000 -n 2 --reporters cli,json,htmlextra,junit,html \
  --reporter-htmlextra-export "./target/postman/reports/factor.users.$LocalDateTimeNow.extra.html" \
  --reporter-html-export "./target/postman/reports/factor.users.$LocalDateTimeNow.html" \
  --reporter-json-export "./target/postman/reports/factor.users.$LocalDateTimeNow.json" \
  --reporter-junit-export "./target/postman/reports/factor.users.$LocalDateTimeNow.xml" 



touch ./target/postman/factor.postman_complete
