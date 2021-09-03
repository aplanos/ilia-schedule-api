# Projeto Bate Ponto Teste

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Geral

Projeto de teste de competencias

Web API para controle de ponto, seguindo o contrato, que permite realizar as seguintes ações:

Registrar os horários da jornada diária de trabalho.

- Apenas 4 horários podem ser registrados por dia.
- Deve haver no mínimo 1 hora de almoço. 
- Sábado e domingo não são permitidos como dia de trabalho.


## Rodando o projeto 

### Linha de comandos

```console

./mvnw spring-boot:run

```

### Docker compose 

```console

./mvnw package
docker-composer up --build

```

## Provando o projeto

Caso tenha rodado o projeto usando a linha de comando encontrará disponível:

- Swagger UI (http://localhost:8080/swagger-ui/)
- Spring Boot Admin (http://localhost:8080/applications)

Caso tenha utilizado o docker compose, as urls são as mesmas, só precisa mudar a porta para **`5000`**

## Branches

Tem dois branches

- master (default - estável)
- staging (desenvolvimento)

## Testes

Foram feitos teste de integração, de repositório e de unidade. Para validiar a cobertura de teste foi adicionado o plugin JaCoCo, é possível gerar o relatório de cobertura executando:

```console

./mvnw clean
./mvnw jacoco:report

```

Após a geração do relatório de cobertura de teste, é possível visualizar ele no caminho:

`\target\site\jacoco\index.html`