package es.riberadeltajo.primaria_sanitycode.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Autowired
    OAuth2SuccessHandler oAuth2SuccessHandler;

    @Value("${app.security.dev-mode:false}")
    private boolean devMode;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        System.out.println(">>> MODO DEV ACTIVADO? " + devMode);

        // >>>> MODO DESARROLLO: todo permitido <<<<
        /* En modo desarrollo, se permite el acceso a todas las rutas sin autenticación, 
         * y se habilita CORS para permitir peticiones desde el frontend en localhost. 
         * Además, se deshabilita CSRF para facilitar las pruebas con herramientas como 
         * Postman o el frontend sin necesidad de gestionar tokens CSRF.
         *  
         */
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        );

        if (devMode) {
            System.out.println(">>> ENTRANDO EN MODO DESARROLLO (permitAll)");
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

        System.out.println(">>> ENTRANDO EN MODO PRODUCCIÓN (OAuth2 obligatorio)");

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/api/auth/user", "/login.html", "/css/**", "/js/**", "/oauth2/**", "/login/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .successHandler(oAuth2SuccessHandler)
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Unauthorized\"}");
                })
            );
                // .oauth2Login(oauth2 -> oauth2.successHandler(oAuth2SuccessHandler))
                // .logout(logout -> logout
                //     .logoutSuccessUrl("/api/auth/user")
                //     .invalidateHttpSession(true)
                //     .deleteCookies("JSESSIONID")
                //     .permitAll()
                // )
                // //corregir error redirección de CORS
                // .exceptionHandling(e -> e
                //     .authenticationEntryPoint((request, response, authException) -> {
                //         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                //         response.getWriter().write("{\"error\": \"No autenticado. Por favor, inicia sesión con Google.\"}");
                //     })
                // );

        return http.build();

    }

    /* Configuracion de CORS para permitir peticiones desde el frontend en desarrollo (localhost:5500)
     * Se permiten los métodos GET, POST, PUT y DELETE, y se permiten todas las cabeceras. Esta configuración se aplica a todas las rutas (/**).
     * En producción, se debería restringir esta configuración para permitir solo las peticiones desde el dominio del frontend desplegado.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
            "http://localhost:5500", 
            "http://127.0.0.1:5500", 
            "https://sanitycode.riberadeltajo.es"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
