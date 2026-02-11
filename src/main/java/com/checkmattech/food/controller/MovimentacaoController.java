package com.checkmattech.food.controller;

import com.checkmattech.food.domain.MovimentacaoEstoqueDomain;
import com.checkmattech.food.service.MovimentacaoEstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {
    private final MovimentacaoEstoqueService movService;

    public MovimentacaoController(MovimentacaoEstoqueService movService) {
        this.movService = movService;
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<List<MovimentacaoEstoqueDomain>> ListarPorProtudo (@PathVariable Long id){
        return ResponseEntity.ok(movService.listarPorProduto(id));
    }

    @PostMapping("/produto/{produtoID}/entrada")
    public ResponseEntity<MovimentacaoEstoqueDomain> entradaMovimentacao (@PathVariable Long produtoID, @RequestParam BigDecimal quantidade, @RequestParam(required = false) String observacao){
        return ResponseEntity.ok(movService.ajustarEntrada(produtoID, quantidade, observacao));
    }

    @PostMapping("/produto/{produtoID}/saida")
    public ResponseEntity<MovimentacaoEstoqueDomain> saidaMovimentacao (@PathVariable Long produtoID, @RequestParam BigDecimal quantidade, @RequestParam(required = false) String observacao) {
        return ResponseEntity.ok(movService.ajustarEntrada(produtoID, quantidade, observacao));
    }

}
