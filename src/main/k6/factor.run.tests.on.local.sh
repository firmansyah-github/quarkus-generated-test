# created by the factor : Feb 23, 2024, 6:45:22 AM  
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

# create k6 directory in target folder
directory="target/k6/reports"

# Check if the directory exists
if [ -d "$directory" ]; then
    echo "Directory '$directory' already exists."
else
    # Create the directory
    mkdir -p "$directory"
    chmod -R uog+rw "$directory"
    echo "Directory '$directory' created."
fi

# Cleanup
rm -f ./target/k6/factor.k6_complete

# in Mac Os please make sure to use or to install coreutils: 
# https://apple.stackexchange.com/questions/135742/time-in-milliseconds-since-epoch-in-the-terminal
# PATH="/opt/homebrew/opt/coreutils/libexec/gnubin:$PATH"
LocalDateTimeNow=$(date +'%Y-%m-%dT%H-%M-%S.%6N')
# UNIQUE=$(date +'%Y%m%dT%H%M%S%6N')

# https://k6.io/blog/load-testing-with-postman-collections/
# 
# 1. Install k6: k6 supports various platforms, including Windows, Linux, macOS and docker
# brew install k6
#
# 2. Run k6 with various performance test scenario:

# ARTICLES
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.articles.k6.collection.js --env RPT=./target/k6/reports/factor.articles.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.articles.k6.collection.js --env RPT=./target/k6/reports/factor.articles.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.articles.k6.collection.js --env RPT=./target/k6/reports/factor.articles.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.articles.k6.collection.js --env RPT=./target/k6/reports/factor.articles.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.articles.k6.collection.js --env RPT=./target/k6/reports/factor.articles.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.articles.k6.collection.js --env RPT=./target/k6/reports/factor.articles.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# COMMENTS
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.comments.k6.collection.js --env RPT=./target/k6/reports/factor.comments.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.comments.k6.collection.js --env RPT=./target/k6/reports/factor.comments.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.comments.k6.collection.js --env RPT=./target/k6/reports/factor.comments.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.comments.k6.collection.js --env RPT=./target/k6/reports/factor.comments.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.comments.k6.collection.js --env RPT=./target/k6/reports/factor.comments.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.comments.k6.collection.js --env RPT=./target/k6/reports/factor.comments.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# FAVORITE_RELATIONSHIP
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.favorite_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.favorite_relationship.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.favorite_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.favorite_relationship.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.favorite_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.favorite_relationship.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.favorite_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.favorite_relationship.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.favorite_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.favorite_relationship.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.favorite_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.favorite_relationship.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# FOLLOW_RELATIONSHIP
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.follow_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.follow_relationship.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.follow_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.follow_relationship.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.follow_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.follow_relationship.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.follow_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.follow_relationship.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.follow_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.follow_relationship.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.follow_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.follow_relationship.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# TAG_RELATIONSHIP
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.tag_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.tag_relationship.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.tag_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.tag_relationship.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.tag_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.tag_relationship.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.tag_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.tag_relationship.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.tag_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.tag_relationship.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.tag_relationship.k6.collection.js --env RPT=./target/k6/reports/factor.tag_relationship.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# TAGS
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.tags.k6.collection.js --env RPT=./target/k6/reports/factor.tags.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.tags.k6.collection.js --env RPT=./target/k6/reports/factor.tags.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.tags.k6.collection.js --env RPT=./target/k6/reports/factor.tags.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.tags.k6.collection.js --env RPT=./target/k6/reports/factor.tags.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.tags.k6.collection.js --env RPT=./target/k6/reports/factor.tags.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.tags.k6.collection.js --env RPT=./target/k6/reports/factor.tags.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# USERS
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.users.k6.collection.js --env RPT=./target/k6/reports/factor.users.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.users.k6.collection.js --env RPT=./target/k6/reports/factor.users.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.users.k6.collection.js --env RPT=./target/k6/reports/factor.users.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.users.k6.collection.js --env RPT=./target/k6/reports/factor.users.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.users.k6.collection.js --env RPT=./target/k6/reports/factor.users.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.users.k6.collection.js --env RPT=./target/k6/reports/factor.users.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# USER
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.user.k6.collection.js --env RPT=./target/k6/reports/factor.user.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.user.k6.collection.js --env RPT=./target/k6/reports/factor.user.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.user.k6.collection.js --env RPT=./target/k6/reports/factor.user.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.user.k6.collection.js --env RPT=./target/k6/reports/factor.user.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.user.k6.collection.js --env RPT=./target/k6/reports/factor.user.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.user.k6.collection.js --env RPT=./target/k6/reports/factor.user.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# ALL
# Run a single VU, once.
k6 run $SCRIPTDIR/factor.all.k6.collection.js --env RPT=./target/k6/reports/factor.all.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.all.k6.collection.js --env RPT=./target/k6/reports/factor.all.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.all.k6.collection.js --env RPT=./target/k6/reports/factor.all.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.all.k6.collection.js --env RPT=./target/k6/reports/factor.all.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.all.k6.collection.js --env RPT=./target/k6/reports/factor.all.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.all.k6.collection.js --env RPT=./target/k6/reports/factor.all.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR


#while true; do                                                                               
#  docker restart eed862cbcce2fa06d3b61066ede57c14dc22dae188774a55fd7ad1f4f20c664a
#  sleep 10
#done

touch ./target/k6/factor.k6_complete
