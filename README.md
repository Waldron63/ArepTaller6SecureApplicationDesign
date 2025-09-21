# Taller de trabajo individual en patrones arquitecturales

## Talleres previos
Para ver los 2 talleres anteriores hechos en clase, "AWS-CLI Primer Workshop" y "Taller: JPA", se encuentran en el siguiente link:

https://github.com/Waldron63/accessing-data-jpa.git

## Introducción


### Prerrequisitos

Para el correcto uso del servidor, es necesario tener las siguientes aplicaciones instaladas:
- JAVA
     ```sh
   java -version
   ```
- MAVEN
     ```sh
   mvn -v
   ```
- GIT
   ```sh
   git --version
   ```
- DOCKER
   ```sh
   docker --version
   ```
(NOTA: si alguna de estas aplicaciones no fue instalada, ir a la página oficial de cada una e instalar la versión recomendada).

### Instalación
1. clonar el repositorio con el siguiente comando y ingresar a la carpeta en donde esta incluido el *pom.xml*:

   ```sh
   git clone https://github.com/Waldron63/ArepTaller5CRUDSystem.git
   cd ArepTaller5CRUDSystem.git
   ```

2. Construir el proyecto:

   ```sh
   mvn clean install
   mvn clean package
   ```
  La consola mostrará información parecida a:
  
  <img width="1451" height="277" alt="image" src="https://github.com/user-attachments/assets/48125c05-4b00-4219-bbcd-24a858ae70e9" />

3. Correr la aplicación:

   ```sh
   docker run -p 3306:3306 --name mysqlproperties -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=properties -e MYSQL_ROOT_HOST=% -d mysql:latest
   docker ps
   mvn spring-boot:run
   ```

   La consola debería mostrar el siguiente mensaje:

    <img width="1455" height="136" alt="image" src="https://github.com/user-attachments/assets/f6ae7916-8e34-4788-a1ef-61205cf566bd" />
    
   ```sh
    2025-09-21T12:09:15.223-05:00  INFO 27272 --- [ArepTaller5CRUDSystem] [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
    2025-09-21T12:09:15.637-05:00  INFO 27272 --- [ArepTaller5CRUDSystem] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
    2025-09-21T12:09:15.648-05:00  INFO 27272 --- [ArepTaller5CRUDSystem] [           main] e.e.a.A.ArepTaller5CrudSystemApplication : Started ArepTaller5CrudSystemApplication in 7.021 seconds (process running for 7.57)
    2025-09-21T12:09:52.609-05:00  INFO 27272 --- [ArepTaller5CRUDSystem] [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
    2025-09-21T12:09:52.610-05:00  INFO 27272 --- [ArepTaller5CRUDSystem] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
    2025-09-21T12:09:52.611-05:00  INFO 27272 --- [ArepTaller5CRUDSystem] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
   ```

   - Página principal:
     Una vez iniciado, en el buscador ingresar: http://localhost:8080/ y lo llevará a la página inicial del proyecto:

      <img width="1528" height="912" alt="image" src="https://github.com/user-attachments/assets/5bce9e45-004a-4aa9-bfc4-498aafe8e9fe" />

   - Archivos estáticos:
     Ingresar alguno de los siguientes comandos para mirar cada archivo estático
     ```bash
       http://localhost:35000/styles.css
       http://localhost:35000/script.js
       http://localhost:35000/index.html
     ```
   - servicios Rest:
     Ingresar el siguiente comando para mirar el servicio Properties (GET):
     ```bash
       http://localhost:35000/api/properties/
       http://localhost:35000/api/priperties/{id}
     ```

### Servicios:
- (GET)/api/properties/: retorna la lista de todos los properties creados.
- (GET)/api/properties/{id}: retorna el property que se pide a partir del id.
- (POST)/api/properties/: genera un property con todos los elementos ingresados en el body (si hay alguno vacio, se coloca un valor predeterminado.
- (PUT)/api/properties/{id}: actualiza los campos que el usuario desee, a partir del id del property.
- (DELETE)/api/properties/{id}: elimina un property a partir del id.

## Arquitectura

<img width="612" height="511" alt="image" src="https://github.com/user-attachments/assets/c33f069b-fbd1-4479-a734-df31f0077b0f" />

La estructura del directorio del proyecto es:

<img width="891" height="949" alt="image" src="https://github.com/user-attachments/assets/b22a135c-1a61-49eb-a7cc-863df9cfbca9" />

donde:

- ArepTaller5CrudSystemApplication.java: programa de ejecución base.
- PropertiesController.java: establece la URI para peticiones Rest y el CRUD base.
- PropertiesService.java: contiene la logica del CRUD.
- PropertiesRepository.java: Genera Peticiones a la base de datos.
- Properties.java: Clase base con la informacion de la estructura de datos.
- PropertiesDto.java: DTO que ayuda a estructurar los datos de entrada del RequestBody en el Controller.


## Despliegue en Docker y AWS

### Subir el repositorio a Docker hub
Se generan los archivos (incluidos en este repositorio) "Dockerfile" y "docker-compose.yml".

Una vez agregados, se genera una imagen para "Docker Desktop" con el siguiente comando
(NOTA: en docker hub, crear un repositorio nuevo con el mismo nombre que el build)

 ```bash
  docker build --tag santiagogualdron/propertieslab05 .
  docker run -d -p 34000:6000 --name propertieslab05 santiagogualdron/propertieslab05
  docker login
  docker push santiagogualdron/propertieslab05
 ```

  <img width="1199" height="111" alt="image" src="https://github.com/user-attachments/assets/e291657c-c191-4c69-ada7-f6916c8e7295" />

  <img width="1264" height="724" alt="image" src="https://github.com/user-attachments/assets/b5708f52-d191-4cf9-b7d3-fb263e474cda" />

### Instancias en AWS
para ambas instancias, al generarlas con EC2, una vez que se conecten a cada instancia, se sugiere agregar los siguientes comandos para instalar en ambas Docker.

 ```bash
  sudo yum update -y
  sudo yum install docker
  sudo service docker start
  sudo usermod -a -G docker ec2-user
  docker --version
  docker login
 ```
Luego se debe salir de la maquina virtual y voler a ingresar, para que carguen todos los archivos y el login

<img width="1569" height="81" alt="image" src="https://github.com/user-attachments/assets/6ef57cfc-0312-4e76-8aba-31ad8d1a4b0c" />

1. Instancia para MySQL
  Primero, configurar la seguridad de la instancia para que acepte el paso por el puerto 3306.

  <img width="1858" height="783" alt="image" src="https://github.com/user-attachments/assets/8e535842-b701-416e-a8ca-21d02b902396" />

  Ahora, dentro de la maquina virtual de la instancia (utilizando la llave .pem) se crea el contenedor para mysql
  
   ```bash
    ssh -i "llave.pem" ec2-user@ipdelainstancia.compute-1.amazonaws.com
   ```

  (NOTA: si docker no encuentra la imagen de mysql, la descarga automaticamente para proseguir con el contenedor).

   ```bash
    docker run -p 3306:3306 --name mysqlproperties -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=properties -e MYSQL_ROOT_HOST=% -d mysql:latest
    docker ps
   ```

  en la terminal, deberia mostrar algo como (confirmando que si esta corriendo en el puerto 3306):
  
  <img width="725" height="82" alt="image" src="https://github.com/user-attachments/assets/fe908fbe-8eef-4a1c-9baf-c5caedd804b6" />

2. Instancia para el Backend
  Primero, configurar la seguridad de la instancia para que acepte el paso por el puerto 8080

  <img width="1919" height="800" alt="image" src="https://github.com/user-attachments/assets/90ab4653-a46d-4b6b-b8c0-f5db0f838e30" />

  Ahora, dentro de la maquina virtual de la instancia (utilizando la llave .pem) se crea el contenedor para el backend

   ```bash
    ssh -i "llave.pem" ec2-user@ipdelainstancia.compute-1.amazonaws.com
   ```

  Para este punto, se debe cambiar los comandos de Dockerfile, docker-compose.yml y application.properties;
  colocandoles la IP de la instancia de la base de datos,
  si se deja como localhost, no podra utilizar la instancia de mysql previamente creada 
  (Se ha dejado documentado la parte de localhost en los codigos para mayor informacion)

   ```bash
    docker pull santiagogualdron/propertieslab05
    docker run -d -p 8080:8080 --name propertieslab05 santiagogualdron/propertieslab05:latest
    docker ps
   ```
### Prueba de conexion entre las instancias

  <img width="1918" height="1013" alt="image" src="https://github.com/user-attachments/assets/121bbf6c-be84-4c31-8670-985d12a763ac" />


  <img width="1571" height="833" alt="image" src="https://github.com/user-attachments/assets/588c2e93-4159-4e1c-ad64-42329b83c7d7" />

  
  <img width="1198" height="139" alt="image" src="https://github.com/user-attachments/assets/91d29cc2-cfe7-4f2b-b2d7-67d5b4b91dc2" />

## Reporte de pruebas

### fecha

Fecha:21/09/2025

### Pruebas unitarias:

<img width="1284" height="305" alt="image" src="https://github.com/user-attachments/assets/db1c30a1-5d42-46fc-986d-817621fb75ec" />

donde cada prueba unitaria (del archivo mainTest, las pruebas de HttpServerTest no cambiaron de la anterior versión) sirve para:
- shouldLoadStaticFileHtml(): comprobar que funciona el estándar o index principal de la aplicación web.
- shouldLoadStaticFileForm(): comprueba que el servidor acepta formato html.
- shouldLoadStaticFileCss(): comprueba que el servidor acepta formato css.
- shouldLoadStaticFileJs(): comprueba que el servidor acepta formato js.
- shouldLoadStaticImagePNG(): comprueba que el servidor acepta formato png y jpg.
- shouldLoadGreetingControllerWithQuery(): Comprueba que genera la petición Http con servicio RestController de forma adecuada, obteniendo el nombre de la persona y devolviendo la respuesta.
- shouldLoadGreetingControllerWithoutQuery(): Comprueba que genera la petición Http con servicio RestController de forma adecuada, sin el nombre de la persona y devolviendo la respuesta estandar.

### Pruebas de aceptación

- index.html:
  
  <img width="1513" height="790" alt="image" src="https://github.com/user-attachments/assets/54970035-9ecc-4bee-ad56-447399e4fc45" />

- styles.css:

  <img width="1089" height="847" alt="image" src="https://github.com/user-attachments/assets/389f6a2e-965a-4a53-aa5b-2e9ede1b9104" />

- script.js:

  <img width="855" height="782" alt="image" src="https://github.com/user-attachments/assets/3934ea2f-0ece-4189-ac39-2a511209e7d0" />

(SERVICIOS REVISADOS EN POSTMAN PARA CONFIRMAR UTILIDAD)
- servicio rest /api/properties/ (POST):

  <img width="1130" height="618" alt="image" src="https://github.com/user-attachments/assets/c8e3baf7-0d29-47e0-9dff-c8142e7c2f07" />

- servicio rest /api/properties/ (GET):

  <img width="1119" height="676" alt="image" src="https://github.com/user-attachments/assets/2fb5443b-43ff-487d-ba1b-4dc1f4316090" />

- servicio rest /api/properties/{id} (GET):

  <img width="1106" height="493" alt="image" src="https://github.com/user-attachments/assets/33886636-b669-4397-a76b-476371ef97df" />

- servicio rest /api/properties/{id} (PUT):

  <img width="1117" height="594" alt="image" src="https://github.com/user-attachments/assets/15fdda4d-40b8-4b56-a834-809ac84a137a" />

  <img width="1532" height="821" alt="image" src="https://github.com/user-attachments/assets/1c781a4b-1448-4ee3-b38e-b054176968ae" />

- servicio rest /api/properties/{id} (DELETE):

  <img width="1123" height="495" alt="image" src="https://github.com/user-attachments/assets/358bccea-01d2-4bf1-a354-a97c88cf2243" />

## Construido con

[Maven](https://maven.apache.org/index.html) - Dependency Management

[Git](https://git-scm.com) - Version Control System

[Docker](https://www.docker.com/) - Containers and Images

## Autor

Santiago Gualdron Rincon - [Waldron63](https://github.com/Waldron63)
