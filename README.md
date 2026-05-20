<h1>🩺 PrimarIA</h1>

<p align="center">
  <img src="./frontend/assets/img/logo-primaria-sanitycode.svg" width="90">
</p>

<p align="center">

  <!-- Estado -->
  <img src="https://img.shields.io/badge/Estado-ffffff?style=flat-square&logo=github&logoColor=000&labelColor=ffffff">
  <img src="https://img.shields.io/badge/En%20Desarrollo-fff4b3?style=flat-square">

  <!-- Versión -->
  <img src="https://img.shields.io/badge/Versión-ffffff?style=flat-square&logo=git&logoColor=000&labelColor=ffffff">
  <img src="https://img.shields.io/badge/0.1-d6eaff?style=flat-square">

</p>


<p align="center"> 

  <!-- Lenguaje backend -->
  <img height="25" src="https://img.shields.io/badge/Java-17-red?style=for-the-badge&logo=openjdk"> 
  <!-- Framework backend -->
  <img height="25" src="https://img.shields.io/badge/Spring%20Boot-4.0.1-green?style=for-the-badge&logo=springboot">
  <!-- Frontend -->
  <img height="25" src="https://img.shields.io/badge/HTML-5-orange?style=for-the-badge&logo=html5">
  <img height="25" src="https://img.shields.io/badge/CSS-3-blue?style=for-the-badge&logo=css3">
  <img height="25" src="https://img.shields.io/badge/JavaScript-ES6-yellow?style=for-the-badge&logo=javascript">
  <!-- Base de datos -->
  <img height="25" src="https://img.shields.io/badge/MariaDB-10.6-blue?style=for-the-badge&logo=mysql">
  <!-- Docker -->
   <img height="25" src="https://img.shields.io/badge/Docker-24.0-blue?style=for-the-badge&logo=docker">
   <img height="25" src="https://img.shields.io/badge/Docker--Compose-1.29-blue?style=for-the-badge&logo=docker">
   <!-- GitHub Actions -->
   <img height="25" src="https://img.shields.io/badge/GitHub%20Actions-CI%2FCD-black?style=for-the-badge&logo=githubactions">


</p>





## 📝 Descripción
PrimarIA es una aplicación web diseñada para la gestión y validación de casos clínicos en un entorno sanitario. Combina un backend desarrollado en Spring Boot con un frontend basado en HTML, CSS y JavaScript para proporcionar una interfaz intuitiva y funcional. Integra autenticación OAuth2 con Google, un sistema de validación de casos clínicos con inteligencia artificial y un panel de administración.

## ✨ Características
- **Gestión de casos clínicos:** Leer, validar y puntuar casos clínicos originales y de muestra.
- **Validaciones con IA:** Sistema de validación automática integrado con un servicio de IA externo.
- **Autenticación de usuarios:** Login seguro con sesión propia y OAuth2 (Google).
- **Panel de administración:** Gestión de usuarios y métricas del sistema.
- **Interfaz de chat:** Chat integrado con historial de conversaciones persistente.
- **Dataset pipeline:** Scripts Python para generación y tratamiento de casos clínicos de muestra.
- **Base de datos:** Integración con MariaDB para almacenamiento persistente.
- **Seguridad:** Spring Security con gestión de sesiones, whitelist de usuarios y cookies seguras.
- **Despliegue con Docker:** Contenedores para backend y frontend con CI/CD automático vía GitHub Actions.


## 💻 Tecnologías Utilizadas
- **Backend**: Java 17, Spring Boot 4.0.1, Spring Security, OAuth2 Client, JPA/Hibernate, Lombok
- **Frontend**: HTML5, CSS3, JavaScript ES6 (arquitectura modular por páginas y servicios)
- **Base de Datos**: MariaDB / MySQL
- **Despliegue**: Docker, Docker Compose, Nginx, GitHub Actions
- **Dataset**: Python (pipeline de generación de casos clínicos)
- **Herramientas de Construcción**: Maven (Maven Wrapper incluido)
- **Control de versiones**: Git, GitHub


## 🛠️ Instalación
### Prerrequisitos
- Java 17 o superior instalado.
- Maven instalado (o usar el Maven Wrapper incluido `./mvnw`).
- Una base de datos MariaDB/MySQL configurada.
- Credenciales de Google OAuth2 (Client ID y Client Secret).


### Pasos de Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/PrimarIA.git
   cd PrimarIA
   ```
 
2. Configura las credenciales. Crea el archivo `backend/src/main/resources/application-secret.yml` con el siguiente contenido:
   ```yaml
   spring:
     datasource:
       username: tu_usuario_bd
       password: tu_contraseña_bd
   GOOGLE_CLIENTE_ID: tu_google_client_id
   GOOGLE_CLIENTE_SECRETO: tu_google_client_secret
   ```
 
3. Revisa la configuración en `backend/src/main/resources/application.yml` (URL de base de datos, perfil activo, etc.).
4. Construye el proyecto:
   ```bash
   cd backend
   ./mvnw clean install
   ```
 
5. Ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```
 
6. Accede al frontend:
   - Abre un navegador y ve a `http://localhost:8080` (o el puerto configurado).


### Despliegue con Docker
 
Para levantar el entorno completo (backend + frontend):
```bash
docker compose up --build -d
```
 
> El despliegue en producción se realiza automáticamente mediante GitHub Actions al hacer push a `main`.


## Acceso desde diferentes entornos de ejecución

| Entorno              | URL de entrada                                        |
| -------------------- | ----------------------------------------------------- |
| Dev sin login        | `http://127.0.0.1:5500/frontend/index.html`           |
| Prod local con login | `http://127.0.0.1:5500/frontend/index.html?mode=prod` |
| Deploy               | `https://sanitycode.riberadeltajo.es/login.html`      |


## 📚 Uso
- **Login**: Inicia sesión con tus credenciales o mediante Google OAuth2.
- **Casos Clínicos**: Navega a la sección de casos clínicos para visualizar, validar y puntuar registros.
- **Panel Admin**: Accede al panel de administración para gestionar usuarios y consultar métricas.
- **Chat**: Utiliza la interfaz de chat para realizar consultas con historial de conversaciones.


## 🗂️ Estructura del Proyecto

```
PrimarIA/
├── README.md                              # Este archivo
├── Acta-Reuniones.md                      # Actas de reuniones del equipo
├── NotaConstruccionDeEquipo.md            # Notas sobre construcción del equipo
├── BBDDprimarIA_script.sql                # Script SQL de la base de datos
├── docker-compose.yml                     # Orquestación de contenedores
├── setup-servidor.sh                      # Script de configuración del servidor
│
├── .github/
│   └── workflows/
│       └── deploy.yml                     # CI/CD con GitHub Actions
│
├── dataset/                               # 🧪 Pipeline de datos
│   ├── casos_clinicos_pipeline.py         # Generación de casos clínicos
│   ├── casos_clinicos_muestra_pipeline.py # Generación de casos de muestra
│   └── requirements.txt                   # Dependencias Python
│
├── backend/                               # 🔧 Backend (Spring Boot)
│   ├── Dockerfile
│   ├── mvnw / mvnw.cmd                    # Maven Wrapper
│   ├── pom.xml                            # Dependencias Maven
│   │
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── es/riberadeltajo/primaria_sanitycode/
│       │   │       ├── PrimarIaSanitycodeApplication.java
│       │   │       ├── config/
│       │   │       │   ├── SecurityConfig.java              # Seguridad y OAuth2
│       │   │       │   └── OAuth2SuccessHandler.java        # Gestión login OAuth2
│       │   │       ├── controller/
│       │   │       │   ├── AdminController.java             # Endpoints de administración
│       │   │       │   ├── AuthController.java              # Autenticación
│       │   │       │   ├── CasoClinicoMuestraController.java
│       │   │       │   ├── CasoClinicoOriginalController.java
│       │   │       │   ├── ChatController.java              # Chat con historial
│       │   │       │   └── ValidacionesController.java      # Validación con IA
│       │   │       ├── service/
│       │   │       │   ├── AiChatService.java               # Integración IA
│       │   │       │   ├── CasoClinicoMuestraService.java
│       │   │       │   ├── CasoClinicoOriginalService.java
│       │   │       │   ├── ConversacionService.java
│       │   │       │   ├── OAuth2UserService.java
│       │   │       │   └── ValidacionService.java
│       │   │       ├── repository/
│       │   │       │   ├── CasoClinicoMuestraRepository.java
│       │   │       │   ├── CasoClinicoOriginalRepository.java
│       │   │       │   ├── ConversacionRepository.java
│       │   │       │   ├── MensajeConversacionRepository.java
│       │   │       │   ├── UsuarioRepository.java
│       │   │       │   ├── ValidacionRepository.java
│       │   │       │   └── WhitelistRepository.java
│       │   │       └── model/
│       │   │           ├── entity/
│       │   │           │   ├── CasoClinicoMuestra.java
│       │   │           │   ├── CasoClinicoOriginal.java
│       │   │           │   ├── Conversacion.java
│       │   │           │   ├── MensajeConversacion.java
│       │   │           │   ├── Usuario.java
│       │   │           │   ├── Validacion.java
│       │   │           │   └── Whitelist.java
│       │   │           └── dto/
│       │   │               ├── ApiRequest.java / ApiResponse.java
│       │   │               ├── CasoPuntuadoDTO.java
│       │   │               ├── ChatMessage.java / ChatRequest.java / ChatResponse.java
│       │   │               ├── MetricaAgenteDTO.java
│       │   │               └── ValidacionRequest.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml                          # Configuración base
│       │       ├── application-dev.yml                      # Perfil desarrollo
│       │       ├── application-prod.yml                     # Perfil producción
│       │       ├── application-deploy.yml                   # Perfil despliegue
│       │       └── application-test.yml                     # Perfil tests
│       │
│       └── test/
│           └── java/es/riberadeltajo/primaria_sanitycode/
│               ├── MuestrasTest.java
│               ├── config/OAuth2SuccessHandlerTest.java
│               ├── controller/
│               │   ├── AdminControllerTest.java
│               │   ├── CasoClinicoControllerTest.java
│               │   └── ChatControllerTest.java
│               ├── integration/Integraciones.java
│               ├── repository/RepositoryTest.java
│               ├── security/SecurityTest.java
│               └── service/
│                   ├── AiChatServiceTest.java
│                   └── ValidacionServiceTest.java
│
└── frontend/                              # 🎨 Frontend (HTML5/CSS3/JavaScript)
    ├── Dockerfile
    ├── nginx.conf                         # Configuración Nginx
    ├── index.html                         # Página principal / login
    ├── casosclinico.html                  # Gestión de casos clínicos
    ├── chat.html                          # Interfaz de chat
    ├── admin.html                         # Panel de administración
    │
    ├── css/
    │   ├── login.css
    │   ├── casoclinico.css
    │   ├── chat.css
    │   ├── admin.css
    │   └── header.css
    │
    ├── js/
    │   ├── iniciosesion.js
    │   ├── components/
    │   │   └── AppHeader.js               # Componente de cabecera reutilizable
    │   ├── pages/
    │   │   ├── login.js
    │   │   ├── validation.js
    │   │   ├── chat.js
    │   │   └── admin.js
    │   ├── services/
    │   │   ├── ApiService.js              # Capa de comunicación con el backend
    │   │   └── config.js                  # Configuración de entornos
    │   └── utils/
    │       └── DOMUtils.js
    │
    └── assets/
        └── img/
```

### 📊 Descripción de directorios principales

| Directorio                    | Descripción                                                                           |
| ----------------------------- | ------------------------------------------------------------------------------------- |
| `backend/`                    | Código fuente del servidor Spring Boot con arquitectura MVC                           |
| `backend/src/main/java/`      | Código Java (controllers, servicios, modelos, repositorios, DTOs)                     |
| `backend/src/main/resources/` | Archivos de configuración por perfil (dev, prod, deploy, test)                        |
| `backend/src/test/`           | Tests unitarios, de integración y de seguridad                                        |
| `frontend/`                   | Aplicación web estática con arquitectura modular (pages, services, components, utils) |
| `dataset/`                    | Scripts Python para la generación del pipeline de casos clínicos                      |
| `.github/workflows/`          | Pipeline CI/CD de despliegue automático con GitHub Actions                            |


## 👥 Equipo de desarrollo - SanityCode


| Nombre                          | Rol                               |
|---------------------------------|------------------------------------|
| David Egea Muñoz               | Tester, Dev                         |
| María Carolina García Correas  | Dev, despliegue, Backend Leader     |
| Marina Miguel Zapata           | Base de datos, Documentación, Dev   |


## 🤝 Contribución
1. Haz un fork del proyecto.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commit (`git commit -am 'Añade nueva funcionalidad'`).
4. Push a la rama (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.

## 📄 Licencia
Proyecto realizado para la asignatura de Proyecto Intermodular del CFGS de Desarrollo de Aplicaciones Multiplataforma (DAM) y Desarrollo de Aplicaciones Web (DAW) en el IES Ribera del Tajo (Talavera de la Reina).

## 📬 Contacto
Para preguntas o soporte, contacta al equipo de desarrollo.

<br>
<p align="center">
  <img src="./frontend/assets/img/logo-primaria-sanitycode.svg" width="90">
</p>
