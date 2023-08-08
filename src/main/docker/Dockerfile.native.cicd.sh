#!/bin/sh
set -e  # Exit on error

# Variables
IMAGE_NAME="firmansyahprofess/realworld-api-quarkus-native-cicd"
IMAGE_TAG="latest"
DOCKERFILE_PATH="src/main/docker/Dockerfile.native.cicd.static"

# Run stages with error handling : install -> test -> sonarcloud -> package -> native
# "install" "test" "sonarcloud" "package" "native"
for target in "native"; do
    if ! docker build -f "$DOCKERFILE_PATH" -t "$IMAGE_NAME:$IMAGE_TAG" --target "$target" .; then
        echo "Error: Stage $target failed"
        exit 1
    fi
done

# Cleanup
rm -f ./target/postman/postman_complete
rm -f ./target/k6/k6_complete

# Run app and services
docker-compose -f src/main/docker/Dockerfile.native.cicd.compose.yml up -d || { echo "Error: Failed to start services"; exit 1; }

# Wait for both signals
while ! ([ -e ./target/postman/postman_complete ] && [ -e ./target/k6/k6_complete ]); do
    sleep 1
done

# Run ZAP security scan with error handling
./src/main/zap/run.api.tests.sh || { echo "Error: ZAP security scan failed"; exit 1; }

# Shutdown containers
docker-compose -f src/main/docker/Dockerfile.native.cicd.compose.yml down

# Cleanup
rm -f ./target/postman/postman_complete
rm -f ./target/k6/k6_complete

# Push to Docker Hub with error handling
docker login || { echo "Error: Docker login failed"; exit 1; }
docker tag "$IMAGE_NAME:$IMAGE_TAG" "$IMAGE_NAME:1.0.0" || { echo "Error: Failed to tag image"; exit 1; }
docker push "$IMAGE_NAME:$IMAGE_TAG" || { echo "Error: Failed to push image"; exit 1; }
#docker push "$IMAGE_NAME:1.0.0" || { echo "Error: Failed to push image"; exit 1; }

# All stages completed successfully
echo "Pipeline completed successfully"

