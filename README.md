UNIVERSIDAD DE LAS FUERZAS ARMADAS ESPE

Ingeniería en Tecnologías de la Información


<<<<<<< HEAD
=======
##### Proyecto fullstack para gestiÃ³n de discursos, con:
>>>>>>> 4c9f074cfc75e30392c163eeab44d1f0dd1f0719

Integrantes:

Cristian Jácome

Ruth Changalombo



Correo:

chrissjacome@gmail.com



<<<<<<< HEAD
GitHub:
=======
##### \## ?? CÃ³mo ejecutar
>>>>>>> 4c9f074cfc75e30392c163eeab44d1f0dd1f0719

chriss843



SpeechDown - Plataforma de Terapia del Habla



Descripción:

Plataforma fullstack para apoyo en terapia del habla infantil, integrando generación 

de cuentos y actividades terapéuticas personalizadas mediante IA.





\*\*\*\*Características principales\*\*\*\*

* Generación de cuentos personalizados con enfoque terapéutico
* Actividades interactivas basadas en necesidades específicas
* Sistema de perfiles para seguimiento de progreso
* Integración con Google TTS para retroalimentación auditiva
* Interfaz amigable con diseño responsive



\*\*\*\* Requisitos del sistema \*\*\*\*

* Java 17+ (Backend Spring Boot)
* Node.js 16+ (Frontend React)
* Maven 3.6+ (Gestión de dependencias Java)
* MySQL 8.0+ (o PostgreSQL para producción)
* Cuenta de Google Cloud (para Text-to-Speech API)



\*\*\*\*Instalación y despliegue\*\*\*\*



* Configuración inicial

1. Clonar el repositorio:

      git clone https://github.com/tu-usuario/speechdown.git

      cd speechdown

* Backend (Spring Boot)



1. Configurar la base de datos:
   cd backend/src/main/resources/  
   cp application.example.properties application.properties
2. Ejecutar la aplicación:

    cd backend/

    mvn spring-boot:run



* Frontend (React)



1. Configurar las variables de entorno:
   cd frontend/  
   cp .env.example .env
2. Instalar dependencias y ejecutar:



    npm install

    npm start



\*\*\*\*Componentes clave\*\*\*\*\*



1. Frontend: Interfaz de usuario en React
2. Backend: API REST en Spring Boot 
3. IA: Integración con Gemini API para generación de contenido.
4. TTS: Google Cloud Text-to-Speech para audio terapéutico.
5. DB: Almacenamiento relacional para usuarios y progreso.



\*\*\*\*\*Variables de entorno\*\*\*\*\*\*\*

* Backend (application.properties)



&nbsp;   # Database  

&nbsp;     spring.datasource.url=jdbc:mysql://localhost:3306/speechdown-db  

&nbsp;     spring.datasource.username=usuario  

&nbsp;     spring.datasource.password=contraseña  

&nbsp;  # Google Cloud  
&nbsp;    google.cloud.api.key=tu-api-key  
&nbsp;    google.cloud.tts.project-id=tu-project-id  
&nbsp;  # Gemini AI  
&nbsp;    gemini.api.key=tu-api-key  
&nbsp;    gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models  

<<<<<<< HEAD
=======
##### \## ?? DiseÃ±ar
>>>>>>> 4c9f074cfc75e30392c163eeab44d1f0dd1f0719



