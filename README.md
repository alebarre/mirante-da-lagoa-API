# mirante-da-lagoa-API

Backend do sistema de gestão do condomínio Mirante da Lagoa - Saquarema/RJ.

## Tecnologias

- Java 17
- Spring Boot 3.5.x
- PostgreSQL (via Docker Compose)
- JWT (JJWT)
- Maven
- Lombok 1.18.34

## Como executar

### 1. Subir o banco de dados

O banco de dados sobe via Docker Compose, que fica na pasta desta API:

```bash
cd mirante-da-lagoa-API
cp .env.example .env
# edite .env se necessário (DB_NAME, DB_USER, DB_PASS, DB_PORT)
docker compose up -d
```

O PostgreSQL ficará disponível na porta `5433`.

### 2. Configurar variáveis de ambiente

Edite o arquivo `.env` na pasta desta API:

| Variável | Obrigatória | Descrição |
|----------|-------------|-----------|
| `JWT_SECRET` | Sim | Chave base64 HS512. Gere com: `openssl rand -base64 64` |
| `DB_URL` | Não | JDBC URL (padrão: `jdbc:postgresql://localhost:5433/mirante_lagoa`) |
| `DB_USER` | Sim | Usuário do PostgreSQL |
| `DB_PASS` | Sim | Senha do PostgreSQL |
| `MAIL_HOST` | Não | Servidor SMTP |
| `MAIL_PORT` | Não | Porta SMTP (padrão: 587) |
| `MAIL_USER` | Não | Usuário SMTP |
| `MAIL_PASS` | Não | Senha SMTP |
| `CORS_ORIGINS` | Não | Origens permitidas (padrão: `http://localhost:4200`) |
| `SERVER_PORT` | Não | Porta da API (padrão: 8080) |

> **ATENÇÃO:** Nunca commite credenciais ou `JWT_SECRET`. Use `.env`.

### 3. Executar a API

```bash
cd mirante-da-lagoa-API
mvn spring-boot:run
```

### Usuário admin padrão

Na primeira execução, um usuário administrador é criado automaticamente:

- Email: `admin@mirantedalagoa.com.br`
- Senha: `admin123`

> Altere a senha após o primeiro login.

## Funcionalidades principais

- Autenticação JWT com perfis de acesso (ADMIN, SÍNDICO, PORTARIA, FUNCIONÁRIO, MORADOR)
- Gestão de moradores, funcionários, compromissos, eventos e obrigações trabalhistas
- Cadastro de funcionários com salário, encargos, benefícios e provisões trabalhistas
- **Gerenciamento de percentuais de encargos trabalhistas** (`/api/parametros`)
  - Permite configurar percentuais de INSS patronal, FGTS, IRRF, vale-transporte, vale-alimentação, plano de saúde, 13º salário, férias, 1/3 de férias e multa rescisória
  - Cálculo automático dos encargos/provisões ao salvar funcionários com regime CLT

## Seed de dados

Os scripts `src/main/resources/init.sql` (DDL) e `seed.sql` (dados iniciais) são executados automaticamente pelo PostgreSQL na primeira criação do container.

A `seed.sql` já inclui os percentuais de encargos trabalhistas baseados na CLT vigente.

## Testes

```bash
mvn test
```

## Comandos Docker

```bash
# Subir banco
docker compose up -d

# Ver logs
docker compose logs -f

# Parar banco
docker compose down

# Parar e apagar dados (cuidado!)
docker compose down -v
```
