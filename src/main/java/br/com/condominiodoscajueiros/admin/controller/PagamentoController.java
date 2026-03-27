package br.com.condominiodoscajueiros.admin.controller;

import br.com.condominiodoscajueiros.admin.domain.Lancamento;
import br.com.condominiodoscajueiros.admin.domain.Pagamento;
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

import java.time.LocalDate;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final CondominioService service;

    public PagamentoController(CondominioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        Pagamento pagamento = new Pagamento();
        pagamento.setDataPagamento(LocalDate.now());

        model.addAttribute("pagamento", pagamento);
        model.addAttribute("lancamentos", service.listarLancamentos());
        model.addAttribute("pagamentos", service.listarPagamentos());
        return "pagamentos/lista";
    }

    @PostMapping
    public String salvar(@RequestParam("lancamentoId") Long lancamentoId,
                         @Valid @ModelAttribute("pagamento") Pagamento pagamento,
                         BindingResult bindingResult,
                         Model model) {
        Lancamento lancamento = service.buscarLancamento(lancamentoId);
        if (lancamento == null) {
            bindingResult.rejectValue("lancamento", "lancamento.invalido", "Lançamento inválido");
        } else {
            pagamento.setLancamento(lancamento);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("lancamentos", service.listarLancamentos());
            model.addAttribute("pagamentos", service.listarPagamentos());
            return "pagamentos/lista";
        }
        service.salvarPagamento(pagamento);
        return "redirect:/pagamentos";
    }

    @GetMapping("/{id}/recibo")
    public String recibo(@PathVariable Long id, Model model) {
        Pagamento pagamento = service.buscarPagamento(id);
        if (pagamento == null) {
            return "redirect:/pagamentos";
        }
        model.addAttribute("pagamento", pagamento);
        return "pagamentos/recibo";
    }
}
