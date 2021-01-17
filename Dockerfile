FROM tomcat:jdk11-openjdk
LABEL maintainer="pspos.developqkation@gmail.com"

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY build/libs/4-lab-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
