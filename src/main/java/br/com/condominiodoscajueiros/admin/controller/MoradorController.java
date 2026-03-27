package br.com.condominiodoscajueiros.admin.controller;

import br.com.condominiodoscajueiros.admin.domain.Morador;
import br.com.condominiodoscajueiros.admin.service.CondominioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/moradores")
public class MoradorController {

    private final CondominioService service;

    public MoradorController(CondominioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("morador", new Morador());
        model.addAttribute("moradores", service.listarMoradores());
        return "moradores/lista";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("morador") Morador morador,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("moradores", service.listarMoradores());
            return "moradores/lista";
        }
        service.salvarMorador(morador);
        return "redirect:/moradores";
    }
}
