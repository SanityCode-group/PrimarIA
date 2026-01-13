package es.riberadeltajo.primaria_sanitycode.controller;

import es.riberadeltajo.primaria_sanitycode.service.OAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthController {

    //Se importa el servicio de usuarios OAuth2UserService y sus dependencias
    @Autowired
    OAuth2UserService oAuth2UserService;

    //Redirige las conexiones de root ("/") a inicio ("/api/auth/user")
    @GetMapping("/")
    public RedirectView inicio(){

        return new RedirectView("/api/auth/user");

    }

    //Inicio antes de registrarse en google, pantalla de inicio
    @GetMapping("/api/auth/user")
    @ResponseBody //Esta anotacion es hasta que se tenga el frontend
    public String user(){

        //Cuando el frontend pasen los modelos y se elija uno se adaptara este contenido para pasar el documento y los datos requeridos
        return "<!DOCTYPE html> <html> <head> <title>Inicio</title> </head> <body> <a href='/api/auth/success'>Pulsar para ir a iniciar sesion en Google</a> </body> </html>";

    }

    //Tras el login correcto, te lleva aqui, donde genera un html basico con el nombre y correo del que inicio sesion, ademas de poder hacer logout
    @GetMapping("/api/auth/success")
    @ResponseBody//Esta anotacion es hasta que se tenga el frontend
    public String success(@AuthenticationPrincipal OAuth2User principal){

        //Cuando el frontend pasen los modelos y se elija uno se adaptara este contenido para pasar el documento y los datos requeridos
        return oAuth2UserService.inicio(principal);

    }

}
