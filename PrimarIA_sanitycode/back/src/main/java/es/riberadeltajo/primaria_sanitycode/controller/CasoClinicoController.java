package es.riberadeltajo.primaria_sanitycode.controller;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CasoClinicoController {

    //Devuelve el html mediantte thymeleaf rellenando todos los campos con los datos del caso necesario siendo que tener cambiadso a futuro con los datos del caso clinico necesario
    //Los datos del caso clinico se podrian hacer a futuro mediante el CasoClinicoService devolviendo un array de string o una lista con todos los datos y los vamos poniendo en las variables
    @GetMapping("/casosclinico")
    public String caso(Model model){

        String nombre = "Paco";
        String edad = "10";
        String genero = "Masculino";
        String numero = "67";
        String fecha = "6/4/2006";
        String descripcion = "Dolor de cabeza cuando se golpea la cabeza";
        String alergias = "A cosas";
        String enfermedades = "Muchas";
        String cirugias = "Dierna";
        String medicacion = "Hostion 200mg al día";
        String pruebas = "Positivimante vivo";
        String tratamiento = "Un te y listo";

        model.addAttribute("nombre", nombre);
        model.addAttribute("edad", edad);
        model.addAttribute("genero", genero);
        model.addAttribute("numero", numero);
        model.addAttribute("fecha", fecha);
        model.addAttribute("descripcion", descripcion);
        model.addAttribute("alergias", alergias);
        model.addAttribute("enfermedades", enfermedades);
        model.addAttribute("cirugias", cirugias);
        model.addAttribute("medicacion", medicacion);
        model.addAttribute("pruebas", pruebas);
        model.addAttribute("tratamiento", tratamiento);

        return "casosclinico";

    }

}
