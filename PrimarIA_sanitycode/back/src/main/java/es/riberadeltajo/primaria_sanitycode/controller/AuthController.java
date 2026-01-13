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
        return "<!DOCTYPE html> <html> <head> <title>Inicio</title> </head> <body> <button onclick='loginGoogle()'>Login con Google</button> <script> function loginGoogle() {const width = 500;  const height = 600; const left = (window.screen.width / 2) - (width / 2); const top = (window.screen.height / 2) - (height / 2); window.open( '/oauth2/authorization/google', 'GoogleLogin', `width=${width},height=${height},top=${top},left=${left}` );} </script> <script> window.addEventListener('message', function(event) { if (event.data === 'login-success') { window.location.replace('/api/auth/success') }}); </script> </body> </html>";

    }

    //Tras el login correcto, te lleva aqui, donde genera un html basico con el nombre y correo del que inicio sesion, ademas de poder hacer logout
    @GetMapping("/api/auth/success")
    @ResponseBody//Esta anotacion es hasta que se tenga el frontend
    public String success(@AuthenticationPrincipal OAuth2User principal){

        //Cuando el frontend pasen los modelos y se elija uno se adaptara este contenido para pasar el documento y los datos requeridos
        return oAuth2UserService.inicio(principal);

    }

}
