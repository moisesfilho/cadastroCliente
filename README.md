# Cadastro de Clientes

Este é um projeto de simplificado de um CRUD para cadastro de clientes baseado em REST,
Com um pequeno front-end baseado em Angular que poderá ser executado a partir do browser.

Por debaixo to capô nós temos:
 - RESTEasy para expôr os REST endpoints
 - Hibernate ORM para execução das operações do CRUD na base de dados
 - Um base de dados H2 em memória
 - ArC, como ferramenta de injeção de dependência
 - Agroal como pool de conexões
 - Cache baseado em Infinispan
 - Narayana Transaction Manager

## Requisitos

Para compilar e executar este projeto você irá precisar de:

- JDK 11+
- GraalVM

Além disso, será necessário um Docker para executar a aplicação em container.

### Configurando GraalVM e JDK 11+

Certifique-se de que as variáveis de ambiente `GRAALVM_HOME` e `JAVA_HOME` tenham sido definidas
e que a JDK 11+ `java` tenha sido adicionada ao path.

Consulte o [Building a Native Executable guide](https://quarkus.io/guides/building-native-image)
para obter ajuda para configurar seu ambiente.

## Build do projeto

Realize o build do Maven na pasta do projeto:

> ./mvnw package

## Executando a projeto

### Live coding com Quarkus

O plugin Maven Quarkus fornece um modo de desenvolvimento que suporta
codificação ao vivo. Para experimentar isso:

> ./mvnw quarkus:dev

Neste modo, você pode fazer alterações no código e aplicar as alterações imediatamente, bastando atualizar seu navegador.

O Hot reload funciona mesmo ao modificar suas entidades JPA.
Tente! Até mesmo o esquema do banco de dados será atualizado em tempo real.

### Executando Quarkus no modo JVM

Quando terminar de experimentar no modo de desenvolvedor, você pode executar o aplicativo como um
arquivo jar convencional.

Primeiro faça o build:

> ./mvnw package

Uma instancia em memória do banco de dados h2 será iniciada junto com a aplicação.

Em seguida, execute:

> java -jar ./target/quarkus-app/quarkus-run.jar

Dê uma olhada em quão rápido ele inicializa.
Ou meça o consumo total de memória nativa.

## Veja a aplicação executando através do browser

Navegue para:

<http://localhost:8080/index.html>

## Acesso ao SwaggerUI

Para acessar as especificações da API no padrão OpenAPI, navegue para:

<http://localhost:8080/q/swagger>

## Acesso a mais dados de desenvolvimento

Para acessar mais ferramentas de desenvolvimento, navegue para:

<http://localhost:8080/q/dev>

## Executando a aplicação no Kubernetes

Realize o build do projeto

> ./mvnw clean package

Ao realizar o build do projeto a extensão configurada no MAVEN irá gerar uma imagem
que será criada com o nome:

> <user>/cadastro-cliente:1.0.0-SNAPSHOT

Para executar a imagem, execute o seguinte comando:

> docker run -p 127.0.0.1:8081:8080/tcp <user>/cadastro-cliente:1.0.0-SNAPSHOT

OBS: o nome da imagem precisará ser ajustado de acordo com o nome que foi criado

Navegue para:

<http://localhost:8081/index.html>