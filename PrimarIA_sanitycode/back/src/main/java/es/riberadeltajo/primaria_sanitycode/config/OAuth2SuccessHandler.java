package es.riberadeltajo.primaria_sanitycode.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //Inicio de sesion personalizable tras loging correcto con google, fuerza la crecion de la cookie de sesion y redirige a success ("/api/auth/success")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        request.getSession(true);

        response.sendRedirect("/api/auth/success");

    }
}
