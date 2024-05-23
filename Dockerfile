FROM java:11
EXPOSE 8081
ADD tweet-0.0.1-SNAPSHOT.jar tweet-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","tweet-0.0.1-SNAPSHOT.jar"]
