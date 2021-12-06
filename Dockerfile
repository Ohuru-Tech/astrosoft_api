# Use the official maven/Java 8 image to create a build artifact: https://hub.docker.com/_/maven
FROM maven:3.8.4-jdk-11 as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY lib ./lib

ARG ASTROSOFT_CORE_VERSION=1.1.1-SNAPSHOT

# Install maven repository locally
RUN mvn install:install-file \
    -Dfile=lib/AstrosoftCore-${ASTROSOFT_CORE_VERSION}.jar \
    -DgroupId=com.innovativeastrosolutions \
    -DartifactId=AstrosoftCore \
    -Dversion=${ASTROSOFT_CORE_VERSION} \
    -Dpackaging=jar

# Build a release artifact.
RUN mvn package -DskipTests

# Use the Official OpenJDK image for a lean production stage of our multi-stage build.
# https://hub.docker.com/_/openjdk
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM openjdk:11-jre

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/astrosoft_api-*.jar /astrosoft_api.jar

# Run the web service on container startup.
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/astrosoft_api.jar"]
