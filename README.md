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
### Note Firmansyah 
1. Need install lombok plugin in Eclipse and config Annotation Processing, see https://projectlombok.org/setup/eclipse
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.8.1:compile (default-compile) on project realworldapiservice: 
Fatal error compiling: java.lang.IllegalAccessError: class lombok.javac.apt.LombokProcessor (in unnamed module @0x634de03e) cannot access class 
com.sun.tools.javac.processing.JavacProcessingEnvironment (in module jdk.compiler) because module jdk.compiler does not export 
com.sun.tools.javac.processing to unnamed module @0x634de03e -> [Help 1]
==> change lombok version in pom.xml file, version need same with plugin/installer
==> need clean the project
==> just run using java -jar lombok.jar (1.18.16) --> GUI installer lombok for Eclipse
2. add -Ddebug=5006 in ./mvnw compile quarkus:dev -Ddebug=5006
3. change default 8080 > quarkus.http.port=9000


### Start local server

```shell
 ./mvnw compile quarkus:dev
 ./mvnw compile quarkus:dev -Ddebug=5006
 ```

The server should be running at http://localhost:8080

### Running the application tests

```shell
./mvnw test 
./mvnw -Dmaven.surefire.debug -Dmaven.surefire.timeout=1000 test
```

# Functional Tests
### Starting the application manually, running all and individual postman collection tests by shell script
1. change port localhost if need it
2. install npx first

```shell
./src/main/postman/run.api.tests.sh
```
### Starting the application automatically, running all and individual postman collection tests by shell script

```shell
./src/main/postman/integrated.tests.sh
```

# Performance Test
### Starting the application manually, running all and individual k6 collection tests by shell script
1. change port localhost if need it
2. install k6 first

```shell
./src/main/k6/run.api.tests.sh
```
### Starting the application automatically, running all and individual k6 collection tests by shell script

```shell
./src/main/k6/integrated.tests.sh
```


# Security Test
### Starting the application manually, running zap tests by shell script

```shell
./src/main/zap/run.api.tests.sh
```
### Starting the application automatically, running zap tests by shell script

```shell
./src/main/zap/integrated.tests.sh
```


### Building jar file

```shell
./mvnw package
```

### Building native executable (https://quarkus.io/guides/building-native-image)

GraalVM is necessary for building native executable, more information about setting up GraalVM can be found
in [Quarkus guides](https://quarkus.io/guides/)
and database engine need to be changed.
1. [error]: Build step io.quarkus.deployment.pkg.steps.NativeImageBuildStep#build threw an exception: java.lang.RuntimeException: Cannot find the `native-image` in the GRAALVM_HOME, JAVA_HOME and System PATH. Install it using `gu install native-image`
   [solution]: need to install the native-image component using the gu (GraalVM Updater) command (https://www.graalvm.org/) (https://www.graalvm.org/latest/docs/getting-started/macos/).
2. [error]: Build step io.quarkus.deployment.pkg.steps.NativeImageBuildStep#build threw an exception: java.lang.IllegalStateException: Out of date version of GraalVM detected: native-image 17.0.7 2023-04-18. Quarkus currently supports 22.0.0.2. Please upgrade GraalVM to this version.             
   [solution]: download and use graalvm on this link: GraalVM Community Components 23.0.0 (https://github.com/graalvm/graalvm-ce-builds/releases)
3. [error]: java.lang.IllegalAccessException: class io.quarkus.runtime.ResourceHelper cannot access class com.oracle.svm.core.jdk.Resources (in module org.graalvm.nativeimage.builder) because module org.graalvm.nativeimage.builder does not export com.oracle.svm.core.jdk to unnamed module @6d7ca82b
   [solution]: quarkus version and graalvm-ce version must compatible, For Quarkus 2.9.2, the compatible version of GraalVM is 21.3.0. Please note that the compatibility between Quarkus and GraalVM can vary with different versions 
```shell
./mvnw package -Pnative
./mvnw package -Pnative -DskipTests
./mvnw install -Dnative -DskipTests
```
### running native 

```shell
./target/{your-application-name}-runner
./target/realworld-api-quarkus-runner
```

### Testing the native executable
```shell
./mvnw verify -Pnative
./mvnw verify -Pnative -Dquarkus.test.native-image-profile=test
./mvnw clean verify -Pnative -Dquarkus-profile=test
```

### Testing an existing native executable
```shell
./mvnw test-compile failsafe:integration-test -Pnative
./mvnw test-compile failsafe:integration-test -Pnative -Dnative.image.path=target/realworld-api-quarkus-runner
```

### Building docker image
1. [error]: MacOS: macos Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?
   [solution]:https://stackoverflow.com/questions/44084846/cannot-connect-to-the-docker-daemon-on-macos

Please read comment on each docker file on folder src/main/docker/

### Scan with sonarqube
```shell
./mvnw clean verify sonar:sonar -Dsonar.token=c8f07b8da132920eac5e3c9b92b34224739710d2
```

### local CICD using docker
```shell
./src/main/docker/Dockerfile.native.cicd.sh
```

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
