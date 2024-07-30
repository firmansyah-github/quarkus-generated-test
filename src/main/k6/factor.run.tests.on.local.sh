# created by the factor : May 30, 2024, 6:48:44 AM  
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

# CATEGORIES
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.categories.k6.collection.js --env RPT=./target/k6/reports/factor.categories.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.categories.k6.collection.js --env RPT=./target/k6/reports/factor.categories.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.categories.k6.collection.js --env RPT=./target/k6/reports/factor.categories.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.categories.k6.collection.js --env RPT=./target/k6/reports/factor.categories.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.categories.k6.collection.js --env RPT=./target/k6/reports/factor.categories.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.categories.k6.collection.js --env RPT=./target/k6/reports/factor.categories.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

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

# CUSTOMER_CUSTOMER_DEMO
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.customer_customer_demo.k6.collection.js --env RPT=./target/k6/reports/factor.customer_customer_demo.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.customer_customer_demo.k6.collection.js --env RPT=./target/k6/reports/factor.customer_customer_demo.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.customer_customer_demo.k6.collection.js --env RPT=./target/k6/reports/factor.customer_customer_demo.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.customer_customer_demo.k6.collection.js --env RPT=./target/k6/reports/factor.customer_customer_demo.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.customer_customer_demo.k6.collection.js --env RPT=./target/k6/reports/factor.customer_customer_demo.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.customer_customer_demo.k6.collection.js --env RPT=./target/k6/reports/factor.customer_customer_demo.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# CUSTOMER_DEMOGRAPHICS
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.customer_demographics.k6.collection.js --env RPT=./target/k6/reports/factor.customer_demographics.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.customer_demographics.k6.collection.js --env RPT=./target/k6/reports/factor.customer_demographics.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.customer_demographics.k6.collection.js --env RPT=./target/k6/reports/factor.customer_demographics.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.customer_demographics.k6.collection.js --env RPT=./target/k6/reports/factor.customer_demographics.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.customer_demographics.k6.collection.js --env RPT=./target/k6/reports/factor.customer_demographics.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.customer_demographics.k6.collection.js --env RPT=./target/k6/reports/factor.customer_demographics.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# CUSTOMERS
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.customers.k6.collection.js --env RPT=./target/k6/reports/factor.customers.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.customers.k6.collection.js --env RPT=./target/k6/reports/factor.customers.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.customers.k6.collection.js --env RPT=./target/k6/reports/factor.customers.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.customers.k6.collection.js --env RPT=./target/k6/reports/factor.customers.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.customers.k6.collection.js --env RPT=./target/k6/reports/factor.customers.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.customers.k6.collection.js --env RPT=./target/k6/reports/factor.customers.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# EMPLOYEE_TERRITORIES
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.employee_territories.k6.collection.js --env RPT=./target/k6/reports/factor.employee_territories.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.employee_territories.k6.collection.js --env RPT=./target/k6/reports/factor.employee_territories.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.employee_territories.k6.collection.js --env RPT=./target/k6/reports/factor.employee_territories.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.employee_territories.k6.collection.js --env RPT=./target/k6/reports/factor.employee_territories.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.employee_territories.k6.collection.js --env RPT=./target/k6/reports/factor.employee_territories.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.employee_territories.k6.collection.js --env RPT=./target/k6/reports/factor.employee_territories.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

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

# ORDER_DETAILS
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.order_details.k6.collection.js --env RPT=./target/k6/reports/factor.order_details.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.order_details.k6.collection.js --env RPT=./target/k6/reports/factor.order_details.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.order_details.k6.collection.js --env RPT=./target/k6/reports/factor.order_details.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.order_details.k6.collection.js --env RPT=./target/k6/reports/factor.order_details.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.order_details.k6.collection.js --env RPT=./target/k6/reports/factor.order_details.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.order_details.k6.collection.js --env RPT=./target/k6/reports/factor.order_details.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# ORDERS
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.orders.k6.collection.js --env RPT=./target/k6/reports/factor.orders.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.orders.k6.collection.js --env RPT=./target/k6/reports/factor.orders.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.orders.k6.collection.js --env RPT=./target/k6/reports/factor.orders.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.orders.k6.collection.js --env RPT=./target/k6/reports/factor.orders.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.orders.k6.collection.js --env RPT=./target/k6/reports/factor.orders.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.orders.k6.collection.js --env RPT=./target/k6/reports/factor.orders.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# PRODUCTS
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.products.k6.collection.js --env RPT=./target/k6/reports/factor.products.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.products.k6.collection.js --env RPT=./target/k6/reports/factor.products.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.products.k6.collection.js --env RPT=./target/k6/reports/factor.products.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.products.k6.collection.js --env RPT=./target/k6/reports/factor.products.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.products.k6.collection.js --env RPT=./target/k6/reports/factor.products.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.products.k6.collection.js --env RPT=./target/k6/reports/factor.products.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# REGION
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.region.k6.collection.js --env RPT=./target/k6/reports/factor.region.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.region.k6.collection.js --env RPT=./target/k6/reports/factor.region.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.region.k6.collection.js --env RPT=./target/k6/reports/factor.region.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.region.k6.collection.js --env RPT=./target/k6/reports/factor.region.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.region.k6.collection.js --env RPT=./target/k6/reports/factor.region.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.region.k6.collection.js --env RPT=./target/k6/reports/factor.region.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# SHIPPERS
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.shippers.k6.collection.js --env RPT=./target/k6/reports/factor.shippers.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.shippers.k6.collection.js --env RPT=./target/k6/reports/factor.shippers.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.shippers.k6.collection.js --env RPT=./target/k6/reports/factor.shippers.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.shippers.k6.collection.js --env RPT=./target/k6/reports/factor.shippers.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.shippers.k6.collection.js --env RPT=./target/k6/reports/factor.shippers.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.shippers.k6.collection.js --env RPT=./target/k6/reports/factor.shippers.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# SUPPLIERS
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.suppliers.k6.collection.js --env RPT=./target/k6/reports/factor.suppliers.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.suppliers.k6.collection.js --env RPT=./target/k6/reports/factor.suppliers.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.suppliers.k6.collection.js --env RPT=./target/k6/reports/factor.suppliers.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.suppliers.k6.collection.js --env RPT=./target/k6/reports/factor.suppliers.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.suppliers.k6.collection.js --env RPT=./target/k6/reports/factor.suppliers.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.suppliers.k6.collection.js --env RPT=./target/k6/reports/factor.suppliers.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

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

# TERRITORIES
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.territories.k6.collection.js --env RPT=./target/k6/reports/factor.territories.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.territories.k6.collection.js --env RPT=./target/k6/reports/factor.territories.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.territories.k6.collection.js --env RPT=./target/k6/reports/factor.territories.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.territories.k6.collection.js --env RPT=./target/k6/reports/factor.territories.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.territories.k6.collection.js --env RPT=./target/k6/reports/factor.territories.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.territories.k6.collection.js --env RPT=./target/k6/reports/factor.territories.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

# US_STATES
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.us_states.k6.collection.js --env RPT=./target/k6/reports/factor.us_states.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.us_states.k6.collection.js --env RPT=./target/k6/reports/factor.us_states.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.us_states.k6.collection.js --env RPT=./target/k6/reports/factor.us_states.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.us_states.k6.collection.js --env RPT=./target/k6/reports/factor.us_states.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.us_states.k6.collection.js --env RPT=./target/k6/reports/factor.us_states.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.us_states.k6.collection.js --env RPT=./target/k6/reports/factor.us_states.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

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

# EMPLOYEES
# Run a single VU, once.
#k6 run $SCRIPTDIR/factor.employees.k6.collection.js --env RPT=./target/k6/reports/factor.employees.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run a single VU, 10 times.
#k6 run -i 10 $SCRIPTDIR/factor.employees.k6.collection.js --env RPT=./target/k6/reports/factor.employees.1VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs, splitting 10 iterations between them.
#k6 run -u 5 -i 10 $SCRIPTDIR/factor.employees.k6.collection.js --env RPT=./target/k6/reports/factor.employees.5VU.10x.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Run 5 VUs for 10s.
#k6 run -u 5 -d 10s $SCRIPTDIR/factor.employees.k6.collection.js --env RPT=./target/k6/reports/factor.employees.5VU.1x.10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Ramp VUs from 0 to 100 over 10s, stay there for 60s, then 10s down to 0.
#k6 run -u 0 -s 10s:100 -s 60s:100 -s 10s:0 $SCRIPTDIR/factor.employees.k6.collection.js --env RPT=./target/k6/reports/factor.employees.0VU.100VU10s.100VU60s.0VU10s.$LocalDateTimeNow.html --env URL=$APIURLVAR

  # Send metrics to an influxdb server
##k6 run -o influxdb=http://1.2.3.4:8086/k6 $SCRIPTDIR/factor.employees.k6.collection.js --env RPT=./target/k6/reports/factor.employees.1VU.1x.$LocalDateTimeNow.html --env URL=$APIURLVAR

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
