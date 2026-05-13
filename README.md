Ecommerce Backend - Java Puro

Este es un sistema de gestión para un eCommerce desarrollado en Java, enfocado en la robustez de los datos y la gestión eficiente de la concurrencia. El proyecto implementa una arquitectura por capas (N-Tier) y utiliza MySQL como motor de persistencia.
Las especificaciones se toman de las del material de la Pre-Entrega de proyecto y lo visto en clases de la comisión 26138

🛠️ Tecnologías y Entorno
Lenguaje: Java (Versión compatible con tu entorno actual).

IDE: Visual Studio Code (Windsurf).

Base de Datos: MySQL 8.x / 9.x.

Librerías: mysql-connector-j-9.7.0.jar.

OS: Ubuntu 24.04 LTS (Noble Numbat).

🏗️ Arquitectura del Proyecto

El proyecto está organizado bajo el paquete base com.techlab.ecommerce y se divide en las siguientes capas:

model: Contiene los POJOs (Plain Old Java Objects) como Producto y Categoria.

repository.dao: Lógica de acceso a datos utilizando JDBC y  Stored Procedures (A migrar cuando se comience con Spring Boot).

service: Capa de negocio donde se aplican las validaciones antes de persistir la información.

exception: Definición de excepciones personalizadas como DatabaseConnectionException (Runtime).

config: Configuración de conexión y el DatabaseInitializer para la creación automática de tablas.

ui: Interfaz de línea de comandos (CLI) para la interacción con el usuario.



🚀 Instalación y Uso

Asegúrate de tener instalado MySQL y que el puerto 3306 esté disponible.

Configura tus credenciales (Usuario/Password) en DatabaseInitializer.java.

Importa el proyecto en VS Code.

Agrega el driver de MySQL ubicado en la carpeta /lib a las Referenced Libraries de Java.

Ejecuta Main.java. El sistema creará automáticamente la base de datos ecommerce y sus tablas.

📈 Próximos Pasos
[ ] Migración a Spring Boot.



Desarrollado por: Daniel Gonzalo Capelli  y TechLab  