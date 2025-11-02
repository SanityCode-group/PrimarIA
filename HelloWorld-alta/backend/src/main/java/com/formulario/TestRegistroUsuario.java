package com.formulario;

import java.util.List;

import com.formulario.model.Usuario;
import com.formulario.util.JsonFileHandler;

public class TestRegistroUsuario {
    public static void main(String[] args) {
        JsonFileHandler jsonFileHandler = new JsonFileHandler();

        // Crear un usuario de prueba
        Usuario usuario1 = new Usuario("Pepe", "pepe", "pepe@example.com", "1234");
        Usuario usuario2 = new Usuario("Juan", "juan", "juan@example.com", "1234");
        Usuario usuario3 = new Usuario("Lola", "lola","lola@example.com",  "1234");

        try {
            // Asegura que el archivo existe
            jsonFileHandler.crearFicheroJson();

            // Guarda el usuario
            jsonFileHandler.guardarUsuario(usuario1);
            jsonFileHandler.guardarUsuario(usuario2);
            jsonFileHandler.guardarUsuario(usuario3);

            System.out.println("-> Usuario registrado correctamente.");

            // Leer y mostrar todos los usuarios registrados
            List<Usuario> usuarios = jsonFileHandler.mostrarUsuarios();
            System.out.println("\n>>> Lista de usuarios registrados:");
            for (Usuario u : usuarios) {
                System.out.println("- Nombre: " + u.getNombre() + " | Username: " + u.getUsername() + " | Email: " + u.getEmail());
            }

        } catch (Exception e) {
            System.out.println("-> Error al registrar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
