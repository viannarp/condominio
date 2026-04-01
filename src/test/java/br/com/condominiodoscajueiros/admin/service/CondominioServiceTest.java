package br.com.condominiodoscajueiros.admin.service;

import br.com.condominiodoscajueiros.admin.dto.RelatorioMensalResumoDto;
import br.com.condominiodoscajueiros.admin.dto.RelatorioUnidadeDto;
import br.com.condominiodoscajueiros.admin.repository.DespesaRepository;
import br.com.condominiodoscajueiros.admin.repository.LancamentoRepository;
import br.com.condominiodoscajueiros.admin.repository.MoradorRepository;
import br.com.condominiodoscajueiros.admin.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class CondominioServiceTest {

    private final MoradorRepository moradorRepository = mock(MoradorRepository.class);
    private final LancamentoRepository lancamentoRepository = mock(LancamentoRepository.class);
    private final PagamentoRepository pagamentoRepository = mock(PagamentoRepository.class);
    private final DespesaRepository despesaRepository = mock(DespesaRepository.class);

    private final CondominioService service = new CondominioService(
            moradorRepository,
            lancamentoRepository,
            pagamentoRepository,
            despesaRepository
    );

    @Test
    void deveSomarTotaisDoResumoMensal() {
        List<RelatorioUnidadeDto> itens = List.of(
                new RelatorioUnidadeDto("101", "Ana", new BigDecimal("300.00"), new BigDecimal("250.00"), new BigDecimal("50.00")),
                new RelatorioUnidadeDto("102", "Bruno", new BigDecimal("400.00"), new BigDecimal("300.00"), new BigDecimal("100.00"))
        );

        RelatorioMensalResumoDto resumo = service.resumirRelatorioMensal(itens);

        assertEquals(new BigDecimal("700.00"), resumo.totalLancado());
        assertEquals(new BigDecimal("550.00"), resumo.totalPago());
        assertEquals(new BigDecimal("150.00"), resumo.saldoAberto());
    }
}
