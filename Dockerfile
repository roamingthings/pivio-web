FROM java:8-jre

EXPOSE 8080

ADD build/libs/view-1.0.0.jar /view.jar

CMD ["java", "-jar", "/view.jar"]
