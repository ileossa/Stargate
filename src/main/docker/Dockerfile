FROM frolvlad/alpine-oraclejdk8:slim

#Install Spring boot artifact
VOLUME /tmp
ADD stargate-2.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]