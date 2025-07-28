# Prueba-Spring-OMC
API de gestión de Todos, creada en Java Spring Boot.

## 🛠️ Tecnologías
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3%20-%20%23663399?style=for-the-badge&logo=css&logoColor=%23FFFFFF)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

## ✅ Requisitos
Para que esta aplicación funcione correctamente, se necesita lo siguiente:

- [Java 17+](https://jdk.java.net/)
- [MySQL](https://dev.mysql.com/downloads/installer/)
- [Maven 3.8+](https://maven.apache.org/download.cgi) (opcional, este proyecto incluye Maven Wrapper)
- Recomendable tener un IDE:
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
  - [Eclipse](https://www.eclipse.org/downloads/)
  - [Visual Studio Code](https://code.visualstudio.com/Download)


## ⚙️ Configuración

Primero, hay que clonar este repositorio: <br>
`git clone https://github.com/OscarPM24/Prueba-Spring-OMC.git`

Configurar la base de datos en `application.properties`, con tus credenciales de MySQL: <br>
`spring.datasource.username=USUARIO`<br>
`spring.datasource.password=PASSWORD`

Importar dependencias con el siguiente comando: <br>
`./mvnw clean install`

Si se usa un IDE. como IntellIJ Idea, no es neceasrio este paso.

## ▶️ Ejecución

Para ejecutar la aplicación, ejecutar el siguiente comando: <br>
`./mvnw spring-boot:run`

Al iniciar la aplicación, se creará una base de datos llamada todolist, con datos falsos para uso de testeo, desde los archivos `/resources/db/schema.sql` y `/resources/db/data.sql`.

Para acceder a la aplicación web, entrar desde `http://localhost:8080/`.

Al acceder, saldrá un formulario de login, para iniciar sesión, hay que poner alguno de los datos creados en el archivo `/resources/db/data.sql`.
Por ejemplo, username=oscar434, password=1234

## 🛠️ Funcionalidades 

#### Listado de Todos

La página principal muestra un listado de Todos, con el título, y el username, país y si está completada. 

Además, los todos se pueden filtrar, usando los filtros que aparecen en la página. Se pueden filtrar los todos por título, o por username.

#### Creación de Todos

En la página del listado, aparece un botón para crear un Todo. 
Este botón te llevará a un formulario, donde puedes insertar el título del todo, y si está completado.

#### Edición de Todos

Cada todo tiene un botón para editarlo.
Este botón te llevará al formulario de creación, con los datos del todo seleccionado para poder editar los valores.

#### Borrado de Todos

Cada todo tiene un botón para eliminarlo.
Al pulsarlo, saldrá una alerta de confirmación. Si es aceptada, el todo se eliminará de la lista.

## Swagger UI

Esta API está documentada con Swagger usando Springdoc OpenAPI, una interfaz para poder acceder a todos los métodos de la API y poder interactuar con ellos.

Para acceder a la interfaz se puede desde la siguiente URL: `http://localhost:8080/swagger-ui/index.html`

## 🔍 Tests

Esta API incluye tests unitarios para comprobar los métodos del controlador, usando Mockito.

Para ejecutar los tests, ejecutar el siguiente comando: <br>

`./mvnw test`
