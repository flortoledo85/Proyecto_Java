package com.talentotech.optica_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String saludar() {
        return "Hola desde Óptica Backend";
    }    
}
