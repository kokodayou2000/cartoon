FROM  adoptopenjdk/openjdk11:jre11u-nightly
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-jar","/app.jar"]