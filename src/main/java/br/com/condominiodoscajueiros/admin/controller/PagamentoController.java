package br.com.condominiodoscajueiros.admin.controller;

import br.com.condominiodoscajueiros.admin.domain.Lancamento;
import br.com.condominiodoscajueiros.admin.domain.Pagamento;
import br.com.condominiodoscajueiros.admin.service.CondominioService;
<<<<<<< HEAD
import jakarta.validation.Valid;
=======
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.validation.Valid;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

<<<<<<< HEAD
=======
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)
import java.time.LocalDate;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final CondominioService service;

    public PagamentoController(CondominioService service) {
        this.service = service;
    }

    @GetMapping
<<<<<<< HEAD
    public String listar(Model model) {
        Pagamento pagamento = new Pagamento();
        pagamento.setDataPagamento(LocalDate.now());
=======
    public String listar(@RequestParam(value = "editar", required = false) Long editarId, Model model) {
        Pagamento pagamento = editarId != null ? service.buscarPagamento(editarId) : new Pagamento();
        if (pagamento == null) {
            pagamento = new Pagamento();
            pagamento.setDataPagamento(LocalDate.now());
        }
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)

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
<<<<<<< HEAD
        }

        if (!bindingResult.hasErrors()) {
=======
            validarPagamentoParcial(pagamento, bindingResult);
        }

        if (bindingResult.hasErrors()) {
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)
            model.addAttribute("lancamentos", service.listarLancamentos());
            model.addAttribute("pagamentos", service.listarPagamentos());
            return "pagamentos/lista";
        }
        service.salvarPagamento(pagamento);
        return "redirect:/pagamentos";
    }

<<<<<<< HEAD
=======
    private void validarPagamentoParcial(Pagamento pagamento, BindingResult bindingResult) {
        BigDecimal totalPagoAtual = service.calcularTotalPago(pagamento.getLancamento().getId());
        if (pagamento.getId() != null) {
            Pagamento existente = service.buscarPagamento(pagamento.getId());
            if (existente != null) {
                totalPagoAtual = totalPagoAtual.subtract(existente.getValorPago());
            }
        }
        BigDecimal novoTotal = totalPagoAtual.add(pagamento.getValorPago());
        if (novoTotal.compareTo(pagamento.getLancamento().getValor()) > 0) {
            BigDecimal saldo = pagamento.getLancamento().getValor().subtract(totalPagoAtual);
            bindingResult.rejectValue("valorPago", "pagamento.acima", "Valor ultrapassa saldo em aberto de R$ " + saldo);
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.excluirPagamento(id);
        return "redirect:/pagamentos";
    }

>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)
    @GetMapping("/{id}/recibo")
    public String recibo(@PathVariable Long id, Model model) {
        Pagamento pagamento = service.buscarPagamento(id);
        if (pagamento == null) {
            return "redirect:/pagamentos";
        }
        model.addAttribute("pagamento", pagamento);
<<<<<<< HEAD
        return "pagamentos/recibo";
    }
=======
        model.addAttribute("historico", service.listarPagamentosPorLancamento(pagamento.getLancamento().getId()));
        model.addAttribute("saldoAberto", service.calcularSaldoAberto(pagamento.getLancamento()));
        return "pagamentos/recibo";
    }

    @GetMapping("/{id}/recibo/pdf")
    public ResponseEntity<byte[]> reciboPdf(@PathVariable Long id) throws DocumentException {
        Pagamento pagamento = service.buscarPagamento(id);
        if (pagamento == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph("Recibo de pagamento - Condomínio dos Cajueiros"));
        document.add(new Paragraph("Morador: " + pagamento.getLancamento().getMorador().getNome()));
        document.add(new Paragraph("Unidade: " + pagamento.getLancamento().getMorador().getUnidade()));
        document.add(new Paragraph("Tipo: " + pagamento.getLancamento().getTipo()));
        document.add(new Paragraph("Competência: " + pagamento.getLancamento().getCompetencia()));
        document.add(new Paragraph("Data de pagamento: " + pagamento.getDataPagamento()));
        document.add(new Paragraph("Forma: " + pagamento.getFormaPagamento()));
        document.add(new Paragraph("Valor recebido: R$ " + pagamento.getValorPago()));
        document.add(new Paragraph("-----------------------------------------------"));
        document.add(new Paragraph("Condomínio dos Cajueiros"));
        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("recibo-" + id + ".pdf").build());
        return ResponseEntity.ok().headers(headers).body(output.toByteArray());
    }
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)
}
