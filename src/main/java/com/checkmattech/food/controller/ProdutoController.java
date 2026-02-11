package com.checkmattech.food.controller;

import com.checkmattech.food.controller.dto.MovimentacaoEstoqueRequest;
import com.checkmattech.food.domain.MovimentacaoEstoqueDomain;
import com.checkmattech.food.domain.ProdutoDomain;
import com.checkmattech.food.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDomain>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ProdutoDomain> criarProduto(@RequestBody ProdutoDomain produtoDomain) {
        ProdutoDomain produto = produtoService.criarProduto(produtoDomain);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produto.getId())
                .toUri();
        return ResponseEntity.created(location).body(produto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDomain> buscarProdutoPorId(@PathVariable Long id) {
        return  ResponseEntity.ok(produtoService.buscarID(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDomain> atualizarProduto(@PathVariable Long id,
                                                          @RequestBody ProdutoDomain produtoDomain) {
        ProdutoDomain atualizado = produtoService.atualizar(id, produtoDomain);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/entrada")
    public ResponseEntity<MovimentacaoEstoqueDomain> entradaM (
            @PathVariable Long id,
            @RequestBody MovimentacaoEstoqueRequest request) {
        MovimentacaoEstoqueDomain mov = produtoService.entradaEstoque(id, request.getQuantidade(), request.getObservacao());
        return  ResponseEntity.ok(mov);
    }

    @PostMapping("/{id}/saida")
    public ResponseEntity<MovimentacaoEstoqueDomain> saidaM (
            @PathVariable Long id,
            @RequestBody MovimentacaoEstoqueRequest request){
        MovimentacaoEstoqueDomain mov = produtoService.saidaEstoque(id, request.getQuantidade(), request.getObservacao());
        return  ResponseEntity.ok(mov);
    }
}
