FROM openjdk:8-jdk
COPY ./target/word-calculator-java-0.0.1-SNAPSHOT.jar /usr/src/wcj/
WORKDIR /usr/src/wcj
EXPOSE 8080
CMD ["java", "-jar", "word-calculator-java-0.0.1-SNAPSHOT.jar"]