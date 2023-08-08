#!/bin/sh
set -x

# in Mac Os please make sure to use or to install coreutils: 
# https://apple.stackexchange.com/questions/135742/time-in-milliseconds-since-epoch-in-the-terminal
# PATH="/opt/homebrew/opt/coreutils/libexec/gnubin:$PATH"
LocalDateTimeNow=$(date +'%Y-%m-%dT%H:%M:%S.%6N')
# UNIQUE=$(date +'%Y%m%dT%H%M%S%6N')

#SCRIPTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
#APIURLVAR=${APIURL:-http://localhost:8080/api}
SCRIPTDIR=$(dirname "$(readlink -f "$0")")
if [ -z "$APIURL" ]; then
    APIURLVAR="http://192.168.1.10:8080/api"
else
    APIURLVAR="$APIURL"
fi

# stable for full image
# bare for small image
IMGTYPE=stable
HTMLREPORT=$LocalDateTimeNow.html
MDREPORT=$LocalDateTimeNow.md
XMLREPORT=$LocalDateTimeNow.xml
JSONREPORT=$LocalDateTimeNow.json
MINUTES=2

# ZAP - Baseline Scan
#docker run --rm -t ghcr.io/zaproxy/zaproxy:$IMGTYPE zap-baseline.py -t $APIURLVAR
docker run --rm --network factor-nt -v ./target/zap:/zap/wrk/:rw -t ghcr.io/zaproxy/zaproxy:$IMGTYPE zap-baseline.py \
    -t $APIURLVAR -g baseline-gen.conf -r rep.base.$HTMLREPORT -w rep.base.$MDREPORT -x rep.base.$XMLREPORT -J rep.base.$JSONREPORT \
    -m $MINUTES


# ZAP - Full Scan  
docker run --rm --network factor-nt -v ./target/zap:/zap/wrk/:rw -t ghcr.io/zaproxy/zaproxy:$IMGTYPE zap-full-scan.py \
    -t $APIURLVAR -g full-gen.conf -r rep.full.$HTMLREPORT -w rep.full.$MDREPORT -x rep.full.$XMLREPORT -J rep.full.$JSONREPORT 
    
#ZAP - API Scan
#-f format         openapi, soap, or graphql
docker run --rm --network factor-nt -v ./target/zap:/zap/wrk/:rw -t ghcr.io/zaproxy/zaproxy:$IMGTYPE zap-api-scan.py -f openapi \
    -t $APIURLVAR -g api-gen.conf -r rep.api.$HTMLREPORT -w rep.api.$MDREPORT -x rep.api.$XMLREPORT -J rep.api.$JSONREPORT     
    


touch ./target/zap/zap_complete
