package es.riberadeltajo.primaria_sanitycode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


        //Se permite acceso a punto de entrada principal ("/") y a la pantalla del login de google ("/login.html")
        //El resto necesitan que estes autentificado
        //Pero se da permiso para que la pagina de login tenga acceso al css, a los recursos como la imagen, y que
        //Tras la autentificacion se accede de manera forzada a el punto de autentificacion aceptada ("/api/auth/success") y rediriges a casosclinicos
        //Al cerrar sesion se le eliminan las cookies de sesion y se le invalida la sesion, siendo dirigido a la pantalla de login

        http
                .authorizeHttpRequests(auth -> auth.requestMatchers("/","/api/auth/login","/css/login.css","/js/connection.js","/assets/**").permitAll().anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.successHandler(oAuth2SuccessHandler))
                .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/api/auth/login").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll());

            return http.build();

        }

}
