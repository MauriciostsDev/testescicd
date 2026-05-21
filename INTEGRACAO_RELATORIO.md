# Relatório de Testes de Integração

## O que é um teste de integração?
Um teste de integração verifica o comportamento de vários componentes do sistema trabalhando juntos, em vez de testar uma única unidade isolada. Ele valida a comunicação entre camadas, como controlador, serviço e repositório, e pode incluir dependências externas como banco de dados.

## Componentes integrados neste projeto
- Controlador REST (`CarroController`)
- Serviço de negócio (`CarroService`)
- Repositório JPA (`CarroRepository`)
- Banco de dados MySQL de teste
- Validação de entrada e tratamento de exceções

## Diferença entre teste unitário e teste de integração
- Teste unitário: valida uma parte isolada do código, normalmente uma classe ou método sem dependências externas.
- Teste de integração: valida fluxos entre múltiplos componentes e dependências externas, garantindo que as integrações funcionem corretamente.

## Quais problemas esse tipo de teste ajuda a identificar?
- Problemas de configuração do Spring ou do contexto de aplicação
- Falhas de mapeamento de entidades ou SQL
- Inconsistências no comportamento entre controlador, serviço e repositório
- Erros de validação e tratamento de exceções em fluxos reais
- Problemas de compatibilidade com o banco de dados utilizado

## Testes implementados
### Testes de integração existentes e adicionados
- `buscarIdInexistente_deveRetornarNotFound`
- `salvarCarroSemModelo_deveRetornarBadRequest`
- `salvarCarroPrecoNegativo_deveRetornarBadRequest`
- `atualizarCarroInexistente_deveRetornarNotFound`
- `salvarCarroModeloX_deveRetornarBadRequestComExcecao`
- `salvarCarroModeloY_deveRetornarBadRequestComExcecao`
- `dadosInseridosPorSql_deveEstarDisponiveisAntesDosTestes`

### Cobertura da atividade
- Implementados pelo menos dois testes adicionais requisitados.
- Usado `@Sql` para inserir dados antes da execução dos testes.
- A execução em Docker validou o cenário com MySQL e Java 17.

## Resultados atuais
- Testes executados: 19
- Falhas: 0
- Erros: 0
- Build final: `BUILD SUCCESS`
- A execução Docker validou o projeto com MySQL e JaCoCo.

## Execução de testes e cobertura
- O projeto já possui configuração do JaCoCo no `pom.xml`.
- O workflow GitHub Actions em `.github/workflows/maven.yml` executa:
  - `./mvnw -B clean package -DskipTests` no build
  - `./mvnw -B test jacoco:report` nos testes com MySQL service
- O perfil de teste está configurado em `src/test/resources/application-test.properties` com MySQL.

## Execução com Docker
- Use o arquivo `docker-compose.yml` para subir o MySQL e um container Maven com Java 17.
- Execute no diretório do projeto:
  - `docker compose up --build --abort-on-container-exit`
- Alternativamente, execute `./run-docker-tests.sh` para o mesmo fluxo.
- As variáveis de conexão do teste usam `MYSQL_HOST=mysql`, `MYSQL_USER=test` e `MYSQL_PASSWORD=test`.
