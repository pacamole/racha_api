# ETAPA DE COMPILAÇÃO
FROM eclipse-temurin:21-jdk AS builder

# Workdir é o local onde o código será copiado e executado (container)
WORKDIR /app

# Copia arquivos do meu projeto para o container (computador | container)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Permite execução no servidor virtual
RUN chmod +x mvnw

# Baixa todas as dependências do projeto
RUN ./mvnw dependency:go-offline

COPY src ./src

# Compila o código e cria o .jar final
RUN ./mvnw clean package -DskipTests

# ETAPA DE EXECUÇÃO
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o .jar gerado na etapa anterior para o container de execução
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]