# created by the factor : Jan 29, 2024, 10:05:08 AM  
# Use the official Postman Newman image as the base
FROM postman/newman:alpine

FROM ghcr.io/zaproxy/zaproxy:stable

# Set the working directory
WORKDIR /app

# Set the entrypoint to run the script (optional)
ENTRYPOINT ["/bin/sh","./zap/factor.run.tests.on.local.sh"]