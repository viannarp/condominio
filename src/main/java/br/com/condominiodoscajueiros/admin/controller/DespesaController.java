package br.com.condominiodoscajueiros.admin.controller;

import br.com.condominiodoscajueiros.admin.domain.Despesa;
import br.com.condominiodoscajueiros.admin.domain.TipoDespesa;
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
import java.time.YearMonth;

@Controller
@RequestMapping("/despesas")
public class DespesaController {

    private final CondominioService service;

    public DespesaController(CondominioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(@RequestParam(value = "editar", required = false) Long editarId, Model model) {
        Despesa despesa = editarId != null ? service.buscarDespesa(editarId) : new Despesa();
        if (despesa.getReferencia() == null) {
            despesa.setReferencia(LocalDate.now());
            despesa.setTipo(TipoDespesa.FIXA);
        }

        model.addAttribute("despesa", despesa);
        model.addAttribute("tipos", TipoDespesa.values());
        model.addAttribute("despesas", service.listarDespesas());
        return "despesas/lista";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("despesa") Despesa despesa,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tipos", TipoDespesa.values());
            model.addAttribute("despesas", service.listarDespesas());
            return "despesas/lista";
        }

        service.salvarDespesa(despesa);
        return "redirect:/despesas";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.excluirDespesa(id);
        return "redirect:/despesas";
    }

    @GetMapping("/relatorios/mensal")
    public String relatorioMensal(@RequestParam(value = "mes", required = false) String mes, Model model) {
        YearMonth referencia = (mes == null || mes.isBlank()) ? YearMonth.now() : YearMonth.parse(mes);

        model.addAttribute("mesSelecionado", referencia.toString());
        model.addAttribute("itens", service.listarDespesasPorMes(referencia));
        model.addAttribute("totaisPorTipo", service.relatorioMensalDespesaPorTipo(referencia));
        model.addAttribute("totalMensal", service.totalDespesasMensais(referencia));
        return "despesas/relatorio-mensal";
    }
}
