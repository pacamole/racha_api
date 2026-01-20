# Racha API

![Java](https://img.shields.io/badge/Java-21-orange) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-green) ![Docker](https://img.shields.io/badge/Docker-Enabled-blue) ![AWS](https://img.shields.io/badge/AWS-EC2-yellow)

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3
* **Banco de Dados:** PostgreSQL
* **Infraestrutura:** Docker & Docker Compose
* **Cloud:** AWS EC2 (Hospedagem) & Cloudflare R2 (Storage)
* **Servidor Web:** Nginx (Proxy Reverso)
* **Documentação:** Swagger (OpenAPI)

## Sobre o Projeto

O **Racha API** é um sistema backend projetado para gerenciar e simplificar a divisão de despesas financeiras entre grupos ("Rachas"). A aplicação resolve a fricção comum de cobrar amigos, validar pagamentos e centralizar comprovantes em um único lugar.

A lógica do sistema gira em torno da entidade **Racha**, onde um representante define o valor total e o sistema gerencia o ciclo de vida da dívida, desde o convite dos participantes até a quitação total.

### Funcionalidades Principais

* 🔐 **Autenticação:** Login via Google e JWT.
* ☁️ **Upload de Arquivos:** Armazenamento de comprovantes utilizando Cloudflare R2.
* 💰 **Pagamentos:** Gerenciamento de chaves PIX.
* 🐳 **Containerização:** Ambientes de Desenvolvimento e Produção isolados via Docker.

### Regras de Negócio

- **Divisão Automática e Justa:**
  * Ao criar um Racha, o sistema calcula automaticamente a cota de cada participante (`Total / Participantes`).
  * O representante pode optar por participar ou não da divisão financeira no momento da criação.
  * O saldo devedor e o total arrecadado (`currentlyPaid`) são atualizados em tempo real a cada pagamento registrado.

- **Sistema de Convites:**
  * Geração de links de convite únicos por Racha, com data de expiração vinculada ao vencimento da conta.
  * Controle de acesso que impede a entrada de usuários duplicados ou em rachas expirados.

- **Pagamentos e Comprovantes (Storage):**
  * Registro detalhado de pagamentos parciais ou totais.
  * **Upload de Comprovantes:** Integração com **Cloudflare R2 (S3 Compatible)** para armazenar fotos/PDFs dos recibos, vinculando-os diretamente à transação para auditoria do representante.

- **Carteira de Chaves PIX:**
  * Os usuários podem cadastrar múltiplas chaves PIX (CPF, E-mail, Telefone, Aleatória) em seus perfis.
  * Facilita a visualização para quem precisa realizar o pagamento, centralizando as informações bancárias.

- **Interação e Transparência:**
  * Sistema de comentários por Racha, permitindo que os participantes discutam sobre a despesa ou avisem sobre pagamentos no próprio contexto do grupo.

- **Segurança e Autenticação:**
  * Suporte híbrido para Login Tradicional (Email/Senha) e **Login Social (Google OAuth2)**.
  * Proteção de rotas via **Tokens JWT**, garantindo que apenas os participantes do grupo tenham acesso aos dados sensíveis.

### Relação de Entidade
<img width="1787" height="1457" alt="Racha - DER" src="https://github.com/user-attachments/assets/75d6d33a-6fc4-4aca-bebb-b49b4f7261a4" />

## 🚀 Como Rodar Localmente

### Pré-requisitos
* [Docker](https://www.docker.com/) e Docker Compose instalados.
* [Git](https://git-scm.com/).

### Passo a Passo

1. **Clone o repositório**
   ```bash
   git clone [https://github.com/pacamole/racha_api.git](https://github.com/pacamole/racha_api.git)
   cd racha_api
   ```
2. **Configure as Variáveis de Ambiente** 
Crie um arquivo ```.env``` na raiz do projeto com as seguintes chaves:
   ```
   # Banco de Dados
   DB_USERNAME=postgres
   DB_PASSWORD=sua_senha
    
   # Google OAuth2
   GOOGLE_CLIENT_ID=seu_client_id
   GOOGLE_CLIENT_ID_SECRET=seu_secret
    
   # Segurança
   JWT_SECRET=sua_chave_secreta_jwt
  
   # Storage (R2/S3)
   R2_ENDPOINT=url_do_endpoint
   R2_ACCESS_KEY=sua_access_key
   R2_SECRET_KEY=sua_secret_key
   R2_BUCKET_NAME=nome_do_bucket
   R2_BUCKET_URL=url_publica
   ``` 
3. Inicie com Docker Compose
   ```
   # Para subir o ambiente de desenvolvimento
   docker-compose up -d app_dev db_dev
   ```
4. Acesse a Documentação Swagger
Estará disponível no link: <http://localhost:80/swagger-ui.html>

### ☁️ Arquitetura de Deploy (AWS)
O projeto está hospedado em uma instância *AWS EC2*, utilizando uma arquitetura otimizada para o *Free Tier*:
- **Nginx** atua como Proxy Reverso na porta 80, gerenciando o tráfego.
- **Docker** orquestra os containers, permitindo alternar entre versões de Dev e Prod.
- Para economizar CPU no servidor, as imagens Docker são construídas localmente e enviadas prontas para a AWS, evitando compilação em ambiente de produção.

***Vizualização da arquitetura***
<img width="5052" height="3213" alt="Racha server - Diagrama ASCII" src="https://github.com/user-attachments/assets/f6dda81b-c1dc-4e5d-8fa2-60d6fca79974" />


