package es.riberadeltajo.primaria_sanitycode.config;

import es.riberadeltajo.primaria_sanitycode.model.entity.Usuario;
import es.riberadeltajo.primaria_sanitycode.repository.UsuarioRepository;
import es.riberadeltajo.primaria_sanitycode.repository.WhitelistRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

//
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private WhitelistRepository whitelistRepository;

    @Value("${app.frontend-url}")
    private String frontendUrl;



    //Inicio de sesion personalizable tras loging correcto con google, fuerza la crecion de la cookie de sesion y redirige a success ("/api/auth/success")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String nombre = oAuth2User.getAttribute("name");

        System.out.println(">>> INTENTO DE LOGIN: " + email);

        // 1. Comprobar whitelist
        if (whitelistRepository.findByEmail(email).isEmpty()) {
            System.out.println(">>> ACCESO DENEGADO: " + email + " no está en la whitelist.");
            request.getSession().invalidate();
            response.sendRedirect(frontendUrl + "/index.html?error=whitelist");
            return;
        }

        // 2. Gestionar usuario
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        Usuario usuario;

        if (usuarioOpt.isPresent()) {
            usuario = usuarioOpt.get();
            usuario.setUltimoAcceso(LocalDateTime.now());
            System.out.println(">>> USUARIO EXISTENTE: Actualizando último acceso para " + email);
        } else {
            usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setNombre(nombre != null ? nombre : email);
            usuario.setRol("USER"); // Rol por defecto
            usuario.setFechaCreacion(LocalDateTime.now());
            usuario.setUltimoAcceso(LocalDateTime.now());
            System.out.println(">>> NUEVO USUARIO: Registrando " + email);
        }

        usuarioRepository.save(usuario);

        request.getSession(true);
        response.sendRedirect(frontendUrl + "/casosclinico.html");
    }
}
