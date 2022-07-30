<div align="center">
    <h2>Image uploader API</h2>
    <p>API para upload e acesso de imagens.</p>
</div>

## Motivação
Resolver o desafio [image uploader](https://devchallenges.io/challenges/O2iGT9yBd6xZBrOcVirx) do site [DevChallenges](https://devchallenges.io/).

## Como executar o projeto?
### Iniciando banco de dados
A primeira coisa a ser feita é inicializar o banco, caso contrário a aplicação não irá 
inicializar.
```shell
docker-compose up
```
### Gerando o jar
```shell
mvn clean package
```
### Executando o projeto
```shell
java -jar target/*.jar
```

### Documentação
**Local**
```shell
localhost:8080/swagger-ui/index.html
```

## Informações extras
### Tecnologias usadas
- Java
- Spring, Spring Boot, Spring Data, Spring Web
- Postgres
- Flyway
- JUnit5
- Mockito
- Testcontainers

### Autor
- Linkedin - [PedroBicudo](https://www.linkedin.com/in/pedro-bicudo)