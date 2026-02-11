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

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<MovimentacaoEstoqueDomain>> listarPorProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(movService.listarPorProduto(produtoId));
    }
}
