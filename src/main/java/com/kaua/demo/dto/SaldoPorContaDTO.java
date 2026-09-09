package com.kaua.demo.dto;

import java.math.BigDecimal;

public class SaldoPorContaDTO {

    private Long contaId;
    private String conta;
    private BigDecimal saldoCalculado;

    public SaldoPorContaDTO(Long contaId, String conta, BigDecimal saldoCalculado) {
        this.contaId = contaId;
        this.conta = conta;
        this.saldoCalculado = saldoCalculado;
    }

    public Long getContaId() {
        return contaId;
    }

    public String getConta() {
        return conta;
    }

    public BigDecimal getSaldoCalculado() {
        return saldoCalculado;
    }
}