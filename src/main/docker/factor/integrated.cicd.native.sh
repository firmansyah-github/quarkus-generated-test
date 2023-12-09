# created by the factor : Dec 9, 2023, 9:10:32 AM  
#!/bin/sh
set -e  # Exit on error

# Variables
IMAGE_NAME="factordeveloperpublic/realworld-api-quarkus-native-cicd"
IMAGE_TAG="latest"
DOCKERFILE_PATH="src/main/docker/factor/Dockerfile.cicd.app.native"

# Build stages with error handling : install -> test -> sonarcloud -> package -> native
# "install" "test" "sonarcloud" "package" "native"

isBuild=true

if [ "$isBuild" = true ]; then
	for target in "native"; do
    	if ! docker build -f "$DOCKERFILE_PATH" -t "$IMAGE_NAME:$IMAGE_TAG" --target "$target" .; then
        	echo "Error: Stage $target failed"
        	exit 1
    	fi
	done
fi

# Cleanup
rm -f ./target/postman/factor.postman_complete
rm -f ./target/k6/factor.k6_complete
rm -f ./target/zap/factor.zap_complete

# Run app, database, postman, k6 and zap
docker-compose -f src/main/docker/factor/integrated.cicd.native.compose.yml up -d || { echo "Error: Failed to start services"; exit 1; }

docker logs -f factor_postman-node_1
# Wait for both signals
while ! ([ -e ./target/postman/factor.postman_complete ] && [ -e ./target/k6/factor.k6_complete ]  && [ -e ./target/zap/factor.zap_complete ]); do
    sleep 1
done


# Shutdown containers
docker-compose -f src/main/docker/factor/integrated.cicd.native.compose.yml down

# Cleanup
rm -f ./target/postman/factor.postman_complete
rm -f ./target/k6/factor.k6_complete
rm -f ./target/zap/factor.zap_complete

# Push to Docker Hub with error handling
echo "$DOCKER_PASSWORD" | docker login --username $DOCKER_USER --password-stdin
# docker login || { echo "Error: Docker login failed"; exit 1; }
docker tag "$IMAGE_NAME:$IMAGE_TAG" "$IMAGE_NAME:$IMAGE_TAG"  || { echo "Error: Failed to tag image"; exit 1; }
docker push "$IMAGE_NAME:$IMAGE_TAG" || { echo "Error: Failed to push image"; exit 1; }

# All stages completed successfully
echo "Pipeline completed successfully"

