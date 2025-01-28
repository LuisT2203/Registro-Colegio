# Etapa 1: Compilación
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM openjdk:17.0.1-jdk-slim
# Instalar las dependencias necesarias
RUN apt-get update && apt-get install -y \
    libfreetype6 \
    fontconfig \
    && apt-get clean

COPY --from=build /target/Registro_Colegio-0.0.1-SNAPSHOT.jar Registro.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Registro.jar"]