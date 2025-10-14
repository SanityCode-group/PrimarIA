# 🧠 HelloWorld SanityCode

Formulario web con backend en Java (Spring Boot) y frontend en HTML/CSS/JS. Permite registrar usuarios a través de un formulario y visualizar la lista de registrados.

## 📋 Campos del formulario

El formulario incluye los siguientes campos:

- **Nombre completo**
- **Usuario**
- **Email**
- **Contraseña**

## 🏗️ Estructura del proyecto
```
HelloWorld-alta/
│
├── backend/
│   ├── src/main/java/com/formulario/demo/
│   │   ├── DemoApplication.java
│   │   ├── controller/
│   │   │   └── AltaController.java
│   │   ├── model/
│   │   │   └── Usuario.java
│   │   └── util/
│   │       └── JsonFileHandler.java
│   └── pom.xml
│
├── usuarios.json
│
└── frontend/
    ├── index.html
    ├── css/
    │   └── styles.css
    └── js/
        └── connection.js
```

## 🔌 Endpoints disponibles

| Método | Endpoint     | Método Java       | Descripción                                                                 |
|--------|--------------|-------------------|-----------------------------------------------------------------------------|
| GET    | `/api/user`  | `verUsuarios()`   | Devuelve la lista de usuarios con nombre, usuario y email.                  |
| POST   | `/api/user`  | `altaUsuario()`   | Crea un nuevo usuario y lo guarda en `usuarios.json`.                       |

## 🚀 Cómo ejecutar

### Backend

1. Asegúrate de tener Java y Maven instalados.
2. Desde la carpeta `backend`, ejecuta:
```bash
mvn spring-boot:run
```

### Frontend

1. Abre `frontend/index.html` en tu navegador.
2. Completa el formulario y envíalo para registrar un nuevo usuario.

## 📂 Almacenamiento

Los usuarios se guardan en el archivo `usuarios.json` en formato JSON.
