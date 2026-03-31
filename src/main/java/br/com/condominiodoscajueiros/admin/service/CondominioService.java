package br.com.condominiodoscajueiros.admin.service;

import br.com.condominiodoscajueiros.admin.domain.Lancamento;
import br.com.condominiodoscajueiros.admin.domain.Morador;
import br.com.condominiodoscajueiros.admin.domain.Pagamento;
import br.com.condominiodoscajueiros.admin.dto.LancamentoResumoDto;
import br.com.condominiodoscajueiros.admin.dto.RelatorioUnidadeDto;
import br.com.condominiodoscajueiros.admin.repository.LancamentoRepository;
import br.com.condominiodoscajueiros.admin.repository.MoradorRepository;
import br.com.condominiodoscajueiros.admin.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CondominioService {

    private final MoradorRepository moradorRepository;
    private final LancamentoRepository lancamentoRepository;
    private final PagamentoRepository pagamentoRepository;

    public CondominioService(MoradorRepository moradorRepository,
                             LancamentoRepository lancamentoRepository,
                             PagamentoRepository pagamentoRepository) {
        this.moradorRepository = moradorRepository;
        this.lancamentoRepository = lancamentoRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    public List<Morador> listarMoradores() {
        return moradorRepository.findAll().stream()
                .sorted(Comparator.comparing(Morador::getUnidade).thenComparing(Morador::getNome))
                .toList();
    }

    public Morador salvarMorador(Morador morador) {
        return moradorRepository.save(morador);
    }

    public Morador buscarMorador(Long id) {
        return moradorRepository.findById(id).orElse(null);
    }

    @Transactional
    public void excluirMorador(Long id) {
        moradorRepository.deleteById(id);
    }

    public List<Lancamento> listarLancamentos() {
        return lancamentoRepository.findAll().stream()
                .sorted(Comparator.comparing(Lancamento::getCompetencia).reversed())
                .toList();
    }

    public Lancamento salvarLancamento(Lancamento lancamento) {
        return lancamentoRepository.save(lancamento);
    }

    public Lancamento buscarLancamento(Long id) {
        return lancamentoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void excluirLancamento(Long id) {
        lancamentoRepository.deleteById(id);
    }

    public List<Pagamento> listarPagamentos() {
        return pagamentoRepository.findAll().stream()
                .sorted(Comparator.comparing(Pagamento::getDataPagamento).reversed())
                .toList();
    }

    public Pagamento salvarPagamento(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento buscarPagamento(Long id) {
        return pagamentoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void excluirPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }

    public BigDecimal calcularTotalPago(Long lancamentoId) {
        return pagamentoRepository.somarPagamentosPorLancamento(lancamentoId);
    }

    public BigDecimal calcularSaldoAberto(Lancamento lancamento) {
        return lancamento.getValor().subtract(calcularTotalPago(lancamento.getId()));
    }

    public List<LancamentoResumoDto> listarResumoLancamentos() {
        return listarLancamentos().stream()
                .map(l -> {
                    BigDecimal totalPago = calcularTotalPago(l.getId());
                    BigDecimal saldo = l.getValor().subtract(totalPago);
                    return new LancamentoResumoDto(l, totalPago, saldo);
                })
                .toList();
    }

    public List<Pagamento> listarPagamentosPorLancamento(Long lancamentoId) {
        return pagamentoRepository.findByLancamentoIdOrderByDataPagamentoAsc(lancamentoId);
    }

    public List<RelatorioUnidadeDto> relatorioMensalPorUnidade(YearMonth mes) {
        LocalDate inicio = mes.atDay(1);
        LocalDate fim = mes.atEndOfMonth();
        List<Lancamento> lancamentos = lancamentoRepository.findByCompetenciaBetween(inicio, fim);

        Map<String, List<Lancamento>> porUnidade = lancamentos.stream()
                .collect(Collectors.groupingBy(l -> l.getMorador().getUnidade()));

        List<RelatorioUnidadeDto> relatorio = new ArrayList<>();
        porUnidade.forEach((unidade, itens) -> {
            BigDecimal totalLancado = itens.stream()
                    .map(Lancamento::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalPago = itens.stream()
                    .map(l -> calcularTotalPago(l.getId()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal saldo = totalLancado.subtract(totalPago);
            String morador = itens.get(0).getMorador().getNome();
            relatorio.add(new RelatorioUnidadeDto(unidade, morador, totalLancado, totalPago, saldo));
        });

        return relatorio.stream()
                .sorted(Comparator.comparing(RelatorioUnidadeDto::unidade))
                .toList();
    }
}
