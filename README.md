# Golden Raspberry Awards API

Esta é uma API RESTful desenvolvida em Spring Boot para fornecer informações sobre os prêmios Golden Raspberry (Framboesa de Ouro), também conhecido como Razzies, é uma premiação satírica que reconhece o pior do cinema em um determinado ano. A cerimônia, idealizada como uma paródia do Oscar, é organizada pela Golden Raspberry Award Foundation e votada por membros da própria fundação, além de cinéfilos e internautas. 

Ao iniciar, a aplicação lê dados de um arquivo CSV e os insere em um banco de dados em memória H2.

## Requisitos do Sistema

* Java 21 

## Como Rodar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/diegocugiki/worst-movie.git](https://github.com/diegocugiki/worst-movie.git)
    cd worst-movie
    ```
2.  **Compile o projeto com Maven:**
    ```bash
    mvn clean install
    ```
3.  **Execute a aplicação Spring Boot:**
    ```bash
    mvn spring-boot:run
    ```
    A aplicação será iniciada na porta padrão 8080 (ou na porta configurada em `application.properties`).

**Nota sobre o CSV:** O arquivo CSV (`movielist.csv`) com os dados dos filmes deve estar localizado em `src/main/resources/`. Ele será lido e os dados inseridos no banco H2 automaticamente na inicialização da aplicação.

## Swagger

Ao rodar o projeto localmente, para acessar o Swagger, use este link:
http://localhost:8080/swagger-ui/index.html

## Acessando o Banco de Dados H2 (Console)

Após iniciar a aplicação, você pode acessar o console do H2 para verificar os dados importados:

* **URL:** `http://localhost:8080/h2-console`
* **JDBC URL:** `jdbc:h2:mem:razziedb`
* **User Name:** `sa`
* **Password:** `#0u7s3ra@2025`

## Testes de Integração

Os testes de integração garantem que a aplicação funciona como esperado, do endpoint da API até a persistência no banco de dados.

1.  **Execute os testes com Maven:**
    ```bash
    mvn test
    ```
    Isso executará todos os testes na pasta `src/test/java`.
