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

        //Configuracion de autorizacion de http
        //Se permite acceso a punto de entrada principal ("/") y a la pantalla antes del login de google ("/api/auth/user")
        //El resto necesitan que estes autentificado
        //Tras la autentificacion se accede de manera forzada a el punto de autentificacion aceptada ("/api/auth/success")
        //Al cerrar sesion se le eliminan las cookies de sesion y se le invalida la sesion, siendo dirigido a la pantalla de login ("/api/auth/user")
        //Se creo el archivo application.yml para la conexion con google y añadir un timeout (forma parte de OAuth2)


        //Necesita que se añada algo similar a un token en los endpoints (utilizar el codigo identificador de google, se llama sub)
        http.authorizeHttpRequests(auth ->
            auth.requestMatchers("/","/api/auth/user").permitAll().anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.successHandler(oAuth2SuccessHandler))
                .logout(logout -> logout.logoutSuccessUrl("/api/auth/user").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll());

            return http.build();

        }

}
