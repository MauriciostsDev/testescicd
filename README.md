# LojaCarro — Como rodar os testes

Este repositório contém testes unitários e de integração para a aplicação Spring Boot.

Pré-requisitos
- Docker (Engine)
- Docker Compose (v2) — `docker compose` disponível
- (Opcional) Java 17 se quiser rodar local sem Docker

Clonar o repositório
```bash
git clone <repo-url>
cd testescicd
```

Rodar com Docker (recomendado)
```bash
docker compose up --build --abort-on-container-exit
```
ou
```bash
./run-docker-tests.sh
```
- O `docker-compose.yml` sobe um MySQL (`lojacarros_test`) e um container Maven com JDK 17 que executa `./mvnw -B test jacoco:report`.
- Credenciais padrão do MySQL: `test` / `test`.

Rodar local (sem Docker)
- Instale Java 17
- Execute:
```bash
./mvnw test
# ou para gerar cobertura
./mvnw test jacoco:report
```

Rodar testes específicos
```bash
# Testes de integração
./mvnw -Dtest=CarroControllerIntegrationTest test
# Testes unitários
./mvnw -Dtest=CarroServiceTest test
```

Restaurar dump do banco de testes
- Para importar `dump_loja_carro.sql` em um MySQL local:
```bash
mysql -u test -p test < dump_loja_carro.sql
```
- Se estiver usando o container do Compose, pode executar:
```bash
docker exec -i <mysql-container> mysql -u test -p test lojacarros_test < dump_loja_carro.sql
```

Relatórios
- Relatório JaCoCo: `target/jacoco-report`
- Relatórios de testes: `target/surefire-reports`

CI
- O workflow em `.github/workflows/maven.yml` já roda os testes com MySQL no Actions.

Observações
- Se encontrar erro `release version 17 not supported`, use o Docker Compose (já preparado) para garantir Java 17 no build.

Se quiser, posso substituir `<repo-url>` pelo URL do repositório remoto ou adicionar instruções para rodar em CI/CD com status badges.