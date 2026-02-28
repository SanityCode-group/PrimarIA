package es.riberadeltajo.primaria_sanitycode.controller;

import es.riberadeltajo.primaria_sanitycode.service.OAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
public class AuthController {

    //Se importa el servicio de usuarios OAuth2UserService y sus dependencias
    @Autowired
    OAuth2UserService oAuth2UserService;

    //Redirige las conexiones de root ("/") a login google ("/login.html")
    @GetMapping("/")
    public RedirectView inicio(){

        return new RedirectView("/api/auth/login");

    }

    //Devuelve el login.html mediante thymeleaf
    @GetMapping("/api/auth/login")
    public String login(){

        return "login";

    }

    //Tras el login correcto, te lleva aqui, donde se procesa el inicio
    //Manda un javascript mandando una señal a la ventana original de que el login fue correcto y cierra la ventana de google
    @GetMapping("/api/auth/success")
    public void success(@AuthenticationPrincipal OAuth2User principal, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().write(oAuth2UserService.respuesta(principal));

        response.getWriter().flush();

    }

}
