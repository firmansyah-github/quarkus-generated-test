# created by the factor : Dec 7, 2023, 4:03:00 PM  
#!/bin/bash
# docker run --name Postgres-test -e POSTGRES_PASSWORD=xWmGrW0hi4 -p 5432:5432 -d postgres

# Get the absolute path of the directory containing the script
script_dir="$(dirname "$(readlink -f "$0")")"

# Construct the path to quarkus-run.jar relative to the script directory
jar_path="$script_dir/../../../target/quarkus-app/quarkus-run.jar"

# Check if the JAR file exists before executing the tests
if [ -f "$jar_path" ]; then
    # Execute your tests using the "$jar_path" variable for the path to quarkus-run.jar
    # echo "Running tests using: $jar_path"
    # Your test commands here...
    java -jar "$jar_path" > factor.zap.service.log &
    SERVICE_PROCESS=$!
    tail -f -n0 factor.zap.service.log | grep -q 'Listening on'
    echo "Application started"
    ./src/main/zap/factor.run.tests.on.docker.sh     # Modify this line to provide the correct path if needed
    kill $SERVICE_PROCESS
    rm factor.zap.service.log
else
    echo "Error: quarkus-run.jar not found at $jar_path"
fi

