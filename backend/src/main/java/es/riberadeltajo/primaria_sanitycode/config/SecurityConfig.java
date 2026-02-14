package es.riberadeltajo.primaria_sanitycode.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Autowired
    OAuth2SuccessHandler oAuth2SuccessHandler;

    @Value("${app.security.dev-mode:false}")
    private boolean devMode;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // >>>> MODO DESARROLLO: todo permitido <<<<
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable());
        if (devMode) {
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }

        // Configuracion de autorizacion de http
        // Se permite acceso a punto de entrada principal ("/") y a la pantalla antes
        // del login de google ("/api/auth/user")
        // El resto necesitan que estes autentificado
        // Tras la autentificacion se accede de manera forzada a el punto de
        // autentificacion aceptada ("/api/auth/success")
        // Al cerrar sesion se le eliminan las cookies de sesion y se le invalida la
        // sesion, siendo dirigido a la pantalla de login ("/api/auth/user")
        // Se creo el archivo application.yml para la conexion con google y añadir un
        // timeout (forma parte de OAuth2)

        // >>>> MODO PRODUCCIÓN: OAuth2 <<<<
        // Necesita que se añada algo similar a un token en los endpoints (utilizar el
        // codigo identificador de google, se llama sub)
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers("/", "/api/auth/user").permitAll().anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.successHandler(oAuth2SuccessHandler))
                .logout(logout -> logout.logoutSuccessUrl("/api/auth/user").invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID").permitAll());

        return http.build();

    }

    /* Configuracion de CORS para permitir peticiones desde el frontend en desarrollo (localhost:5500)
     * Se permiten los métodos GET, POST, PUT y DELETE, y se permiten todas las cabeceras. Esta configuración se aplica a todas las rutas (/**).
     * En producción, se debería restringir esta configuración para permitir solo las peticiones desde el dominio del frontend desplegado.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5500", "http://127.0.0.1:5500"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
