FROM maven:3.9.8-amazoncorretto-21

WORKDIR /app
COPY . .

CMD ["mvn", "clean", "install", "test", "-P run-selenoid-tests"]