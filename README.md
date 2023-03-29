# Projeto de Upload, Delete e Download de Arquivos com Spring Boot e AWS S3 Bucket

Este é um projeto de exemplo para demonstrar como fazer upload, download e exclusão de arquivos utilizando o Spring Boot e AWS S3 Bucket. O projeto permite que os usuários façam upload de arquivos para a nuvem da AWS, baixem os arquivos de volta para o dispositivo local e excluam os arquivos do bucket S3 da AWS.

### Pré-requisitos
Antes de executar o projeto, você deve ter as seguintes ferramentas instaladas em sua máquina:

- Java 8 ou superior
- Maven
- Conta AWS e as credenciais AWS configuradas em seu sistema

### Configuração

1. Abra o arquivo `application` em `src/main/resources`.
2. Adicione suas credenciais da AWS e substitua os valores de accessKey e secretKey pelos valores das suas credenciais.
3. Execute o projeto com o seguinte comando Maven: `mvn spring-boot:run`

### Funcionalidades

- Upload de arquivos: permite que os usuários façam upload de arquivos para a nuvem da AWS.
- Listagem de arquivos: lista os arquivos armazenados no bucket S3 da AWS.
- Download de arquivos: permite que os usuários baixem os arquivos do bucket S3 da AWS.
- Exclusão de arquivos: permite que os usuários excluam os arquivos do bucket S3 da AWS.

### Considerações Finais

Este é um projeto de exemplo para demonstrar como utilizar o Spring Boot e AWS S3 Bucket para fazer upload, download e exclusão de arquivos. Sinta-se à vontade para adaptar este projeto para suas necessidades. Se você tiver alguma dúvida ou sugestão, sinta-se à vontade para entrar em contato.
