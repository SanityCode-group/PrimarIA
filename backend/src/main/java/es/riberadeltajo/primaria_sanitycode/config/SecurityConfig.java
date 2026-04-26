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

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        System.out.println(">>> MODO DEV ACTIVADO? " + devMode);

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

        System.out.println(">>> ENTRANDO EN MODO PRODUCCIÓN (OAuth2 obligatorio)");

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/api/auth/user", "/index.html", "/css/**", "/js/**", "/oauth2/**", "/login/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .successHandler(oAuth2SuccessHandler)
                .failureUrl(frontendUrl + "/index.html?error=google")
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint((request, response, authException) -> {
                    if (request.getRequestURI().startsWith("/api")) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\": \"Unauthorized\"}");
                    } else {
                        response.sendRedirect(frontendUrl + "/index.html");
                    }
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
