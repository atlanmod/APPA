FROM java:8
EXPOSE 109 370 1099 5353 6446
ADD /target/appa-bootstrapper-0.0.1-SNAPSHOT.jar appa-bootstrapper.jar
ENTRYPOINT ["java","-jar","appa-bootstrapper.jar"]