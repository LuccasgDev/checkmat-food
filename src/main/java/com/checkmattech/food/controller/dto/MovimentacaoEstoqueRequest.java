package com.checkmattech.food.controller.dto;

import java.math.BigDecimal;

public class MovimentacaoEstoqueRequest {

    private BigDecimal quantidade;
    private String observacao;

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}

