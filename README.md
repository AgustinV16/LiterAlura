# LiterAlura 📚

LiterAlura es una aplicación de consola desarrollada en Java y Spring Boot que funciona como un catálogo de libros interactivo. Permite a los usuarios buscar libros utilizando la API pública de Gutendex, guardar la información en una base de datos PostgreSQL y realizar consultas sobre los libros y autores almacenados.

Este proyecto fue desarrollado como parte del Challenge de Programación de Alura Latam, enfocado en poner en práctica:

Desarrollo backend con Java y Spring Boot

Consumo de APIs externas

Persistencia de datos con JPA y PostgreSQL

## Funcionalidades

La aplicación ofrece un menú interactivo en la consola con las siguientes opciones:

Buscar libro por título: Busca un libro en la API de Gutendex y lo guarda en la base de datos junto con su autor.

Listar libros registrados: Muestra todos los libros almacenados en la base de datos.

Listar autores registrados: Incluye año de nacimiento, fallecimiento y títulos de sus libros.

Listar libros por idioma: Filtra los libros por idioma (ej. español, inglés, francés).

Listar autores vivos en un año específico: Muestra los autores que estaban vivos en el año ingresado.

## Tecnologías Utilizadas

- **Lenguaje**: Java 17 
- **Framework**: Spring Boot 3.5.0 
- **Persistencia de Datos**: Spring Data JPA 
- **Base de Datos**: PostgreSQL 
- **Cliente HTTP**: HttpClient nativo de Java 
- **Manejo de JSON**: Jackson Databind (ObjectMapper) 
- **Gestión de Dependencias**: Apache Maven

## Prerrequisitos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- [**JDK (Java Development Kit)**](https://www.oracle.com/java/technologies/downloads/) 
- Versión 17 o superior. 
- [**Apache Maven**](https://maven.apache.org/download.cgi) 
- Versión 3.8 o superior. 
- [**PostgreSQL**](https://www.postgresql.org/download/) 
- Una instancia de base de datos activa.

## Cómo Ejecutar el Proyecto

1. Clonar el Repositorio

bash 
git clone https://github.com/tu-usuario/literalura.git
cd literalura

2. Configurar la Base de Datos

Edita el archivo src/main/resources/application.properties con tus credenciales de PostgreSQL:

spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=tu_usuario_postgres
spring.datasource.password=tu_contraseña_postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

3. Compilar y Ejecutar la Aplicación

Con Maven Wrapper:

bash
./mvnw spring-boot:run


O con Maven instalado globalmente:

bash
mvn spring-boot:run

## API Externa

Este proyecto consume datos de Gutendex, una API web para libros de dominio público del Proyecto Gutenberg.
