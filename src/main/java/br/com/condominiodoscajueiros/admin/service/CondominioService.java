package br.com.condominiodoscajueiros.admin.service;

import br.com.condominiodoscajueiros.admin.domain.Lancamento;
import br.com.condominiodoscajueiros.admin.domain.Morador;
import br.com.condominiodoscajueiros.admin.domain.Pagamento;
import br.com.condominiodoscajueiros.admin.repository.LancamentoRepository;
import br.com.condominiodoscajueiros.admin.repository.MoradorRepository;
import br.com.condominiodoscajueiros.admin.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return moradorRepository.findAll();
    }

    public Morador salvarMorador(Morador morador) {
        return moradorRepository.save(morador);
    }

    public Morador buscarMorador(Long id) {
        return moradorRepository.findById(id).orElse(null);
    }

    public List<Lancamento> listarLancamentos() {
        return lancamentoRepository.findAll();
    }

    public Lancamento salvarLancamento(Lancamento lancamento) {
        return lancamentoRepository.save(lancamento);
    }

    public Lancamento buscarLancamento(Long id) {
        return lancamentoRepository.findById(id).orElse(null);
    }

    public List<Pagamento> listarPagamentos() {
        return pagamentoRepository.findAll();
    }

    public Pagamento salvarPagamento(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento buscarPagamento(Long id) {
        return pagamentoRepository.findById(id).orElse(null);
    }
}
