# syntax=docker/dockerfile:1

################################################################################
# 1. STAGE: DEPS - Descargar dependencias
################################################################################
FROM maven:3.9-eclipse-temurin-17 as deps

WORKDIR /build

COPY pom.xml .

# CORRECCIÓN:
# 1. Agregamos -Dmaven.resolver.transport=wagon (Obliga a usar el motor antiguo)
# 2. Pasamos las banderas SSL directamente en el comando (más seguro que MAVEN_OPTS)
RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline -DskipTests \
    -Dmaven.resolver.transport=wagon \
    -Dmaven.wagon.http.ssl.insecure=true \
    -Dmaven.wagon.http.ssl.allowall=true \
    -Dmaven.wagon.http.ssl.ignore.validity.dates=true

################################################################################
# 2. STAGE: PACKAGE - Compilar el código
################################################################################
FROM deps as package

WORKDIR /build

COPY ./src src/

# Aplicamos las mismas banderas aquí para la compilación
RUN --mount=type=cache,target=/root/.m2 \
    mvn package -DskipTests \
    -Dmaven.resolver.transport=wagon \
    -Dmaven.wagon.http.ssl.insecure=true \
    -Dmaven.wagon.http.ssl.allowall=true \
    -Dmaven.wagon.http.ssl.ignore.validity.dates=true && \
    mv target/*.jar target/app.jar

################################################################################
# 3. STAGE: EXTRACT - Separar en capas
################################################################################
FROM package as extract

WORKDIR /build

RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

################################################################################
# 4. STAGE: FINAL - Imagen ligera
################################################################################
FROM eclipse-temurin:17-jre-jammy AS final

ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

COPY --from=extract /build/target/extracted/dependencies/ ./
COPY --from=extract /build/target/extracted/spring-boot-loader/ ./
COPY --from=extract /build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract /build/target/extracted/application/ ./

EXPOSE 8080

ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ]