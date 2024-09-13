FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /secondchanceshop
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk AS deploy
WORKDIR /secondchanceshop
COPY --from=builder /secondchanceshop/target/SecondChanceShopBE-0.0.1-SNAPSHOT.jar /secondchanceshop/secondchanceshop.jar
ENTRYPOINT ["java","-jar","secondchanceshop.jar"]