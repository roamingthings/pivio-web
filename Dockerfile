FROM java:8-jre

EXPOSE 8080

ADD build/libs/view.jar /view.jar
ADD server_config.yaml /server_config.yaml

CMD ["java", "-jar", "/view.jar"]
