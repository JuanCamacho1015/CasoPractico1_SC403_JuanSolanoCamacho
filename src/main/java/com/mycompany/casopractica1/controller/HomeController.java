package com.mycompany.casopractica1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Controlador Home, solo maneja regresar a la páginas principal

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/sala/listado";
    }
}
