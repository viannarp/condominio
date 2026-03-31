package br.com.condominiodoscajueiros.admin.controller;

import br.com.condominiodoscajueiros.admin.domain.Lancamento;
import br.com.condominiodoscajueiros.admin.domain.Morador;
import br.com.condominiodoscajueiros.admin.domain.TipoLancamento;
import br.com.condominiodoscajueiros.admin.service.CondominioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/lancamentos")
public class LancamentoController {

    private final CondominioService service;

    public LancamentoController(CondominioService service) {
        this.service = service;
    }

    @GetMapping
<<<<<<< HEAD
    public String listar(Model model) {
        Lancamento lancamento = new Lancamento();
        lancamento.setCompetencia(LocalDate.now().withDayOfMonth(1));
=======
    public String listar(@RequestParam(value = "editar", required = false) Long editarId, Model model) {
        Lancamento lancamento = editarId != null ? service.buscarLancamento(editarId) : new Lancamento();
        if (lancamento == null) {
            lancamento = new Lancamento();
            lancamento.setCompetencia(LocalDate.now().withDayOfMonth(1));
        }
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)

        model.addAttribute("lancamento", lancamento);
        model.addAttribute("tipos", TipoLancamento.values());
        model.addAttribute("moradores", service.listarMoradores());
<<<<<<< HEAD
        model.addAttribute("lancamentos", service.listarLancamentos());
=======
        model.addAttribute("resumos", service.listarResumoLancamentos());
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)
        return "lancamentos/lista";
    }

    @PostMapping
    public String salvar(@RequestParam("moradorId") Long moradorId,
                         @Valid @ModelAttribute("lancamento") Lancamento lancamento,
                         BindingResult bindingResult,
                         Model model) {
        Morador morador = service.buscarMorador(moradorId);
        if (morador == null) {
            bindingResult.rejectValue("morador", "morador.invalido", "Morador inválido");
        } else {
            lancamento.setMorador(morador);
        }

<<<<<<< HEAD
        if (!bindingResult.hasErrors()) {
            model.addAttribute("tipos", TipoLancamento.values());
            model.addAttribute("moradores", service.listarMoradores());
            model.addAttribute("lancamentos", service.listarLancamentos());
=======
        if (bindingResult.hasErrors()) {
            model.addAttribute("tipos", TipoLancamento.values());
            model.addAttribute("moradores", service.listarMoradores());
            model.addAttribute("resumos", service.listarResumoLancamentos());
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)
            return "lancamentos/lista";
        }
        service.salvarLancamento(lancamento);
        return "redirect:/lancamentos";
    }
<<<<<<< HEAD
=======

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.excluirLancamento(id);
        return "redirect:/lancamentos";
    }
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)
}
