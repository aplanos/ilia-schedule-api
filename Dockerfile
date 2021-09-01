FROM openjdk:11
RUN addgroup spring && adduser -ingroup spring spring
USER spring:spring
ARG WAR_FILE=/target/*.war
COPY ${WAR_FILE} app.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.war"]