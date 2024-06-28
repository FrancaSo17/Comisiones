package com.example.demo.controladores;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccesoDenegadoControlador {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accesoDenegado";  // Nombre de la plantilla de Thymeleaf o JSP
    }
}
