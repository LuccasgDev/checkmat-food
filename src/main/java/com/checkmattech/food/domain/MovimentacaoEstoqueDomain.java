package com.checkmattech.food.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes_estoque")
public class MovimentacaoEstoqueDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoDomain produto;

    @Column(name = "quantidade", nullable = false)
    private BigDecimal quantidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentoDomain tipo;

    @Column(nullable = false)
    private LocalDateTime dataMovimento;

    @Column
    private String observacao;

    public MovimentacaoEstoqueDomain(){}

    public MovimentacaoEstoqueDomain(ProdutoDomain produto, BigDecimal quantidade, TipoMovimentoDomain tipo, String observacao) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.observacao = observacao;
        this.dataMovimento = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (this.dataMovimento == null) {
            this.dataMovimento = LocalDateTime.now();
        }
    }
}
