FROM openjdk:21

WORKDIR /usrapp/bin

ENV PORT 8080
ENV SPRING_DATASOURCE_URL jdbc:mysql://localhost:3306/properties?createDatabaseIfNotExist=true
#ENV SPRING_DATASOURCE_URL jdbc:mysql://13.218.247.45:3306/properties?createDatabaseIfNotExist=true
ENV SPRING_DATASOURCE_USERNAME root
ENV SPRING_DATASOURCE_PASSWORD secret

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency

CMD ["java","-cp","./classes:./dependency/*","com.edu.eci.arep.ArepTaller6SecureApplicationDesign"]