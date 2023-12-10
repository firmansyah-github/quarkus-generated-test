# created by the factor : Dec 11, 2023, 5:57:49 AM  
#!/bin/sh
set -x

# in Mac Os please make sure to use or to install coreutils: 
# https://apple.stackexchange.com/questions/135742/time-in-milliseconds-since-epoch-in-the-terminal
# PATH="/opt/homebrew/opt/coreutils/libexec/gnubin:$PATH"
LocalDateTimeNow=$(date +'%Y-%m-%dT%H:%M:%S.%6N')
# UNIQUE=$(date +'%Y%m%dT%H%M%S%6N')

# Capture the IP address of the en0 interface
ip_address=$(ipconfig getifaddr en0)

# Print the IP address
echo "The IP address is: $ip_address"

SCRIPTDIR=$(dirname "$(readlink -f "$0")")
if [ -z "$APIURL" ]; then
    APIURLVAR="http://$ip_address:8080/api"
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

# Get the absolute path of the directory containing the script
script_dir="$(dirname "$(readlink -f "$0")")"


# ZAP - Baseline Scan
#docker run --rm -t ghcr.io/zaproxy/zaproxy:$IMGTYPE zap-baseline.py -t $APIURLVAR
docker run --rm  -v $script_dir/../../../target/zap:/zap/wrk/:rw -t ghcr.io/zaproxy/zaproxy:$IMGTYPE zap-baseline.py \
    -t $APIURLVAR -g baseline-gen.conf -r rep.base.$HTMLREPORT -w rep.base.$MDREPORT -x rep.base.$XMLREPORT -J rep.base.$JSONREPORT \
    -m $MINUTES


# ZAP - Full Scan  
#docker run --rm  -v $script_dir/../../../target/zap:/zap/wrk/:rw -t ghcr.io/zaproxy/zaproxy:$IMGTYPE zap-full-scan.py \
#    -t $APIURLVAR -g full-gen.conf -r rep.full.$HTMLREPORT -w rep.full.$MDREPORT -x rep.full.$XMLREPORT -J rep.full.$JSONREPORT 
    
#ZAP - API Scan
#-f format         openapi, soap, or graphql
#docker run --rm  -v $script_dir/../../../target/zap:/zap/wrk/:rw -t ghcr.io/zaproxy/zaproxy:$IMGTYPE zap-api-scan.py -f openapi \
#    -t $APIURLVAR -g api-gen.conf -r rep.api.$HTMLREPORT -w rep.api.$MDREPORT -x rep.api.$XMLREPORT -J rep.api.$JSONREPORT     
    


touch $script_dir/../../../target/zap/factor.zap_complete
