# created by the factor : Jan 29, 2024, 10:05:08 AM  
# ![RealWorld Example App](quarkus-logo.png)

> ### Quarkus Framework codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.

This codebase was created to demonstrate a fully fledged fullstack application built with [Quarkus](https://quarkus.io/)
including CRUD operations, authentication, routing, pagination, and more.

We've gone to great lengths to adhere to the Quarkus community styleguides & best practices.

For more information on how to this works with other frontends/backends, head over to
the [RealWorld](https://github.com/gothinkster/realworld) repo.

[![Java CI with Maven](https://github.com/diegocamara/realworld-api-quarkus/actions/workflows/maven.yml/badge.svg)](https://github.com/diegocamara/realworld-api-quarkus/actions/workflows/maven.yml)

# How it works

This application basically uses Quarkus Framework with Java 11 with some other modules known to development community:

* Hibernate 5
* Jackson for JSON
* H2 in memory database
* JPA Criteria
* Auth0 java-jwt

### Project structure:

```
application/            -> business orchestration layer
+-- web/                -> web layer models and resources
domain/                 -> core business implementation layer
+-- model/              -> core business entity models
+-- feature/            -> all features logic implementation
+-- validator/          -> model validation implementation 
+-- exception/          -> all business exceptions
infrastructure/         -> technical details layer
+-- configuration/      -> dependency injection configuration
+-- repository/         -> adapters for domain repositories
+-- provider/           -> adapters for domain providers
+-- web/                -> web layer infrastructure models and security
```

# Getting started
NOTE: 
1. Issues: Need install lombok plugin in Eclipse and config Annotation Processing, see https://projectlombok.org/setup/eclipse
   		   [ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.8.1:compile (default-compile) on project realworldapiservice: 
           Fatal error compiling: java.lang.IllegalAccessError: class lombok.javac.apt.LombokProcessor (in unnamed module @0x634de03e) cannot access class 
           com.sun.tools.javac.processing.JavacProcessingEnvironment (in module jdk.compiler) because module jdk.compiler does not export 
           com.sun.tools.javac.processing to unnamed module @0x634de03e 
   Solution: change lombok version in pom.xml file, version need same with plugin/installer
           : need clean the project
           : just run using java -jar lombok.jar (1.18.16) --> GUI installer lombok for Eclipse
2. add -Ddebug=5006 in ./mvnw compile quarkus:dev -Ddebug=5006
3. change default 8080 > quarkus.http.port=9000


### Update security executable for all shell script file
```shell
chmod +x ./mvnw
chmod +x ./src/main/postman/run-api-tests.sh
chmod +x ./src/main/postman/factor.run.tests.on.local.sh
chmod +x ./src/main/postman/factor.integrated.tests.sh
chmod +x ./src/main/k6/factor.run.tests.on.local.sh
chmod +x ./src/main/k6/factor.integrated.tests.sh
chmod +x ./src/main/docker/factor/integrated.cicd.native.sh
chmod +x ./src/main/zap/factor.run.tests.on.local.sh
chmod +x ./src/main/zap/factor.run.tests.on.docker.sh
chmod +x ./src/main/zap/factor.integrated.tests.sh
```


### Start local server

```shell
 ./mvnw compile quarkus:dev
```

for debuging, just run below command and use your IDE with remote debug configuration
```shell
 ./mvnw compile quarkus:dev -Ddebug=5006
```

The server should be running at http://localhost:8080

### Running the application tests

```shell
./mvnw test 
```

for debuging, just run below command and use your IDE with remote debug configuration
```shell
./mvnw -Dmaven.surefire.debug -Dmaven.surefire.timeout=1000 test
```

### Building jar file

```shell
./mvnw package
```

### Scan with sonarqube
```shell
./mvnw clean verify sonar:sonar -Dsonar.token=9753c01aeebd990f7e33d7aea8b4d02badae9e4d
```

### Building native executable (https://quarkus.io/guides/building-native-image)
NOTE:
GraalVM is necessary for building native executable, more information about setting up GraalVM can be found
in [Quarkus guides](https://quarkus.io/guides/)
and database engine need to be changed.
1. [error]: Build step io.quarkus.deployment.pkg.steps.NativeImageBuildStep#build threw an exception: java.lang.RuntimeException: Cannot find the `native-image` in the GRAALVM_HOME, JAVA_HOME and System PATH. Install it using `gu install native-image`
   [solution]: need to install the native-image component using the gu (GraalVM Updater) command (https://www.graalvm.org/) (https://www.graalvm.org/latest/docs/getting-started/macos/).
2. [error]: Build step io.quarkus.deployment.pkg.steps.NativeImageBuildStep#build threw an exception: java.lang.IllegalStateException: Out of date version of GraalVM detected: native-image 17.0.7 2023-04-18. Quarkus currently supports 22.0.0.2. Please upgrade GraalVM to this version.             
   [solution]: download and use graalvm on this link: GraalVM Community Components 23.0.0 (https://github.com/graalvm/graalvm-ce-builds/releases)
3. [error]: java.lang.IllegalAccessException: class io.quarkus.runtime.ResourceHelper cannot access class com.oracle.svm.core.jdk.Resources (in module org.graalvm.nativeimage.builder) because module org.graalvm.nativeimage.builder does not export com.oracle.svm.core.jdk to unnamed module @6d7ca82b
   [solution]: quarkus version and graalvm-ce version must compatible, For Quarkus 2.9.2, the compatible version of GraalVM is 21.3.0. Please note that the compatibility between Quarkus and GraalVM can vary with different versions 
4. Issues: in MacOS, build package with native is hang
   RCA   : Not compatible between Quarkus 3.5.x with Oracle GraalVM 20.0.1+9.1 
   Solution: https://quarkus.io/blog/quarkus-3-5-0-released/ ==> GraalVM/Mandrel for Java 21
           : https://github.com/graalvm/graalvm-ce-builds/releases 
           : https://www.graalvm.org/latest/docs/getting-started/macos/ (for MacOs)
           : # still error on MacOS M1 ARM6, need time to assessment
           : Build using docker container

```shell
./mvnw package -Pnative                      # still error on MacOS M1 ARM6, need time to assessment
./mvnw package -Pnative -DskipTests
./mvnw install -Dnative -DskipTests
```
### running native 

```shell
./target/{your-application-name}-runner      # still error on MacOS M1 ARM6, need time to assessment
./target/realworld-api-quarkus-runner
```

### Testing the native executable
```shell
./mvnw verify -Pnative                       # still error on MacOS M1 ARM6, need time to assessment
./mvnw verify -Pnative -Dquarkus.test.native-image-profile=test
./mvnw clean verify -Pnative -Dquarkus-profile=test
```

### Testing an existing native executable
```shell
./mvnw test-compile failsafe:integration-test -Pnative       # still error on MacOS M1 ARM6, need time to assessment
./mvnw test-compile failsafe:integration-test -Pnative -Pnative.image.path=target/realworld-api-quarkus-runner
```



# Functional Tests

### Starting the application manually, running all and individual postman collection tests by shell script
NOTE:
1. change port localhost if need it
2. install npx first
3. Issue: Invalid IP address: undefined
   RCA: https://github.com/postmanlabs/newman/issues/3104
   Solution: Upgrade Newman --> npm install -g newman@latest
           : localhost change to 127.0.0.1
```shell
./src/main/postman/run-api-tests.sh          # original, need to run first 
./src/main/postman/factor.run.tests.on.local.sh   # factor
```


### Starting the application automatically, running all and individual postman collection tests by shell script

```shell
./mvnw clean package -DskipTests
./src/main/postman/factor.integrated.tests.sh
```




# Performance Test

### Starting the application manually, running all and individual k6 collection tests by shell script
NOTE:
1. change port localhost if need it
2. install k6 first

```shell
./src/main/k6/factor.run.tests.on.local.sh
```
### Starting the application automatically, running all and individual k6 collection tests by shell script

```shell
./src/main/k6/factor.integrated.tests.sh
```


# Security Test
### Starting the application automatically, running zap tests by shell script

```shell
./mvnw clean package -DskipTests
./src/main/zap/factor.integrated.tests.sh
```


# Building docker image
NOTE:
1. [error]: MacOS: macos Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?
   [solution]:https://stackoverflow.com/questions/44084846/cannot-connect-to-the-docker-daemon-on-macos
2. Issues: com.oracle.svm.core.util.UserError$UserException: Discovered unresolved type during parsing: com.ibm.icu.text.Transliterator. This error is reported at image build time because class com.github.slugify.Slugify is registered for linking at image build time by command line and command line. Error encountered while parsing com.github.slugify.Slugify.lambda$slugify$2(Slugify.java:112)
   RCA: Missing dependency
   Solution: <dependency>
			   <groupId>com.ibm.icu</groupId>
			   <artifactId>icu4j</artifactId>
			   <version>4.8.1</version>
			</dependency>
3. Please always check .dockerignore file for included or excluded of the files or folder in process of build images
   [error]:failed to solve: failed to compute cache key: "/target/quarkus-app/quarkus" not found: not found
   [solution]:https://stackoverflow.com/questions/66146088/docker-failed-to-compute-cache-key-not-found-runs-fine-in-visual-studio 
4. Check ip address database in pod or docker container and modify application.properties
   a. As a pod: 
```shell
kubectl get pod <pod_id> -o jsonpath='{.status.podIP}'
```
   b. As docker container:
```shell
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <container_id_or_name>
```
5. Check the network being used between quarkus docker container and database are in same network name
```shell
docker network ls
docker inspect <network_id>
```
   
   
## 1. Docker Quarkus application in JVM mode
1. This Dockerfile is used in order to build a container that runs the Quarkus application in JVM mode
2. Before building the docker image run:

```shell
./mvnw package -DskipTests
```

3. Then, build the image with:

```shell
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/realworldapiservice-jvm .
```
or
```shell
docker build -f src/main/docker/factor/Dockerfile.jvm -t factordeveloperpublic/realworld-api-quarkus-jvm .
```

4. Check and verify the connectivity parameter between Docker Quarkus application and database
5. Then run the container using:

```shell
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
       --rm -p 8080:8080 quarkus/realworldapiservice-jvm
```
or 
```shell
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
       --rm -p 8080:8080 factordeveloperpublic/realworld-api-quarkus-jvm
```
6. More environment parameter can be found on src/main/docker/factor/Dockerfile.jvm file
7. You can run all script above all together as below script:
```shell
./mvnw package -DskipTests
docker build -f src/main/docker/factor/Dockerfile.jvm -t factordeveloperpublic/realworld-api-quarkus-jvm .
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
       --rm -p 8080:8080 factordeveloperpublic/realworld-api-quarkus-jvm
```

## 2. Docker Quarkus application in JVM mode with legacy jar file style
1. This Dockerfile is used in order to build a container that runs the Quarkus application in JVM mode.
2. Before building the container image run:
```shell
./mvnw package -Dquarkus.package.type=legacy-jar
```
or for skip the unit test
```shell
./mvnw package -Dquarkus.package.type=legacy-jar -DskipTests
```

3. Then, build the image with:
```shell
docker build -f src/main/docker/factor/Dockerfile.legacy-jar -t factordeveloperpublic/realworld-api-quarkus-legacy-jar .
```

4. Check and verify the connectivity parameter between Docker Quarkus application and database
5. Then run the container using:
```shell
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
           --rm -p 8080:8080 factordeveloperpublic/realworld-api-quarkus-legacy-jar
```

6. More environment parameter can be found on src/main/docker/factor/Dockerfile.legacy-jar file
7. You can run all script above all together as below script:
```shell
./mvnw package -Dquarkus.package.type=legacy-jar -DskipTests
docker build -f src/main/docker/factor/Dockerfile.legacy-jar -t factordeveloperpublic/realworld-api-quarkus-legacy-jar .
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
           --rm -p 8080:8080 factordeveloperpublic/realworld-api-quarkus-legacy-jar
```


## 3. Docker Quarkus application in native (no JVM) mode
1. This Dockerfile is used in order to build a container that runs the Quarkus application in native (no JVM) mode.
2. Building native inside the container image run: (Creating a Linux executable without GraalVM installed)
3. Based on https://quarkus.io/guides/building-native-image
4. For MacOs arm64, build native inside the docker (please check the image that compatible with your processor architecture):
```shell
./mvnw package -DskipTests -Pnative --offline -T 8 -Dquarkus.native.container-build=true \
                                    -Dquarkus.native.container-runtime=docker \
                                    -Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-mandrel-builder-image:23.1.1.0-Final-java21-arm64
```

5. NOTE:
   a. [error]: exec ./application: exec format error
      [solution]:https://stackoverflow.com/questions/71332558/quarkus-docker-run-exec-user-process-caused-exec-format-error
   b. Always check the .dockerignore, if has issues on 'not found' or 'not included' during docker build
   c. Make sure version compatibility between quarkus version and graalvm version, quarkus version and graalvm-ce version must compatible, For Quarkus 2.9.2, the compatible version of GraalVM is 21.3.0. Please note that the compatibility between Quarkus and GraalVM can vary with different versions 
   d. Recheck again the version of 'ubi-quarkus-mandrel-builder-image:21.3.6.0-Final-java11-arm64' on https://quay.io/repository/quarkus/ubi-quarkus-mandrel-builder-image?tab=tags and change it if necessary for compatibility
   e. In MacOs, don't use './mvnw package -Pnative' and build image that based on linux, it will error because different os platform

6. Then, build the image with: (choose one)
   a. registry.access.redhat.com/ubi8/ubi-minimal:8.6 (native)
```shell
docker build -f src/main/docker/factor/Dockerfile.native -t factordeveloperpublic/realworld-api-quarkus-native .
```
   b. quay.io/quarkus/quarkus-micro-image:2.0 (native-micro)
```shell
docker build -f src/main/docker/factor/Dockerfile.native-micro -t factordeveloperpublic/realworld-api-quarkus-native-micro .
```
   c. quay.io/quarkus/quarkus-distroless-image:2.0 (native-distroless)
```shell
docker build -f src/main/docker/factor/Dockerfile.native-distroless -t factordeveloperpublic/realworld-api-quarkus-native-distroless .
```


7. Then run the container using: (choose one)
   a. registry.access.redhat.com/ubi8/ubi-minimal:8.6 (native)
```shell
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
           --rm -p 8080:8080 factordeveloperpublic/realworld-api-quarkus-native
```
   b. quay.io/quarkus/quarkus-micro-image:2.0 (native-micro)
```shell
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
           --rm -p 8080:8080 factordeveloperpublic/realworld-api-quarkus-native-micro
```
   c. quay.io/quarkus/quarkus-distroless-image:2.0 (native-distroless)
```shell
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
           --rm -p 8080:8080 factordeveloperpublic/realworld-api-quarkus-native-distroless
```


8. You can run all script above all together as below script:
   a. registry.access.redhat.com/ubi8/ubi-minimal:8.6 (native)

```shell
./mvnw package -DskipTests -Pnative --offline -T 8 -Dquarkus.native.container-build=true \
                                    -Dquarkus.native.container-runtime=docker \
                                    -Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-mandrel-builder-image:23.1.1.0-Final-java21-arm64
docker build -f src/main/docker/factor/Dockerfile.native -t factordeveloperpublic/realworld-api-quarkus-native .
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
           --rm -p 8080:8080 factordeveloperpublic/realworld-api-quarkus-native
```

      b. quay.io/quarkus/quarkus-micro-image:2.0 (native-micro)

```shell
./mvnw package -DskipTests -Pnative --offline -T 8 -Dquarkus.native.container-build=true \
                                    -Dquarkus.native.container-runtime=docker \
                                    -Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-mandrel-builder-image:23.1.1.0-Final-java21-arm64
docker build -f src/main/docker/factor/Dockerfile.native-micro -t factordeveloperpublic/realworld-api-quarkus-native-micro .
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
           --rm -p 8080:8080 factordeveloperpublic/realworld-api-quarkus-native-micro
```

      c. quay.io/quarkus/quarkus-distroless-image:2.0 (native-distroless)

```shell
./mvnw package -DskipTests -Pnative --offline -T 8 -Dquarkus.native.container-build=true \
                                    -Dquarkus.native.container-runtime=docker \
                                    -Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-mandrel-builder-image:23.1.1.0-Final-java21-arm64
docker build -f src/main/docker/factor/Dockerfile.native-distroless -t factordeveloperpublic/realworld-api-quarkus-native-distroless .
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
           --rm -p 8080:8080 factordeveloperpublic/realworld-api-quarkus-native-distroless
```
    
## 4. Docker Quarkus application in native (no JVM) mode with multistage
1. This Dockerfile is used in order to build a container that runs the Quarkus application in native (no JVM) mode.
2. build the image with:
```shell
docker build -f src/main/docker/factor/Dockerfile.native.multistage -t factordeveloperpublic/realworld-api-quarkus-native-multistage .
```

3. Then run the container using:
```shell
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
                      --rm -p 8080:8080 factordeveloperpublic/realworld-api-quarkus-native-multistage
```
4. You can run all script above all together as below script:
```shell
docker build -f src/main/docker/factor/Dockerfile.native.multistage -t factordeveloperpublic/realworld-api-quarkus-native-multistage .
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
                      --rm -p 8080:8080 factordeveloperpublic/realworld-api-quarkus-native-multistage
```
5. Issues : qemu-x86_64: Could not open '/lib64/ld-linux-x86-64.so.2': No such file or directory
   RCA    : because using 23.1.1.0-Final-java21-amd64 image in MacOs M1 ARM64
   Solution: change the image into 23.1.1.0-Final-java21
                                            

# Building and run CICD
## local CICD using docker
1. Build stages with error handling : install -> test -> sonarcloud -> package -> native
2. Run app, database, postman, k6 and zap
3. Register in Docker Hub
4. Set isBuild=false, inside below script to bypass No.1 (Build stages)
```shell
export DOCKER_PASSWORD='publicQ!@#123'
export DOCKER_USER=factordeveloperpublic
./src/main/docker/factor/integrated.cicd.native.sh
```

## Jenkins pipeline CICD using host docker
1. Build image that has Jenkins (Debian 12) with docker
```shell
docker build -f src/main/docker/factor/Dockerfile.cicd.jenkins.with.docker -t factor-jenkins-docker .
```

2. Create directory in the host for container docker of Jenkins, so the container can mount, read and write the files that required
```shell
sudo mkdir -p /var/jenkins_home
sudo chmod ugo+w /var/jenkins_home
```

3. Modify volume that will be mounted, below is for MacOs or Linux based OS, and provide enough cpu and memory
```shell
docker run -it \
  -p 8081:8080 -p 50000:50000 \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v /var/jenkins_home:/var/jenkins_home \
  -e JAVA_OPTS="-Dorg.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL=86400" \
  --cpus=8 \
  --memory=6g \
  --memory-swap=8g \
  --memory-reservation=1g \
  --memory-swappiness=0 \
  --cpu-shares=1024 \
  factor-jenkins-docker
```

4. Configuring your Jenkins environment (Skip if already configured):
   a. Go to localhost:8081
   b. Get the admin password from the log returned and login
   c. In the following prompt, select Install suggested plugins and Docker Plugin. Jenkins will automatically install all plugins the Jenkins community finds most useful.
   d. After the installation, create a first admin user and finish the configuration.

5. Create a New Jenkins Pipeline:
   a. Click on "New Item" on the Jenkins dashboard.
   b. Enter a name for your project (e.g., Quarkus-Pipeline) and select "Pipeline" as the type.
   c. Click "OK" to create the pipeline.
   d. Configure Pipeline Settings:
      1. Scroll down to the "Pipeline" section and choose "Pipeline script from SCM" as the Definition.
      2. Select Git as the SCM.
      3. Enter the GitHub repository URL (https://github.com/firmansyah-github/quarkus-generated-test.git) in the Repository URL field.
      4. Set the appropriate credentials for accessing the repository.
      5. Specify the branch to build (e.g., main, master).
      6. Specify Jenkinsfile Path:
         a. In the "Script Path" field, enter the path to your Jenkinsfile relative to the repository root:
            src/main/docker/factor/Jenkinsfile.cicd.jenkins.with.docker
         b. Save the Pipeline Configuration:
   e. Click "Save" to save the pipeline configuration.

6. Run the Pipeline:
   a. Go back to the Jenkins dashboard and locate your newly created pipeline project.
   b. Click "Build Now" or trigger the pipeline manually to start the build process.
   c. Jenkins will retrieve the source code from the specified GitHub repository and execute the pipeline steps defined in the Jenkinsfile located at the provided path.
   d. Ensure that your Jenkinsfile (Jenkinsfile.cicd.jenkins.with.docker) contains the necessary stages, steps, and Docker-related configurations to build and deploy the Quarkus application according to your requirements.

#### Database changes can be made to the application.properties file.

```properties
# Database configuration
quarkus.datasource.db-kind=h2
quarkus.datasource.jdbc.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
quarkus.datasource.jdbc.driver=org.h2.Driver
quarkus.datasource.username=sa
quarkus.datasource.password=
```

## Help

Improvements are welcome, feel free to contribute.
