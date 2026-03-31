package br.com.condominiodoscajueiros.admin.controller;

import br.com.condominiodoscajueiros.admin.service.CondominioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    private final CondominioService service;

    public RelatorioController(CondominioService service) {
        this.service = service;
    }

    @GetMapping("/mensal")
    public String mensal(@RequestParam(value = "mes", required = false) String mes, Model model) {
        YearMonth referencia = (mes == null || mes.isBlank()) ? YearMonth.now() : YearMonth.parse(mes);
        model.addAttribute("mesSelecionado", referencia.toString());
        model.addAttribute("itens", service.relatorioMensalPorUnidade(referencia));
        return "relatorios/mensal";
    }
}
