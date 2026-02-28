package es.riberadeltajo.primaria_sanitycode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    //Devuelve mediante thymeleaf el documento html con el token crsf propio de cada usuario en un fromulario invisible para poder hacer logout de maner correcta
    @GetMapping("/chat")
    public String chat(Model model){

        return "chat";

    }

}
