mvn clean package && \
cp -fr target/songsWS.war /opt/apache-tomcat-9.0.46/webapps/ && \
/opt/apache-tomcat-9.0.46/bin/shutdown.sh && \
/opt/apache-tomcat-9.0.46/bin/startup.sh