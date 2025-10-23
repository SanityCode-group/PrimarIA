// Registrar usuario
    document.getElementById("formRegistro").addEventListener("submit", async (event) => {
      event.preventDefault();

      const usuario = {
        nombre: document.getElementById("nombre").value,
        usuario: document.getElementById("usuario").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
      };

      try {
        const response = await fetch("http://localhost:8080/api/user/registro", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(usuario)
        });

        const text = await response.text();
        alert("Respuesta del servidor: " + text);
      } catch (error) {
        alert("Error al conectar con el backend");
        console.error(error);
      }
    });