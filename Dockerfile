FROM anapsix/alpine-java

LABEL maintainer="yahirzapatae@gmail.com"

COPY somafit-api-0.0.1-SNAPSHOT.jar app.jar

CMD ["java","-jar","somafit-api-0.0.1-SNAPSHOT.jar"]