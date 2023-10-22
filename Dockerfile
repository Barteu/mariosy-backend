FROM amazoncorretto:17

ENV DB_PROVIDER=postgresql
ENV DB_PORT=5432
ENV DB_DATABASE_NAME=mariosy
ENV DB_USERNAME=postgresadmin

EXPOSE 8083
VOLUME /tmp
COPY target/*.jar mariosy-1.jar
ENTRYPOINT ["java","-jar","/mariosy-1.jar"]