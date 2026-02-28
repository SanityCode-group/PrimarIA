package es.riberadeltajo.primaria_sanitycode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class OAuth2UserService {

    @Autowired
    ObjectMapper objectMapper;

    //Recibiendo el valor de inicio de sesion del usuario, devuelve una cadena de caracteres de el html que se mostrara despues
    public boolean inicio(OAuth2User principal) throws IOException {

        if (principal != null){

            String nombre = principal.getAttribute("name");
            String email = principal.getAttribute("email");

            //Se crea un json para cada usuario que se alla registrado
            json(principal);


           return true;

        }else {

            return false;

        }

    }

    //Crea una carpeta para cada usuario nuevo en data, y dentro de cada carpeta los json de cada vez que se halla conectado
    public void json(OAuth2User user){

        try {

            //Si es neceario crea la carpeta para guardar los registros
            File d = new File(new File("PrimarIA_sanitycode/back/src/main/resources/data") + File.separator + new File(user.getAttribute("sub") + "(" + user.getAttribute("name").toString().replace(" ","")) + ")");

            if (!d.exists()){

                d.mkdirs();

            }

            //Crea un json con todos los datos del usuario por cada registro
            //Cada registro tendra de nombre "{fecha de expiracion del token}.json"
            File f = new File( d, user.getAttribute("exp").toString().replace(":","-") + ".json");

            if(!f.exists()){

                //Imprime por consola algunos de los datos mas importantes del usuario que inicio sesion(completamente innecesario, es para ver que datos se estan recibiendo )
                System.out.println("Datos( Nombre:"  + user.getAttribute("name") + ", Email:"  + user.getAttribute("email") + ", Sub:"  + user.getAttribute("sub") + ", Fecha:"  + user.getAttribute("exp") + ", Hd:"  + user.getAttribute("hd") + " )");

                Map<String,Object> userData = user.getAttributes();

                objectMapper.writeValue(f,userData);

            }

        } catch (Exception e) {

            System.out.println("Error al generar json de " + user.getAttribute("name").toString());
            e.printStackTrace();

        }

    }

    //Se encarga de devolver un script que se ejecuta automaticamente para devolver a la ventana original al hacer login para que cierre la ventana de google
    public String respuesta(OAuth2User principal) throws IOException {

        boolean ok = inicio(principal);

        if (ok) {

           return """
                <script>
                  if (window.opener) {
                    window.opener.postMessage('login-success', window.location.origin);
                  }
                  window.close();
                </script>
            """;

        } else {

            return """                
                <script>
                  alert('Error en el inicio de sesión');
                  window.close();
                </script>
            """;

        }

    }

}
