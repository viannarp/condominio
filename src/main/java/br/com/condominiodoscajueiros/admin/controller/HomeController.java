package br.com.condominiodoscajueiros.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
<<<<<<< HEAD
=======

    @GetMapping("/login")
    public String login() {
        return "login";
    }
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)
}
