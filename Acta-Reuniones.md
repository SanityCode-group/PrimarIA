# 🧾 Acta de Sesión 20/10/2025
### Asistentes

| Nombre                             | Asistencia |
|------------------------------------|------------|
| Lucas Melero Mendiondo             | ✔️         |
| Rubén Mora López                   |  ❌         |
| Christian García Ruiz              | ✔️         |
| David Egea Muñoz                   | ✔️         |
| María Carolina García Correas      | ✔️         |
| José Antonio García Pajares        | ✔️         |
| Marina Miguel Zapata               | ✔️         |


### Objetivos
Definir roles y responsabilidades. Poder acceder al dominio.

### Desarrollo de la sesión
- Creación de cuenta para acceder en Hugging Face al dataset de prueba.
- Establecimiento de roles para cada miembro del equipo.
- Intento de acceso al servidor donde se alojará la herramienta.

### Tareas pendientes
1. PPT de modo de trabajo
2. Desarrollo del frontend
3. Desarrollo del backend

---------------------------------------------------


# 🧾 Acta de Sesión 27/10/2025
### Asistentes

| Nombre                             | Asistencia |
|------------------------------------|------------|
| Lucas Melero Mendiondo             | ✔️         |
| Rubén Mora López                   | ❌          |
| Christian García Ruiz              | ✔️         |
| David Egea Muñoz                   | ✔️         |
| María Carolina García Correas      | ✔️         |
| José Antonio García Pajares        | ✔️         |
| Marina Miguel Zapata               | ✔️         |

### Objetivos

Presentación de forma de trabajo en la asignatura de Proyecto Intermodular para visitantes de instituto de Barcelona.

### Desarrollo de la sesión

Primera parte de la sesión de reunión con visitantes.
En la segunda parte de la sesión se ha establecido conexión con el server.

### Tareas pendientes

1. Conectar front y back.
2. Crear README que reemplace la nota de construcción de equipo.
3. Estructurar carpeta del proyecto para documentación (`documentation/` o `docs/`) y seguimiento de sesiones:
   - 3.1. Documentación de la herramienta.
   - 3.2. Carpeta `Sessions` para guardar actas de sesiones.
   - 3.3. Documento de nota de construcción de equipo.
4. Establecer calendario de reuniones.

---------------------------------------------------


# 🧾 Acta de Sesión 03/11/2025

### Asistentes

| Nombre                             | Asistencia |
|------------------------------------|------------|
| Lucas Melero Mendiondo             | ✔️         |
| Rubén Mora López                   | ✔️         |
| Christian García Ruiz              | ✔️         |
| David Egea Muñoz                   | ✔️         |
| María Carolina García Correas      | ✔️         |
| José Antonio García Pajares        | ✔️         |
| Marina Miguel Zapata               | ✔️         |

### Objetivos

Tratar requisitos del software y funcionalidades mínimas.


### Desarrollo de la sesión

Elaboración de funcionalidades mínimas de la herramienta.

### Acuerdos y decisioens tomadas

La herramienta tendrá:
- Sistema de gestión de sesiones para los médicos.
- Ficha para mostrar casos clínicos.
- Sistema de validación de casos clínicos según ciertos requisitos marcados.
- Sistema de chatbot.

### Tareas cumplidas de la sesión anterior

Conexión front-back.


### Tareas pendientes de la sesión anterior

1. Crear README que reemplace la nota de construcción de equipo.
2. Estructurar carpeta del proyecto para documentación (`documentation/` o `docs/`) y seguimiento de sesiones:
   - 2.1. Documentación de la herramienta.
   - 2.2. Carpeta `Sessions` para guardar actas de sesiones.
   - 2.3. Documento de nota de construcción de equipo.
3. Establecer calendario de reuniones.

### Tareas pendientes

Reunirse para debatir Software Requirements Specification.
- Definir cuándo se abrirá la conversación en el chatbot y cuándo se cerrará.
Elaborar Documento -> Software Requirements Specification (SRS) based on IEEE Std 830-1998



---------------------------------------------------
# 🧾 Acta de Sesión 05/11/2025 (reunión Discord)

### Asistentes


| Nombre                             | Asistencia |
|------------------------------------|------------|
| Lucas Melero Mendiondo             | ✔️         |
| Rubén Mora López                   | ✔️         |
| Christian García Ruiz              | ❌          |
| David Egea Muñoz                   | ✔️         |
| María Carolina García Correas      | ✔️         |
| José Antonio García Pajares        | ✔️         |
| Marina Miguel Zapata               | ✔️         |


### Objetivos
Reunirse para debatir Software Requirements Specification.

### Desarrollo de la sesión
Se ha elaborado el  Documento Software Requirements Specification (SRS) based on IEEE Std 830-1998




---------------------------------------------------
# 🧾 Acta de Sesión 12/01/2026

### Asistentes


| Nombre                             | Asistencia |
|------------------------------------|------------|
| Lucas Melero Mendiondo             | ✔️         |
| Rubén Mora López                   | ❌          |
| Christian García Ruiz              | ✔️         |
| David Egea Muñoz                   | ✔️         |
| María Carolina García Correas      | ✔️         |
| José Antonio García Pajares        | ✔️         |
| Marina Miguel Zapata               | ✔️         |


### Objetivos

- Repasar lo hecho hasta el momento y decidir modificaciones y correcciones.
- Planificar el diseño del front.

### Tareas cumplidas de la sesión anterior
- Creación de la base de datos.
- Creación de las entidades de Java correspondientes a esa base de datos.
- Desarrollo de lógica para mostrar y modificar casos clínicos.

### Tareas pendientes
- Generar login (OAuth).
- Generar front.
- Generar test (una vez implementado el back).



---------------------------------------------------
# 🧾 Acta de Sesión 19/01/2026

### Asistentes


| Nombre                             | Asistencia |
|------------------------------------|------------|
| Lucas Melero Mendiondo             | ✔️         |
| Rubén Mora López                   | ✔️         |
| Christian García Ruiz              | ✔️         |
| David Egea Muñoz                   | ✔️         |
| María Carolina García Correas      | ✔️         |
| José Antonio García Pajares        | ✔️         |
| Marina Miguel Zapata               | ✔️         |


### Objetivos

- Revisar prototipo de front.
- Establecer correcciones del back.


### Desarrollo de la sesión

Se han tomado decisiones sobre cambios en el front y en el back y discutido cómo hacer cambios.


### Acuerdos y decisiones tomadas

El cliente cambia requisitos: ya no se modificarán los casos clínicos, sólo se valorarán. Por lo tanto:
- Modificar tablas de la base de datos y sus correspondientes entidades.
- Modificar lógica de backend para no modificar casos clínicos.


### Tareas pendientes

- Modificar tablas de BD.
- Modificar entidades de Spring Boot.
- Añadir campos de observaciones y valoraciones según criterios en el front.
- Modificar lógica backend para añadir valoraciones y eliminar la modificación de campos del caso clínico.


---------------------------------------------------
# 🧾 Acta de Sesión 09/02/2026

### Asistentes

| Nombre                             | Asistencia |
|------------------------------------|------------|
| Lucas Melero Mendiondo             | ✔️         |
| Rubén Mora López                   | ❌         |
| Christian García Ruiz              |  ✔️        |
| David Egea Muñoz                   | ✔️         |
| María Carolina García Correas      | ✔️         |
| José Antonio García Pajares        | ✔️         |
| Marina Miguel Zapata               |   ❌       |


### Objetivos
Crear un algoritmo el cual devuela el caso clinico elegido de los 100 o 200 seleccionados para realizar las valoraciones

### Temas tratados / Orden del día

Definificion de algoritmo de los casos para mostrar
Comprobacion del diseño de frontend
Revision de documentacion

### Tareas pendientes 
Un medico tiene que recibir 3 casos pseudo-aleatorios generados por 3 ias diferentes sobre una misma temática (que surja de la misma bibliografia).

Como mostrar caso clinico mediante un algoritmo con las sguientes cualdades
- que no se le haya mostrado ya
- aleatorio dirigido.  aleatorio sobre un conjunto reducido de casos (no coger los miles de casos que hay) (100 o 200).
- de esos, controlar que al menos 2 medicos pasen por cada caso. maximo 3 medicos
- 20 medicos evaluan.
- varias ias generando casos sinteticos. De un pedazo de bibliografia, se generen al menos 3 casos sinteticos. Quiero saber también de qué IA me fio más (de los casos sintéticos que genera). RECAMARA - NO SEGURO.


---------------------------------------------------
# 🧾 Acta de Sesión 15/02/2026

### Asistentes

| Nombre                             | Asistencia |
|------------------------------------|------------|
| Lucas Melero Mendiondo             | ✔️  ❌       |
| Rubén Mora López                   | ✔️  ❌       |
| Christian García Ruiz              | ✔️  ❌       |
| David Egea Muñoz                   | ✔️  ❌       |
| María Carolina García Correas      | ✔️  ❌       |
| José Antonio García Pajares        | ✔️  ❌       |
| Marina Miguel Zapata               | ✔️  ❌       |


### Objetivos

María C García Correas comunica al resto del equipo avances en el trabajo.

*Canal de comunicación:* WhatsApp

### Orden del día

Tareas realizadas:
- Modificación del esquema de las tablas de la Base de Datos conforme a criterios.
- Actualización de dataset en BD.
- Modificación de entidades Java + Controllers + Services + Repository
- Crear endpoints.
- Comunicación frontend con backend.
- Configuración de CORS globales.
- Adaptar HTML para mostrar datos dinámicos.
- Probar integración backend - frontend.

### Tareas cumplidas de la sesión anterior
- Actualización de BD y entidades.
- Muestra de caso clínico aleatorio.

### Tareas pendientes

- Revisión de novedades por parte del equipo.
- Trabajar en algoritmo.
- Trabajar en despliegue.
- Trabajar en seguridad de servidor.
- Trabajar en testing.
- Revisión de documentación.



---------------------------------------------------
# 🧾 Acta de Sesión xx/xx/2026

### Asistentes

| Nombre                             | Asistencia |
|------------------------------------|------------|
| Lucas Melero Mendiondo             | ✔️  ❌       |
| Rubén Mora López                   | ✔️  ❌       |
| Christian García Ruiz              | ✔️  ❌       |
| David Egea Muñoz                   | ✔️  ❌       |
| María Carolina García Correas      | ✔️  ❌       |
| José Antonio García Pajares        | ✔️  ❌       |
| Marina Miguel Zapata               | ✔️  ❌       |


### Objetivos

aaa

### Temas tratados / Orden del día

aaa

### Desarrollo de la sesión

aaa

### Acuerdos y decisiones tomadas

aaa

### Tareas cumplidas de la sesión anterior

aaa

### Tareas pendientes de la sesión anterior

aaa

### Tareas pendientes

aaa


---------------------------------------------------

# 🧾 Acta de Sesión xx/xx/2026

### Asistentes

| Nombre                             | Asistencia |
|------------------------------------|------------|
| Lucas Melero Mendiondo             | ✔️  ❌       |
| Rubén Mora López                   | ✔️  ❌       |
| Christian García Ruiz              | ✔️  ❌       |
| David Egea Muñoz                   | ✔️  ❌       |
| María Carolina García Correas      | ✔️  ❌       |
| José Antonio García Pajares        | ✔️  ❌       |
| Marina Miguel Zapata               | ✔️  ❌       |


### Objetivos

aaa

### Temas tratados / Orden del día

aaa

### Desarrollo de la sesión

aaa

### Acuerdos y decisiones tomadas

aaa

### Tareas cumplidas de la sesión anterior

aaa

### Tareas pendientes de la sesión anterior

aaa

### Tareas pendientes

aaa


---------------------------------------------------

# 🧾 Acta de Sesión xx/xx/2026

### Asistentes

| Nombre                             | Asistencia |
|------------------------------------|------------|
| Lucas Melero Mendiondo             | ✔️  ❌       |
| Rubén Mora López                   | ✔️  ❌       |
| Christian García Ruiz              | ✔️  ❌       |
| David Egea Muñoz                   | ✔️  ❌       |
| María Carolina García Correas      | ✔️  ❌       |
| José Antonio García Pajares        | ✔️  ❌       |
| Marina Miguel Zapata               | ✔️  ❌       |


### Objetivos

aaa

### Temas tratados / Orden del día

aaa

### Desarrollo de la sesión

aaa

### Acuerdos y decisiones tomadas

aaa

### Tareas cumplidas de la sesión anterior

aaa

### Tareas pendientes de la sesión anterior

aaa

### Tareas pendientes

aaa


---------------------------------------------------

# 🧾 Acta de Sesión xx/xx/2026

### Asistentes

| Nombre                             | Asistencia |
|------------------------------------|------------|
| Lucas Melero Mendiondo             | ✔️  ❌       |
| Rubén Mora López                   | ✔️  ❌       |
| Christian García Ruiz              | ✔️  ❌       |
| David Egea Muñoz                   | ✔️  ❌       |
| María Carolina García Correas      | ✔️  ❌       |
| José Antonio García Pajares        | ✔️  ❌       |
| Marina Miguel Zapata               | ✔️  ❌       |


### Objetivos

aaa

### Temas tratados / Orden del día

aaa

### Desarrollo de la sesión

aaa

### Acuerdos y decisiones tomadas

aaa

### Tareas cumplidas de la sesión anterior

aaa

### Tareas pendientes de la sesión anterior

aaa

### Tareas pendientes

aaa


---------------------------------------------------