# Use a smaller base image
FROM adoptopenjdk:11-jre-hotspot as build

# download maven dependencies first to take advantage of docker caching
COPY pom.xml                                     /cbioportal/
COPY version.sh                                  /cbioportal/
WORKDIR /cbioportal

COPY core/pom.xml                                   core/
COPY db-scripts/pom.xml                             db-scripts/
COPY model/pom.xml                                  model/
COPY persistence/pom.xml                            persistence/
COPY persistence/persistence-api/pom.xml            persistence/persistence-api/
COPY persistence/persistence-connections/pom.xml    persistence/persistence-connections/
COPY persistence/persistence-mybatis/pom.xml        persistence/persistence-mybatis/
COPY scripts/pom.xml                                scripts/
COPY security/pom.xml                               security/
COPY security/security-spring/pom.xml               security/security-spring/
COPY service/pom.xml                                service/
COPY web/pom.xml                                    web/
RUN for subproject in */.; do cp version.sh "$subproject"; cd "$subproject"; mvn dependency:go-offline --fail-never; cd ..; done
COPY portal/pom.xml                                 portal/
RUN mvn dependency:go-offline --fail-never

COPY $PWD/portal/src/main/docker /cbioportal/docker
COPY $PWD/requirements.txt /cbioportal/requirements.txt
# Use COPY with a list of files
COPY --from=build /cbioportal/portal/target/cbioportal*.war /cbioportal/app.war
COPY --from=build /cbioportal/portal/target/dependency/webapp-runner.jar /cbioportal/webapp-runner.jar
COPY --from=build /cbioportal/core /cbioportal/core
COPY --from=build /cbioportal/scripts /cbioportal/scripts
COPY --from=build /cbioportal/db-scripts /cbioportal/db-scripts

# add importer scripts to PATH for easy running in containers
RUN find /cbioportal/core/src/main/scripts/ -type f -executable \! -name '*.pl'  -print0 | xargs -0 -- ln -st /usr/local/bin

# put config files in this folder if you want to override config
ENV PORTAL_HOME=/cbioportal

RUN mkdir -p $PORTAL_WEB_HOME
RUN unzip /app.war -d  $PORTAL_WEB_HOME

# add entrypoint
COPY --from=build /cbioportal/docker/web-and-data/docker-entrypoint.sh /usr/local/bin/
ENTRYPOINT ["docker-entrypoint.sh"]
