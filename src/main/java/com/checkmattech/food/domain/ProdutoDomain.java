package com.checkmattech.food.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class ProdutoDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadeMedida unidadeMedida;

    private BigDecimal estoqueAtual = BigDecimal.ZERO;

    private BigDecimal estoqueMinimo = BigDecimal.ZERO;

    public ProdutoDomain() {}

    public ProdutoDomain(String nome, UnidadeMedida unidadeMedida, BigDecimal estoqueAtual, BigDecimal estoqueMinimo){
        this.nome = nome;
        this.unidadeMedida = unidadeMedida;
        this.estoqueAtual = estoqueAtual != null ? estoqueAtual : BigDecimal.ZERO;
        this.estoqueMinimo = estoqueMinimo != null  ? estoqueMinimo : BigDecimal.ZERO;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public UnidadeMedida getUnidadeMedida(){
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida){
        this.unidadeMedida = unidadeMedida;
    }

    public BigDecimal getEstoqueAtual(){
        return estoqueAtual;
    }

    public void setEstoqueAtual(BigDecimal estoqueAtual){
        this.estoqueAtual = estoqueAtual;
    }

    public  BigDecimal getEstoqueMinimo(){
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(BigDecimal estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }


    public void adicionarEstoque(BigDecimal quantidade){
        if(quantidade == null) return;
        this.estoqueAtual = this.estoqueAtual.add(quantidade);
    }

    public void removerEstoque(BigDecimal quantidade){
        if(quantidade == null) return;
        this.estoqueAtual = this.estoqueAtual.subtract(quantidade);
    }

    public enum UnidadeMedida {
        PACOTE,
        CAIXA,
        SACO
    }
}
