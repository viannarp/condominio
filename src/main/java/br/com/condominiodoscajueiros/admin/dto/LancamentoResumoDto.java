package br.com.condominiodoscajueiros.admin.dto;

import br.com.condominiodoscajueiros.admin.domain.Lancamento;

import java.math.BigDecimal;

public record LancamentoResumoDto(Lancamento lancamento, BigDecimal totalPago, BigDecimal saldoAberto) {
}
