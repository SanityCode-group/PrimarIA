package com.formulario.controller;


import java.io.IOException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formulario.model.Usuario;
import com.formulario.util.JsonFileHandler;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*") //permitir peticiones desde frontend
public class UsuarioController {

    private final JsonFileHandler jsonFileHandler;

    /**
     * Constructor de la clase UsuarioController
     */
    public UsuarioController(JsonFileHandler jsonFileHandler) {
        this.jsonFileHandler = new JsonFileHandler();
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            // Crear el fichero JSON si no existe
            jsonFileHandler.crearFicheroJson();

            // Guardar el usuario en el fichero JSON

System.out.println("Invocando guardarUsuario desde el controlador...");


            jsonFileHandler.guardarUsuario(usuario);

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario.");
        }
    }

    @PostMapping("/mostrar")
    public ResponseEntity<Object> mostrarUsuarios() {
        try{
            ArrayList<Usuario> listaUsuarios = jsonFileHandler.mostrarUsuarios();
            return ResponseEntity.ok(listaUsuarios);
        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al leer los usuarios.");
        }
    }
    


}
