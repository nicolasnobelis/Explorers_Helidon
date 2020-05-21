FROM alpine/git AS explorersModelGit
WORKDIR /app
RUN git clone https://github.com/nicolasnobelis/Explorers_Model.git

FROM maven:3.6-openjdk-11 AS explorersBuilt
WORKDIR /model
COPY --from=explorersModelGit /app/Explorers_Model /model
RUN mvn install

WORKDIR /helidon

## Create a first layer to cache the "Maven World" in the local repository.
## Incremental docker builds will always resume after that, unless you update
ADD pom.xml .
RUN mvn package -DskipTests
## Do the Maven build!
## Incremental docker builds will resume here when you change sources
ADD src src
RUN mvn package -DskipTests
RUN echo "done!"


## 2nd stage, build the runtime image
FROM openjdk:11-jre-slim
WORKDIR /app
#
## Copy the binary built in the 1st stage
COPY --from=explorersBuilt /helidon/target/explorers-helidon.jar ./
COPY --from=explorersBuilt /helidon/target/libs ./libs
#
CMD ["java", "-jar", "explorers-helidon.jar"]
#
EXPOSE 8080

