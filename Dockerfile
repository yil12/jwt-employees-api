# Usar imagen base de Java 17
FROM eclipse-temurin:17-jdk-jammy

# Directorio dentro del contenedor
WORKDIR /app

# Copiar el .jar generado
COPY target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]