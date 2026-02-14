<h1>🩺 PrimarIA</h1>

<p>

  <!-- Estado -->
  <img src="https://img.shields.io/badge/Estado-ffffff?style=flat-square&logo=github&logoColor=000&labelColor=ffffff">
  <img src="https://img.shields.io/badge/En%20Desarrollo-fff4b3?style=flat-square">

  <!-- Versión -->
  <img src="https://img.shields.io/badge/Versión-ffffff?style=flat-square&logo=git&logoColor=000&labelColor=ffffff">
  <img src="https://img.shields.io/badge/0.1-d6eaff?style=flat-square">

</p>


<p>

  <!-- Lenguaje backend -->
  <img height="25" src="https://img.shields.io/badge/Java-17-red?style=for-the-badge&logo=openjdk"> 
  <!-- Framework backend -->
  <img height="25" src="https://img.shields.io/badge/Spring%20Boot-4.0.1-green?style=for-the-badge&logo=springboot">
  <!-- Frontend -->
  <img height="25" src="https://img.shields.io/badge/HTML-5-orange?style=for-the-badge&logo=html5">
  <img height="25" src="https://img.shields.io/badge/CSS-3-blue?style=for-the-badge&logo=css3">
  <img height="25" src="https://img.shields.io/badge/JavaScript-ES6-yellow?style=for-the-badge&logo=javascript">
  <!-- Base de datos -->
  <img height="25" src="https://img.shields.io/badge/MariaDB-X-blue?style=for-the-badge&logo=mysql">

</p>





## 📝 Descripción
PrimarIA es una aplicación web diseñada para la gestión de casos clínicos en un entorno sanitario. Combina un backend desarrollado en Spring Boot con un frontend basado en HTML, CSS y JavaScript para proporcionar una interfaz intuitiva y funcional.

## ✨ Características
- Gestión de casos clínicos: Leer, validar y puntuar casos clínicos.
- Autenticación de usuarios: Sistema de login seguro.
- Interfaz de chat: Comunicación integrada para consultas.
- Base de datos: Integración con base de datos para almacenamiento persistente.
- Seguridad: Configuración de seguridad con Spring Security.

## 💻 Tecnologías Utilizadas
- **Backend**: Java 17, Spring Boot, Spring Security, JPA/Hibernate
- **Frontend**: HTML, CSS, JavaScript
- **Base de Datos**: MySQL
- **Herramientas de Construcción**: Maven
- **Control de versiones**: Git, GitHub

## 🛠️ Instalación
### Prerrequisitos
- Java 17 o superior instalado.
- Maven instalado.
- Una base de datos MySQL configurada (ver `application.properties` para configuración).

### Pasos de Instalación
1. Clona el repositorio:
   ```
   git clone https://github.com/tu-usuario/PrimarIA.git
   cd PrimarIA
   ```

2. Configura la base de datos:
   - Crea una base de datos en MySQL.
   - Actualiza `backend/src/main/resources/application.properties` con tus credenciales de base de datos.

3. Construye el proyecto:
   ```
   cd backend
   ./mvnw clean install
   ```

4. Ejecuta la aplicación:
   ```
   ./mvnw spring-boot:run
   ```

5. Accede al frontend:
   - Abre un navegador y ve a `http://localhost:8080` (o el puerto configurado).

## 📚 Uso
- **Login**: Inicia sesión con tus credenciales.
- **Casos Clínicos**: Navega a la sección de casos clínicos para gestionar registros.
- **Chat**: Utiliza la interfaz de chat para comunicaciones.

## 🗂️ Estructura del Proyecto

```
PrimarIA/
├── README.md                          # Este archivo
├── Acta-Reuniones.md                  # Actas de reuniones del equipo
├── NotaConstruccionDeEquipo.md        # Notas sobre construcción del equipo
├── BBDDprimarIA_pruebas.sql           # Script SQL para pruebas de base de datos
│
├── backend/                           # 🔧 Backend (Spring Boot)
│   ├── mvnw                           # Maven Wrapper (Linux/Mac)
│   ├── mvnw.cmd                       # Maven Wrapper (Windows)
│   ├── pom.xml                        # Configuración de dependencias Maven
│   │
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── es/riberadeltajo/primaria_sanitycode/
│       │   │       ├── PrimarIaSanitycodeApplication.java          # Clase principal
│       │   │       ├── config/
│       │   │       │   └── SecurityConfig.java                    # Configuración de seguridad
│       │   │       ├── controller/
│       │   │       │   └── CasoClinicoController.java             # Endpoints REST
│       │   │       ├── service/
│       │   │       │   └── CasoClinicoService.java                # Lógica de negocio
│       │   │       ├── repository/
│       │   │       │   ├── CasoClinicoRepository.java             # Acceso a datos
│       │   │       │   └── CasoClinicoOriginalRepository.java
│       │   │       └── model/entity/
│       │   │           ├── CasoClinico.java                       # Entidades JPA
│       │   │           ├── CasoClinicoOriginal.java
│       │   │           ├── Usuario.java
│       │   │           └── Whitelist.java
│       │   │
│       │   └── resources/
│       │       └── application.properties                         # Configuración de la aplicación
│       │
│       └── test/
│           └── java/es/riberadeltajo/primaria_sanitycode/
│               └── PrimarIaSanitycodeApplicationTests.java         # Tests unitarios
│
└── frontend/                          # 🎨 Frontend (HTML5/CSS3/JavaScript)
    ├── login.html                     # Página de login
    ├── casosclinico.html              # Página de casos clínicos
    ├── chat.html                      # Página de chat
    │
    ├── css/                           # Estilos
    │   ├── login.css
    │   ├── casoclinico.css
    │   ├── chat.css
    │   └── header.css
    │
    ├── js/                            # Scripts
    │   └── script.js
    │
    └── assets/
        └── img/                       # Imágenes
```

### 📊 Descripción de directorios principales

| Directorio | Descripción |
|-----------|-------------|
| `backend/` | Código fuente del servidor Spring Boot con arquitectura MVC |
| `backend/src/main/java/` | Código Java (controllers, servicios, modelos, repositorios) |
| `backend/src/main/resources/` | Archivos de configuración y propiedades |
| `backend/src/test/` | Tests unitarios |
| `frontend/` | Aplicación web estática (HTML, CSS, JavaScript) |
| `frontend/css/` | Hojas de estilos |
| `frontend/js/` | Scripts JavaScript |
| `frontend/assets/` | Recursos multimedia (imágenes, etc.) |

## 👥 Equipo de desarrollo - SanityCode
# 🧩 Frontend – SaniDev

| Nombre                    | Rol        |
|---------------------------|------------|
| Lucas Melero Mendiondo   | Frontend Leader   |
| Rubén Mora López         | Frontend   |
| Christian García Ruiz    | Frontend   |


# ⚙️ Backend – FourCode

| Nombre                          | Rol                               |
|---------------------------------|------------------------------------|
| David Egea Muñoz               | Tester                             |
| María Carolina García Correas  | Dev, despliegue, Backend Leader            |
| José Antonio García Pajares    | Documentación y seguridad          |
| Marina Miguel Zapata           | Base de datos                      |


## 🤝 Contribución
1. Haz un fork del proyecto.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commit (`git commit -am 'Añade nueva funcionalidad'`).
4. Push a la rama (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.

## 📄 Licencia
Proyecto realizado para la asignatura de Proyecto Intermodular del CFGS de Desarrollo de Aplicaciones Multiplataforma (DAM) en el IES Ribera del Tajo (Talavera de la Reina).

## 📬 Contacto
Para preguntas o soporte, contacta al equipo de desarrollo.