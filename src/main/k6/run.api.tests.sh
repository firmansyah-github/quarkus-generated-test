#!/bin/sh
set -x

#SCRIPTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
#APIURLVAR=${APIURL:-http://localhost:8080/api}
SCRIPTDIR=$(dirname "$(readlink -f "$0")")
if [ -z "$APIURL" ]; then
    APIURLVAR="http://localhost:8080/api"
else
    APIURLVAR="$APIURL"
fi

# Cleanup
rm -f ./target/k6/k6_complete

# in Mac Os please make sure to use or to install coreutils: 
# https://apple.stackexchange.com/questions/135742/time-in-milliseconds-since-epoch-in-the-terminal
# PATH="/opt/homebrew/opt/coreutils/libexec/gnubin:$PATH"
LocalDateTimeNow=$(date +'%Y-%m-%dT%H:%M:%S.%6N')
# UNIQUE=$(date +'%Y%m%dT%H%M%S%6N')

# https://k6.io/blog/load-testing-with-postman-collections/
# 
# 1. Install k6: k6 supports various platforms, including Windows, Linux, macOS and docker
# brew install k6
#
# 2. Run k6 with various performance test scenario:

# ARTICLES
# Run a single VU, once.
#k6 run $SCRIPTDIR/articles.k6.collection.js --env RPT=./target/k6/reports/articles.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/articles.k6.collection.js --env RPT=./target/k6/reports/articles.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/articles.k6.collection.js --env RPT=./target/k6/reports/articles.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/articles.k6.collection.js --env RPT=./target/k6/reports/articles.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/articles.k6.collection.js --env RPT=./target/k6/reports/articles.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/articles.k6.collection.js --env RPT=./target/k6/reports/articles.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# COMMENTS
# Run a single VU, once.
#k6 run $SCRIPTDIR/comments.k6.collection.js --env RPT=./target/k6/reports/comments.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/comments.k6.collection.js --env RPT=./target/k6/reports/comments.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/comments.k6.collection.js --env RPT=./target/k6/reports/comments.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/comments.k6.collection.js --env RPT=./target/k6/reports/comments.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/comments.k6.collection.js --env RPT=./target/k6/reports/comments.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/comments.k6.collection.js --env RPT=./target/k6/reports/comments.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# FOLLOW_RELATIONSHIP
# Run a single VU, once.
#k6 run $SCRIPTDIR/follow_relationship.k6.collection.js --env RPT=./target/k6/reports/follow_relationship.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/follow_relationship.k6.collection.js --env RPT=./target/k6/reports/follow_relationship.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/follow_relationship.k6.collection.js --env RPT=./target/k6/reports/follow_relationship.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/follow_relationship.k6.collection.js --env RPT=./target/k6/reports/follow_relationship.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/follow_relationship.k6.collection.js --env RPT=./target/k6/reports/follow_relationship.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/follow_relationship.k6.collection.js --env RPT=./target/k6/reports/follow_relationship.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# SCHOOL
# Run a single VU, once.
#k6 run $SCRIPTDIR/school.k6.collection.js --env RPT=./target/k6/reports/school.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/school.k6.collection.js --env RPT=./target/k6/reports/school.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/school.k6.collection.js --env RPT=./target/k6/reports/school.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/school.k6.collection.js --env RPT=./target/k6/reports/school.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/school.k6.collection.js --env RPT=./target/k6/reports/school.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/school.k6.collection.js --env RPT=./target/k6/reports/school.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# TAG_RELATIONSHIP
# Run a single VU, once.
#k6 run $SCRIPTDIR/tag_relationship.k6.collection.js --env RPT=./target/k6/reports/tag_relationship.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/tag_relationship.k6.collection.js --env RPT=./target/k6/reports/tag_relationship.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/tag_relationship.k6.collection.js --env RPT=./target/k6/reports/tag_relationship.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/tag_relationship.k6.collection.js --env RPT=./target/k6/reports/tag_relationship.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/tag_relationship.k6.collection.js --env RPT=./target/k6/reports/tag_relationship.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/tag_relationship.k6.collection.js --env RPT=./target/k6/reports/tag_relationship.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# TAGS
# Run a single VU, once.
#k6 run $SCRIPTDIR/tags.k6.collection.js --env RPT=./target/k6/reports/tags.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/tags.k6.collection.js --env RPT=./target/k6/reports/tags.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/tags.k6.collection.js --env RPT=./target/k6/reports/tags.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/tags.k6.collection.js --env RPT=./target/k6/reports/tags.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/tags.k6.collection.js --env RPT=./target/k6/reports/tags.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/tags.k6.collection.js --env RPT=./target/k6/reports/tags.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# USERS
# Run a single VU, once.
#k6 run $SCRIPTDIR/users.k6.collection.js --env RPT=./target/k6/reports/users.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/users.k6.collection.js --env RPT=./target/k6/reports/users.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/users.k6.collection.js --env RPT=./target/k6/reports/users.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/users.k6.collection.js --env RPT=./target/k6/reports/users.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/users.k6.collection.js --env RPT=./target/k6/reports/users.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/users.k6.collection.js --env RPT=./target/k6/reports/users.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# FAVORITE_RELATIONSHIP
# Run a single VU, once.
#k6 run $SCRIPTDIR/favorite_relationship.k6.collection.js --env RPT=./target/k6/reports/favorite_relationship.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/favorite_relationship.k6.collection.js --env RPT=./target/k6/reports/favorite_relationship.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/favorite_relationship.k6.collection.js --env RPT=./target/k6/reports/favorite_relationship.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/favorite_relationship.k6.collection.js --env RPT=./target/k6/reports/favorite_relationship.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/favorite_relationship.k6.collection.js --env RPT=./target/k6/reports/favorite_relationship.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/favorite_relationship.k6.collection.js --env RPT=./target/k6/reports/favorite_relationship.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# USER
# Run a single VU, once.
#k6 run $SCRIPTDIR/user.k6.collection.js --env RPT=./target/k6/reports/user.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/user.k6.collection.js --env RPT=./target/k6/reports/user.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/user.k6.collection.js --env RPT=./target/k6/reports/user.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/user.k6.collection.js --env RPT=./target/k6/reports/user.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/user.k6.collection.js --env RPT=./target/k6/reports/user.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/user.k6.collection.js --env RPT=./target/k6/reports/user.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# ALL
# Run a single VU, once.
#k6 run $SCRIPTDIR/all.k6.collection.js --env RPT=./target/k6/reports/all.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/all.k6.collection.js --env RPT=./target/k6/reports/all.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/all.k6.collection.js --env RPT=./target/k6/reports/all.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/all.k6.collection.js --env RPT=./target/k6/reports/all.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/all.k6.collection.js --env RPT=./target/k6/reports/all.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/all.k6.collection.js --env RPT=./target/k6/reports/all.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR


#while true; do                                                                               
#  docker restart eed862cbcce2fa06d3b61066ede57c14dc22dae188774a55fd7ad1f4f20c664a
#  sleep 10
#done

touch ./target/k6/k6_complete
