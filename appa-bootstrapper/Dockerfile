FROM java:8
EXPOSE 109 370 5353
ADD /target/appa-bootstrapper-0.0.1-SNAPSHOT.jar appa-bootstrapper.jar
ENTRYPOINT ["java","-jar","appa-bootstrapper.jar"]