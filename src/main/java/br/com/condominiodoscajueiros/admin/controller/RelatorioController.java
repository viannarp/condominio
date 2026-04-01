package br.com.condominiodoscajueiros.admin.controller;

import br.com.condominiodoscajueiros.admin.dto.RelatorioMensalResumoDto;
import br.com.condominiodoscajueiros.admin.dto.RelatorioUnidadeDto;
import br.com.condominiodoscajueiros.admin.service.CondominioService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.time.YearMonth;
import java.util.List;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    private final CondominioService service;

    public RelatorioController(CondominioService service) {
        this.service = service;
    }

    @GetMapping("/mensal")
    public String mensal(@RequestParam(value = "mes", required = false) String mes, Model model) {
        YearMonth referencia = resolverReferencia(mes);
        List<RelatorioUnidadeDto> itens = service.relatorioMensalPorUnidade(referencia);

        model.addAttribute("mesSelecionado", referencia.toString());
        model.addAttribute("itens", itens);
        model.addAttribute("resumo", service.resumirRelatorioMensal(itens));
        return "relatorios/mensal";
    }

    @GetMapping("/mensal/pdf")
    public ResponseEntity<byte[]> mensalPdf(@RequestParam(value = "mes", required = false) String mes) throws DocumentException {
        YearMonth referencia = resolverReferencia(mes);
        List<RelatorioUnidadeDto> itens = service.relatorioMensalPorUnidade(referencia);
        RelatorioMensalResumoDto resumo = service.resumirRelatorioMensal(itens);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph("Relatório mensal por unidade - Condomínio dos Cajueiros"));
        document.add(new Paragraph("Referência: " + referencia));
        document.add(new Paragraph(" "));

        if (itens.isEmpty()) {
            document.add(new Paragraph("Nenhum lançamento encontrado para o período."));
        } else {
            for (RelatorioUnidadeDto item : itens) {
                document.add(new Paragraph("Unidade " + item.unidade()
                        + " | Morador: " + item.morador()
                        + " | Lançado: R$ " + item.totalLancado()
                        + " | Pago: R$ " + item.totalPago()
                        + " | Saldo: R$ " + item.saldoAberto()));
            }
        }

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Totais do mês"));
        document.add(new Paragraph("Total lançado: R$ " + resumo.totalLancado()));
        document.add(new Paragraph("Total pago: R$ " + resumo.totalPago()));
        document.add(new Paragraph("Saldo em aberto: R$ " + resumo.saldoAberto()));
        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline()
                .filename("relatorio-mensal-" + referencia + ".pdf")
                .build());
        return ResponseEntity.ok().headers(headers).body(output.toByteArray());
    }

    private YearMonth resolverReferencia(String mes) {
        return (mes == null || mes.isBlank()) ? YearMonth.now() : YearMonth.parse(mes);
    }
}
