# ===================================
# Etapa 1: Build (Construcción)
# ===================================
FROM eclipse-temurin:21-jdk-jammy AS builder

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Descargar dependencias (se cachea si pom.xml no cambia)
RUN ./mvnw dependency:go-offline

# Copiar código fuente
COPY src ./src

# Compilar y empaquetar la aplicación
RUN ./mvnw clean package -DskipTests

# ===================================
# Etapa 2: Runtime (Ejecución)
# ===================================
FROM eclipse-temurin:21-jre-jammy

# Crear usuario no privilegiado para seguridad
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Cambiar ownership al usuario no privilegiado
RUN chown -R appuser:appuser /app

# Cambiar al usuario no privilegiado
USER appuser

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]