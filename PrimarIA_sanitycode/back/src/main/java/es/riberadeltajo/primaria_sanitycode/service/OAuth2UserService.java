package es.riberadeltajo.primaria_sanitycode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;

@Service
public class OAuth2UserService {

    @Autowired
    ObjectMapper objectMapper;

    //Recibiendo el valor de inicio de sesion del usuario, devuelve una cadena de caracteres de el html que se mostrara despues
    public String inicio(OAuth2User principal){

        if (principal != null){

            //Imprime por consola algunos de los datos mas importantes del usuario que inicio sesion
            System.out.println("Datos( Nombre:"  + principal.getAttribute("name") + ", Email:"  + principal.getAttribute("email") + ", Sub:"  + principal.getAttribute("sub") + ", Fecha:"  + principal.getAttribute("exp") + ", Hd:"  + principal.getAttribute("hd") + " )");

            String nombre = principal.getAttribute("name");
            String email = principal.getAttribute("email");

            //Se crea un json para cada usuario que se alla registrado
            json(principal);

            //Los returns estos con la estructura html se pueden quitar encuanto se tenga el modelo de frontend
            return "<!DOCTYPE html> <html> <head><title>Bienvenido</title></head> <body> <h1>" + nombre + "!</h1> <p>Email: " + email + "</p> <a href='/logout'>Cerrar sesion</a> </body> </html>";

        }

        return "<!DOCTYPE html> <html> <head><title>Bienvenido</title></head> <body> <h1> Error en inicio de sesion </h1> <a href='/logout'>Cerrar sesion</a> </body> </html>";

    }

    //Crea una carpeta para cada usuario nuevo en data, y dentro de cada carpeta los json de cada vez que se halla conectado
    public void json(OAuth2User user){

        try {

            File d = new File(new File("data") + File.separator + new File(user.getAttribute("name").toString().replace(" ","")));

            if (!d.exists()){

                d.mkdirs();

            }

            File f = new File( d, user.getAttribute("sub").toString() + "(" + user.getAttribute("exp").toString().replace(":","-") + ")" + ".json");

            Map<String,Object> userData = user.getAttributes();

            objectMapper.writeValue(f,userData);

        } catch (Exception e) {

            System.out.println("Error al generar json de " + user.getAttribute("name").toString());
            e.printStackTrace();

        }

    }

}
