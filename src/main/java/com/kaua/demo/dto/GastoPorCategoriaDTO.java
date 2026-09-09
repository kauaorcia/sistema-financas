package com.kaua.demo.dto;

import java.math.BigDecimal;

public class GastoPorCategoriaDTO {

    private String categoria;
    private BigDecimal total;

    public GastoPorCategoriaDTO(String categoria, BigDecimal total) {
        this.categoria = categoria;
        this.total = total;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getTotal() {
        return total;
    }
}