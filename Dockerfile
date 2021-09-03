FROM openjdk:11
RUN addgroup spring && adduser -ingroup spring spring
ARG WAR_FILE=/target/*.war
ARG DB_FILE=/data/myDB.mv.db
COPY ${WAR_FILE} app.war
COPY ${DB_FILE} /data/myDB.mv.db
RUN chmod -R 777 /data
USER spring:spring
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.war"]