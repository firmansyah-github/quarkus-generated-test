# created by the factor : Dec 14, 2023, 6:54:47 AM  
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



# Wait for all signals
while true; do
    postman_complete="./target/postman/factor.postman_complete"
    k6_complete="./target/k6/factor.k6_complete"
    zap_complete="./target/zap/factor.zap_complete"

    postman_exists=$(if [ -e "$postman_complete" ]; then echo "true"; else echo "false"; fi)
    k6_exists=$(if [ -e "$k6_complete" ]; then echo "true"; else echo "false"; fi)
    zap_exists=$(if [ -e "$zap_complete" ]; then echo "true"; else echo "false"; fi)

    echo "Is Postman complete: $postman_exists"
    echo "Is k6 complete: $k6_exists"
    echo "Is Zap complete: $zap_exists"
    
    if [ "$postman_exists" = "false" ]; then
    	echo "--------10 last log line of POSTMAN---------------"
    	docker logs -n 10 postman-node
    	echo "--------------------------------------------------"    
    fi
    
    if [ "$k6_exists" = "false" ]; then
    	echo "--------10 last log line of K6---------------"
    	docker logs -n 10 k6-node
    	echo "--------------------------------------------------"    
    fi
    
    if [ "$zap_exists" = "false" ]; then
    	echo "--------10 last log line of ZAP---------------"
    	docker logs -n 10 zap-runner
    	echo "--------------------------------------------------"    
    fi

    if [ "$postman_exists" = "true" ] && [ "$k6_exists" = "true" ] && [ "$zap_exists" = "true" ]; then
        break
    else
        sleep 1
    fi
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

