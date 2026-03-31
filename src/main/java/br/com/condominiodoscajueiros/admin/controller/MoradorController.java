package br.com.condominiodoscajueiros.admin.controller;

import br.com.condominiodoscajueiros.admin.domain.Morador;
import br.com.condominiodoscajueiros.admin.service.CondominioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/moradores")
public class MoradorController {

    private final CondominioService service;

    public MoradorController(CondominioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(@RequestParam(value = "editar", required = false) Long editarId, Model model) {
        Morador morador = editarId != null ? service.buscarMorador(editarId) : new Morador();
        if (morador == null) {
            morador = new Morador();
        }
        model.addAttribute("morador", morador);
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

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.excluirMorador(id);
        return "redirect:/moradores";
    }
}
