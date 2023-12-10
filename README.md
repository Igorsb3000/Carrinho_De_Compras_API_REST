# API Carrinho de Compras

## Descrição do Projeto
Este é o projeto final da disciplina de Gerência de Configuração e Teste de Software da Residência em TI do TRF5.
O projeto consiste em uma API REST desenvolvida em Spring Boot com Java, utilizando um banco de dados PostgreSQL. A API inclui testes unitários com JUnit 5 e Mockito para garantir a integridade do código.


## Pré-requisitos para Execução do Projeto
- Docker instalado;
- JDK 17 ou superior instalado
- Maven 3.x instalado
- Banco de dados PostgreSQL
- Terminal ou IDE Eclipse para execução dos próximos passos


## Execução do Projeto no Terminal:
## Configurando Variáveis de Ambiente
1. No windows, pesquise por "Editar as variáveis de ambiente do sistema".
2. Após abrir o janela de propriedades do sistema, clique no botão "Variáveis de ambiente...".
3. Clique no botão "Novo", pode ser em variáveis do sistema ou variáveis do usuário.
4. Então, preencha com os dados do arquivo ```.env```.
5. Por exemplo, em nome da variável adicione ```DATABASE_HOST``` e em valor da variável adicione ```localhost```.
6. Repita o passo 5 para todas as variáveis presentes no arquivo ```.env```.
7. Nas variáveis DATABASE_USERNAME e DATABASE_PASSWORD, adicione como valor suas respectivas suas credenciais do PostgreSQL

## Executando o Docker
1. Abra o terminal externo ou Git Bash.
2. Navegue até a pasta raiz do projeto.
3. Execute o comando ```docker-compose up -d``` para iniciar o contêiner do PostgreSQL.

## Execução do Projeto
1. Abra o termina externo ou Git Bash.
2. Navegue até a pasta raíz do projeto.
3. Execute o comando ```mvn spring-boot:run``` para executar o projeto.
4. A API estará disponível em http://localhost:8081.
5. O swagger do projeto estará disponível em ```http://localhost:8081/api/swagger-ui/index.html```.
6. *OBS: Caso a porta ```8081``` esteja em uso, altere no arquivo ```application.properties``` para outra valor, como por exemplo ```8082```.

## Execução dos Testes
1. Abra o terminal externo ou Git Bash.
2. Navegue até a pasta raíz do projeto.
3. Execute o comando ```mvn test``` para executar os testes unitários com JUnit 5.

## Verificação da Cobertura dos Testes
1. Abra o termina externo ou Git Bash.
2. Navegue até a pasta raíz do projeto.
3. Execute o comando ```mvn jacoco:report``` para gerar o relatório de cobertura.
4. No explorador de arquivos do seus sistema, vá até o diretório ```Carrinho_De_Compras_API_REST/target/site/jacoco/```.
5. Abra o arquivo ```index.html```.
6. Navegue até o pacote ```br.com.atlantic.api.domain.service```;
7. Agora, você poderá verificar a cobertura da classe CarrinhoService.


## Execução do Projeto no Eclipse:
### Configuração das Variáveis de Ambiente no Eclipse
1. Abra o projeto no eclipse.
2. Então clique com o botão direito do mouse em cima do diretório raíz do projeto e selecione Properties.
3. Vá até a opção Run/Debug Settings e clique no botão New.
4. Então selecione Java Application e pressione Ok.
5. Na opçãp Main Class clique em search e busque a classe CarrinhoApplication (classe principal do projeto).
6. Agora, vá até a aba Environment, copie o conteúdo do arquivo ```.env``` e coloque nessa aba.
7. Nas variáveis DATABASE_USERNAME e DATABASE_PASSWORD, adicione como valor suas respectivas suas credenciais do PostgreSQL
8. Clique em Apply, depois em Ok e por fim Apply and close.

### Execução do Docker no Eclipse
1. Abra o terminal do Eclipse ou um terminal externo.
2. Navegue até a pasta raiz do projeto.
3. Execute o comando ```docker-compose up -d``` para iniciar o contêiner do PostgreSQL.

### Execução do Projeto no Eclipse
1. No Eclipse, localize o arquivo principal da aplicação (CarrinhoApplication.java) dentro do pacote br.com.atlantic.api.
2. Clique com o botão direito no arquivo e selecione Run As > Java Application.
3. Isso iniciará sua aplicação na IDE.

### Execução dos Testes no Eclipse
1. Navegue até a classe SuiteTestes.java dentro do pacote br.com.atlantic.api.domain.service do diretório ```src/test/java```.
2. Clique com o botão direito na classe de teste e selecione Run As > JUnit Test.

### Verificação da cobertura dentro do Eclipse.
1. Clique com o botão direito na classe SuiteTestes.java.
2. Vá para Coverage As > JUnit Test.
3. Isso abrirá uma visualização da cobertura de código diretamente no Eclipse na aba Coverage.

## Exemplo de Requisição POST para o Endpoint 'checkout'
Este é um exemplo básico de como fazer uma requisição POST para o endpoint 'checkout' da API Carrinho de Compras.

### Request
```http
POST /api/v1/checkout HTTP/1.1
Host: localhost:8081
Content-Type: application/json

{
  "nome": "João Teste",
  "cpf": "123.456.678-99",
  "itens": [
    {
      "nome": "Veja",
      "descricao": "produto de limpeza",
      "tipo": "CASA",
      "peso": 1.0,
      "preco": 4.99,
      "quantidade": 1
    }
  ]
}
```
### Response
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
    "subTotal": 4.99,
    "frete": 0.00,
    "total": 4.99
}
```

## Autores
- Igor Silva Bento
	- Github: https://github.com/Igorsb3000 
		- Linkedin: https://www.linkedin.com/in/igor-silva-bento-7542004a/
- Dawerton Eduardo Carlos Vaz
	- Github: https://github.com/eduardocvaz
		- Linkedin: https://www.linkedin.com/in/eduardo-c-vaz/
- Ricardo Julio da Silva Carvalho
	- Github: https://github.com/ricardoufrn
- Alex Pereira Barros
