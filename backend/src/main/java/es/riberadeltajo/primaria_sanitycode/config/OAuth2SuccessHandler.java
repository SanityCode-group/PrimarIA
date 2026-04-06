package es.riberadeltajo.primaria_sanitycode.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

//
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.frontend-url}")
    private String frontendUrl;



    //Inicio de sesion personalizable tras loging correcto con google, fuerza la crecion de la cookie de sesion y redirige a success ("/api/auth/success")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        request.getSession(true);
        System.out.println(">>> AUTENTICACIÓN EXITOSA: " + authentication.getName());

        //redirigir a página de muestra de casos una vez iniciado sesión correctamente a través de Google. 
        /*
        Se obtiene la URL base del frontend desde el encabezado "Origin" de la solicitud, y si no está presente, 
        se construye a partir del esquema, servidor y puerto de la solicitud. Luego se redirige a "/frontend/casosclinico.html" 
        dentro del mismo origen para mostrar la página de casos clínicos. Esto asegura que después de un inicio de sesión exitoso, 
        el usuario sea llevado directamente a la interfaz principal del frontend donde podrá interactuar con los casos clínicos.
        */
        //response.sendRedirect("/api/auth/success");
        // String baseUrl = request.getHeader("Origin");
        // if (baseUrl == null) {
        //     baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        // }

        // response.sendRedirect(baseUrl + "/frontend/casosclinico.html");


        response.sendRedirect(frontendUrl + "/casosclinico.html");

    }
}
