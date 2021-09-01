# Projeto Bate Ponto Teste

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Geral

Projeto de teste de competencias

Tratase de uma web API para controle de ponto, seguindo o contrato, que permite realizar as seguintes ações:

Registrar os horários da jornada diária de trabalho.

- Apenas 4 horários podem ser registrados por dia.
- Deve haver no mínimo 1 hora de almoço. 
- Sábado e domingo não são permitidos como dia de trabalho.

## Começando

```console

./mvnw package
docker-composer up --build

```