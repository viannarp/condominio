package br.com.condominiodoscajueiros.admin.dto;

import java.math.BigDecimal;

public record RelatorioUnidadeDto(String unidade,
                                  String morador,
                                  BigDecimal totalLancado,
                                  BigDecimal totalPago,
                                  BigDecimal saldoAberto) {
}
