FROM eclipse-temurin:17-jdk-jammy

# Add project directory
# COPY . /app

# Working dir is now the project dir
# WORKDIR /app

# RUN ./gradlew --refresh-dependencies

# RUN ./gradlew build -x test --no-daemon

EXPOSE 3000

ARG JAR_FILE=build/libs/synthea.jar
ADD ${JAR_FILE} app.jar

# Run the application
CMD ["java", "-jar" ,"/app.jar"]