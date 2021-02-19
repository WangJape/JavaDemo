FROM java:8
COPY *.jar /demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/demo.jar"]
CMD ["--server.port=8080"]