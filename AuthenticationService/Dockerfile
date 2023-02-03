FROM openjdk:8-jre-slim
RUN groupadd -g 12000 authentication && \
    useradd -r -u 11000 -g authentication authentication
USER 11000
VOLUME /tmp
EXPOSE 8089
EXPOSE 8080
WORKDIR /home/hotel
ADD target/hotel-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /home/hotel/app.jar"]