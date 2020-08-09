FROM anapsix/alpine-java:8u202b08_server-jre_unlimited
RUN apk update
RUN apk add curl

VOLUME /tmp
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.paul.spark.SparkApplication"]

EXPOSE 4201
