package br.com.condominiodoscajueiros.admin.dto;

import java.math.BigDecimal;

public record RelatorioMensalResumoDto(BigDecimal totalLancado,
                                       BigDecimal totalPago,
                                       BigDecimal saldoAberto) {
}
