package com.checkmattech.food.controller;

import com.checkmattech.food.domain.MovimentacaoEstoqueDomain;
import com.checkmattech.food.domain.ProdutoDomain;
import com.checkmattech.food.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/protudos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoDomain> criarProduto(@RequestBody ProdutoDomain produtoDomain) {
        ProdutoDomain produto = produtoService.criarProduto(produtoDomain);
        return  ResponseEntity.ok(produto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDomain> buscarProdutoPorId(@PathVariable Long id) {
        return  ResponseEntity.ok(produtoService.buscarID(id));
    }

    @PostMapping("/{id}/entrada")
    public ResponseEntity<MovimentacaoEstoqueDomain> entradaM (@PathVariable Long id, @RequestParam BigDecimal quantidade, @RequestParam(required = false) String observacao) {
        MovimentacaoEstoqueDomain mov = produtoService.entradaEstoque(id, quantidade, observacao);
        return  ResponseEntity.ok(mov);
    }

    @PostMapping("/{id}/saida")
    public ResponseEntity<MovimentacaoEstoqueDomain> saidaM (@PathVariable Long id, @RequestParam BigDecimal quantidade, @RequestParam(required = false) String observacao){
        MovimentacaoEstoqueDomain mov = produtoService.saidaEstoque(id, quantidade, observacao);
        return  ResponseEntity.ok(mov);
    }
}
