# API Carrinho de Compras

## Descrição do Projeto
Este é o projeto final da disciplina de Gerência de Configuração e Teste de Software da Residência em TI do TRF5.
O projeto consiste em uma API REST desenvolvida em Spring Boot com Java, utilizando um banco de dados PostgreSQL. A API inclui testes unitários com JUnit 5 e Mockito para garantir a integridade do código.

## Pré-requisitos para Execução do Projeto
- Docker instalado;
- JDK 17 ou superior instalado
- Maven 3.x instalado
- Banco de dados PostgreSQL
- Git Bash ou terminal para execução dos próximos passos

## Configurando Variáveis de Ambiente

Antes de executar o Docker Compose (responsável por criar a tabela 'carrinho' no banco de dados PostgreSQL), é necessário configurar as variáveis de ambiente para o banco de dados PostgreSQL. Siga os passos abaixo:

1. Abra o arquivo `application.properties` no caminho: ```Carrinho_De_Compras_API_REST/src/main/resources```.

2. Altere os campos DATABASE_USERNAME e DATABASE_PASSWORD, adicionado respectivamente seu usuário e senha do PostgreSQL.

3. Salve o arquivo 'application.properties' após fazer a alteração. Pode utilizar o comando Ctrl + S.

###OBS: Para executar os próximos passos é necessário:

1. Estar com uma janela aberta do git bash (windows) ou terminal (linux/mac);

2. Navegar até a pasta raíz do projeto ```Carrinho_De_Compras_API_REST/```

## Executando o Docker

Para executar o arquivo docker do projeto execute o comando: ```docker-compose up -d```


## Execução do Projeto

Para executar o projeto localmente use o comando: ```mvn spring-boot:run```

- A API estará disponível em http://localhost:8081.

O swagger da API estará disponível em ```http://localhost:8081/api/swagger-ui/index.html```

## Execução dos Testes
Para executar os testes unitários, utilize o seguinte comando: ```mvn test```

## Verificação da Cobertura dos Testes
Para verificar a cobertura dos testes, execute o seguinte comando: ```mvn jacoco:report```

- Isso gerará um relatório de cobertura dos testes que pode ser encontrado em ```Carrinho_De_Compras_API_REST/target/site/jacoco/index.html```

Ao abrir o arquivo index.html em seu navegador, faça:
- Clique no link do pacote ````br.com.atlantic.api.domain.service```;
- Agora, você poderá verificar a cobertura da classe CarrinhoService através da cobertura das instruções e branches.

#### OBS: Todos esses procedimentos de execução da API REST e dos testes também podem ser feitos através do Eclipse IDE (https://eclipseide.org/).
