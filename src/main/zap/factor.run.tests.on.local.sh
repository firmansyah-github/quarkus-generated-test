# created by the factor : Dec 7, 2023, 4:03:00 PM  
#!/bin/sh
set -x

# in Mac Os please make sure to use or to install coreutils: 
# https://apple.stackexchange.com/questions/135742/time-in-milliseconds-since-epoch-in-the-terminal
# PATH="/opt/homebrew/opt/coreutils/libexec/gnubin:$PATH"
LocalDateTimeNow=$(date +'%Y-%m-%dT%H:%M:%S.%6N')
# UNIQUE=$(date +'%Y%m%dT%H%M%S%6N')

SCRIPTDIR=$(dirname "$(readlink -f "$0")")
if [ -z "$APIURL" ]; then
    APIURLVAR="http://app:8080/api"
else
    APIURLVAR="$APIURL"
fi


HTMLREPORT=$LocalDateTimeNow.html
MDREPORT=$LocalDateTimeNow.md
XMLREPORT=$LocalDateTimeNow.xml
JSONREPORT=$LocalDateTimeNow.json
MINUTES=2


# ZAP - Baseline Scan
zap-baseline.py -t $APIURLVAR -g baseline-gen.conf -r rep.base.$HTMLREPORT -w rep.base.$MDREPORT -x rep.base.$XMLREPORT -J rep.base.$JSONREPORT -m $MINUTES

# ZAP - Full Scan
zap-full-scan.py -t $APIURLVAR -g full-gen.conf -r rep.full.$HTMLREPORT -w rep.full.$MDREPORT -x rep.full.$XMLREPORT -J rep.full.$JSONREPORT

# ZAP - API Scan
zap-api-scan.py -f openapi -t $APIURLVAR -g api-gen.conf -r rep.api.$HTMLREPORT -w rep.api.$MDREPORT -x rep.api.$XMLREPORT -J rep.api.$JSONREPORT


touch /zap/wrk/factor.zap_complete
