FROM java:8-jre

EXPOSE 8080

ADD build/libs/view.jar /view.jar

CMD ["java", "-jar", "/view.jar"]
