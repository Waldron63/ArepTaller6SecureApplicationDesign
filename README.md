# Taller del Diseño de Aplicaciones Seguras

## Introducción
En este taller se desea mostrar el uso de la seguridad para aplicaciones, utilizando certificados gratuitos por "Let's Encode", DNS gratuitos con "DuckDns"; entre otras cosas.

En este laboratorio de desea mostrar como se divide la arquitectura de la aplicación en 3 instancias diferentes (Todas en EC2 de AWS);
donde el front y back se desarrollaron con una AMI LA2023, a las cuales se les configuró LAMP, y la DataBase desarrollada en MySQL. 


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
   git clone https://github.com/Waldron63/ArepTaller6SecureApplicationDesign.git
   cd ArepTaller6SecureApplicationDesign
   ```

2. Construir el proyecto:

   ```sh
   mvn clean install
   mvn clean package
   ```
   
  La consola mostrará información parecida a:

<img width="1159" height="331" alt="image" src="https://github.com/user-attachments/assets/3e896250-da1b-435d-bbb5-389e502fc038" />

3. Correr la aplicación:

   ```sh
   docker run -p 3306:3306 --name mysqlproperties -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=properties -e MYSQL_ROOT_HOST=% -d mysql:latest
   docker ps
   mvn spring-boot:run
   ```

   La consola debería mostrar el siguiente mensaje:

     <img width="1163" height="115" alt="image" src="https://github.com/user-attachments/assets/b4116780-f67c-4bcc-bf5e-c992743ba5ac" />
    
   ```sh
    2025-10-08T12:09:15.223-05:00  INFO 27272 --- [ArepTaller6SecureApplicationDesign] [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
    2025-10-08T12:09:15.637-05:00  INFO 27272 --- [ArepTaller6SecureApplicationDesign] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
    2025-10-08T12:09:15.648-05:00  INFO 27272 --- [ArepTaller6SecureApplicationDesign] [           main] e.e.a.A.ArepTaller6SecureApplicationDesign : Started ArepTaller6SecureApplicationDesign in 7.021 seconds (process running for 7.57)
    2025-10-08T12:09:52.609-05:00  INFO 27272 --- [ArepTaller6SecureApplicationDesign] [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
    2025-10-08T12:09:52.610-05:00  INFO 27272 --- [ArepTaller6SecureApplicationDesign] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
    2025-10-08T12:09:52.611-05:00  INFO 27272 --- [ArepTaller6SecureApplicationDesign] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
   ```

   - Página principal:
     Una vez iniciado, en el buscador ingresar: http://localhost:8080/ y lo llevará a la página inicial del proyecto:

     <img width="1221" height="630" alt="image" src="https://github.com/user-attachments/assets/f260100b-20a7-4586-8ea0-b34c82cb7177" />

   - Archivos estáticos:
     Ingresar alguno de los siguientes comandos para mirar cada archivo estático
     ```bash
       http://localhost:35000/styles.css
       http://localhost:35000/script.js
       http://localhost:35000/index.html
       http://localhost:35000/auth.css
       http://localhost:35000/auth.js
       http://localhost:35000/auth.html
     ```
   - servicios Rest:
     Ingresar el siguiente comando para mirar el servicio Properties (GET):
     ```bash
       http://localhost:35000/api/properties/
       http://localhost:35000/api/priperties/{id}
     ```
     Ingresar el siguiente comando para mirar el servicio Auth (POST):
     ```bash
       http://localhost:35000/api/auth/login
       http://localhost:35000/api/auth/register
     ```

### Servicios:

#### Servicios Properties
- (GET) /api/properties/: retorna la lista de todos los properties creados.
- (GET) /api/properties/{id}: retorna el property que se pide a partir del id.
- (POST) /api/properties/: genera un property con todos los elementos ingresados en el body (si hay alguno vacio, se coloca un valor predeterminado.
- (PUT) /api/properties/{id}: actualiza los campos que el usuario desee, a partir del id del property.
- (DELETE) /api/properties/{id}: elimina un property a partir del id.

#### Servicios Auth
- (POST) /api/auth/login: Revisa en la base de datos si existe el usuario que desee ingresar a la app, si es correcto lo lleva a "index.html", sino devuelve error.
- (POST) /api/auth/register: Registra a un usuario con contraseña en la Base de Datos para que pueda ingresar a la aplicación.

## Arquitectura

<img width="1087" height="743" alt="image" src="https://github.com/user-attachments/assets/1caacfed-96ea-48d1-baea-74fa9bab3d5b" />

La estructura del directorio del proyecto es:

<img width="1169" height="830" alt="image" src="https://github.com/user-attachments/assets/2987056c-f95a-4abd-ad74-2d9fc985a190" />

<img width="1136" height="882" alt="image" src="https://github.com/user-attachments/assets/1fa3e8a5-5c87-4867-b55b-b600fa8013a6" />

donde:

- ArepTaller6SecureApplicationDesign.java: programa de ejecución base.
- WebConfig.java: programa de configuracion de cors.
- PropertiesController.java: establece la URI para peticiones Rest y el CRUD base.
- PropertiesService.java: contiene la logica del CRUD.
- PropertiesRepository.java: Genera Peticiones a la base de datos del properties.
- Properties.java: Clase base con la informacion de la estructura de datos.
- PropertiesDto.java: DTO que ayuda a estructurar los datos de entrada del RequestBody en el Controller.
  
- AuthController.java: establece la URI para peticiones Rest para autenticacion y registro.
- AuthService.java: contiene la logica del inicio de sesion.
- UserRepository.java: Genera Peticiones a la base de datos.
- User.java: Clase base con la informacion de la estructura de datos del usuario.
- UserDto.java: DTO que ayuda a estructurar los datos de entrada del RequestBody en el Controller.


## Despliegue en Docker y AWS

### Subir el repositorio a Docker hub
Se generan los archivos (incluidos en este repositorio) "Dockerfile" y "docker-compose.yml".

Una vez agregados, se genera una imagen para "Docker Desktop" con el siguiente comando
(NOTA: en docker hub, crear un repositorio nuevo con el mismo nombre que el build)

 ```bash
  docker build --tag santiagogualdron/ArepTaller6SecureApplicationDesign .
  docker run -d -p 34000:6000 --name ArepTaller6SecureApplicationDesign santiagogualdron/ArepTaller6SecureApplicationDesign
  docker login
  docker push santiagogualdron/ArepTaller6SecureApplicationDesign
 ```

  <img width="946" height="104" alt="image" src="https://github.com/user-attachments/assets/3f79ac09-ca3b-4193-bb9c-59c059d1ebf4" />

<img width="1212" height="683" alt="image" src="https://github.com/user-attachments/assets/c295ddd4-69d7-4719-abd7-b4a5c682c593" />

### Instancias en AWS
para las 3 instancias, en las instancias de front y back, se agregaron las actualizaciones LAMP, con certificados gratuitos de Let's encrypt y Dns Duck.
Para mas informacion seguir [este link](https://docs.aws.amazon.com/linux/al2023/ug/ec2-lamp-amazon-linux-2023.html)

Al generarlas con EC2, una vez que se conecten a cada instancia, se sugiere agregar los siguientes comandos para instalar en ambas Docker.

 ```bash
  sudo yum update -y
  sudo yum install docker
  sudo service docker start
  sudo usermod -a -G docker ec2-user
  docker --version
  docker login
 ```
Luego se debe salir de la maquina virtual y voler a ingresar, para que carguen todos los archivos y el login

<img width="1634" height="99" alt="image" src="https://github.com/user-attachments/assets/08b45469-d87a-4aa1-bf3e-f81d282ed077" />

<img width="1083" height="638" alt="image" src="https://github.com/user-attachments/assets/03237325-6771-48d7-ac59-3c31a507e68a" />

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
  Primero, configurar la seguridad de la instancia para que acepte el paso por el puerto 8443

  <img width="1373" height="480" alt="image" src="https://github.com/user-attachments/assets/55f3be37-c2c6-48da-b931-dddda236ac54" />

  Ahora, dentro de la maquina virtual de la instancia (utilizando la llave .pem) se crea el contenedor para el backend

   ```bash
    ssh -i "llave.pem" ec2-user@ipdelainstancia.compute-1.amazonaws.com
   ```

  Para este punto, se debe cambiar los comandos de Dockerfile, docker-compose.yml y application.properties;
  colocandoles la IP de la instancia de la base de datos,
  si se deja como localhost, no podra utilizar la instancia de mysql previamente creada 
  (Se ha dejado documentado la parte de localhost en los codigos para mayor informacion)

   ```bash
    docker pull santiagogualdron/ArepTaller6SecureApplicationDesign
    docker run -d -p 8443:8443 --name ArepTaller6SecureApplicationDesign santiagogualdron/ArepTaller6SecureApplicationDesign:latest
    docker ps
   ```

  Con el DuckDns "sgrlab07back.duckdns.org" se verifica que la configuracion de HTTPD funcione, con el certificado activo:

  <img width="817" height="97" alt="image" src="https://github.com/user-attachments/assets/a3c35385-0f0e-44f0-b21c-96b0acf9bee0" />

  <img width="1330" height="615" alt="image" src="https://github.com/user-attachments/assets/7a2de42f-bfb7-4e39-b2a7-26c89c8c3514" />



3. Instancia para el Frontend
  Primero, configurar la seguridad de la instancia para que acepte el paso por el puerto 8443

  <img width="1373" height="480" alt="image" src="https://github.com/user-attachments/assets/55f3be37-c2c6-48da-b931-dddda236ac54" />

  Ahora, dentro de la maquina virtual de la instancia (utilizando la llave .pem) se crea el contenedor para el backend

   ```bash
    ssh -i "llave.pem" ec2-user@ipdelainstancia.compute-1.amazonaws.com
   ```

  Para este punto, se debe cambiar los comandos de Dockerfile, docker-compose.yml y application.properties;
  colocandoles la IP de la instancia de la base de datos,
  si se deja como localhost, no podra utilizar la instancia de mysql previamente creada 
  (Se ha dejado documentado la parte de localhost en los codigos para mayor informacion)

   ```bash
    docker pull santiagogualdron/ArepTaller6SecureApplicationDesign
    docker run -d -p 6000:6000 --name ArepTaller6SecureApplicationDesign santiagogualdron/ArepTaller6SecureApplicationDesign:latest
    docker ps
   ```
(NOTA: se genero un contenedor docker para sacar los archivos statics del repositorio y agregarlos al path /var/www/html/)
 
   ```bash
    docker stop ArepTaller6SecureApplicationDesign
    docker rm ArepTaller6SecureApplicationDesign
    docker ps
   ```

Con el DuckDns "sgrlab07.duckdns.org" se verifica que la configuracion de HTTPD funcione, con el certificado activo:

<img width="972" height="628" alt="image" src="https://github.com/user-attachments/assets/d5ccb2a9-1424-42d8-a199-92a9fdd89fa1" />

<img width="933" height="620" alt="image" src="https://github.com/user-attachments/assets/858f52a0-706a-41ac-85c5-7f15ca296015" />

### Prueba de conexion entre las instancias

#### Pings

<img width="961" height="570" alt="image" src="https://github.com/user-attachments/assets/2f4fa2dc-1cd7-49b6-80ba-faf1121e63d2" />

<img width="1050" height="573" alt="image" src="https://github.com/user-attachments/assets/58085bbb-5e94-41bf-9f47-49100ebfb0f0" />

#### Certificados

<img width="1443" height="149" alt="image" src="https://github.com/user-attachments/assets/e2fafe14-bd16-4c28-ae77-6e8128427b73" />

<img width="1465" height="566" alt="image" src="https://github.com/user-attachments/assets/d5908dcf-f308-4e22-b3db-bb586eb63ba2" />

#### Pruebas de llamado entre instancias

<img width="917" height="544" alt="image" src="https://github.com/user-attachments/assets/f69a4564-bfcd-47c2-b5ea-05da1605d7fd" />

<img width="925" height="600" alt="image" src="https://github.com/user-attachments/assets/e989fbc0-3c97-418e-940b-b9d5f7b4d185" />

<img width="918" height="542" alt="image" src="https://github.com/user-attachments/assets/e1260a15-af8e-43bb-90e2-a3b48e44feca" />

<img width="919" height="590" alt="image" src="https://github.com/user-attachments/assets/9f922535-6af6-4ccc-887c-55066260edc7" />

#### Datos encriptados en la DataBase

<img width="748" height="517" alt="image" src="https://github.com/user-attachments/assets/24d9fc63-3537-4079-be04-e9905b06135c" />

<img width="757" height="563" alt="image" src="https://github.com/user-attachments/assets/73ad55c1-c5db-4a8c-ade0-9e2520528831" />

## video de desarrollo
Video de muestra del funcionamiento completo de AWS, despliegue y peticiones:

[Deployment Video](./areptaller6.mp4)

## Reporte de pruebas

### fecha

Fecha:08/10/2025

### Pruebas unitarias:

<img width="1168" height="315" alt="image" src="https://github.com/user-attachments/assets/81496299-840d-46d5-91c2-16e46b0b7cf6" />

donde cada prueba unitaria sirve para:
1. PropertiesTests: pruebas para Properties y PropertiesDto.
     - testPropertiesDtoConstructorAndGettersSetters()
     - testPropertiesDtoAllArgsConstructor()
     - testPropertiesBuilder()
     - testPropertiesEntityConstructorAndGettersSetters()
     - testPropertiesEntityAllArgsConstructor()
     - testPropertiesEntityBuilder()
     - testPropertiesToString()
     - testEqualsAndHashCodeEntity()
     - testEqualsAndHashCode()
2. PropertiesServiceTest: pruebas para PropertiesService.
     - testCreateProperties()
     - testGetProperties()
     - testGetPropertiesById()
     - testUpdateProperties()
     - testUpdatePropertiesIgnoresInvalidValues()
     - testDeleteProperties()
3. PropertiesControllerTest: pruebas para PropertiesController.
     - testCreateProperties()
     - testFindProperties()
     - testFindPropertiesById()
     - testUpdateProperties()
     - testDeleteProperties()

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

- servicio rest /api/auth/login (POST):

  <img width="930" height="602" alt="image" src="https://github.com/user-attachments/assets/1faf14db-2d92-4abe-b456-221da3b88ae0" />

- servicio rest /api/auth/register (POST):

  <img width="948" height="637" alt="image" src="https://github.com/user-attachments/assets/1f134a59-99f2-49da-b643-c5ea59b3c38a" />

## Construido con

[Maven](https://maven.apache.org/index.html) - Dependency Management

[Git](https://git-scm.com) - Version Control System

[Docker](https://www.docker.com/) - Containers and Images

[AWS academy](https://www.awsacademy.com/vforcesite/LMS_Login) - Instances EC2

## Autor

Santiago Gualdron Rincon - [Waldron63](https://github.com/Waldron63)


