package br.com.condominiodoscajueiros.admin.dto;

import br.com.condominiodoscajueiros.admin.domain.TipoDespesa;

import java.math.BigDecimal;

public record RelatorioDespesaTipoDto(TipoDespesa tipo, BigDecimal total) {
}
