package com.checkmattech.food.controller;

import com.checkmattech.food.domain.FichaTecnicaDomain;
import com.checkmattech.food.service.FichaTecnicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/fichas-tecnicas")
public class FichaTecnicaController {

    private final FichaTecnicaService fichaService;

    public FichaTecnicaController(FichaTecnicaService fichaService) {
        this.fichaService = fichaService;
    }

    // Adicionar item na ficha técnica
    @PostMapping
    public ResponseEntity<FichaTecnicaDomain> adicionarItem(
            @RequestParam Long pratoId,
            @RequestParam Long produtoId,
            @RequestParam BigDecimal quantidadeUsada
    ) {
        FichaTecnicaDomain ficha = fichaService.adicionarItem(pratoId, produtoId, quantidadeUsada);
        return ResponseEntity.ok(ficha);
    }

    // Listar ficha técnica de um prato
    @GetMapping("/prato/{pratoId}")
    public ResponseEntity<List<FichaTecnicaDomain>> listarPorPrato(@PathVariable Long pratoId) {
        return ResponseEntity.ok(fichaService.listarPorPrato(pratoId));
    }

    // Atualizar quantidade de um item
    @PutMapping("/{fichaId}")
    public ResponseEntity<FichaTecnicaDomain> atualizarQuantidade(
            @PathVariable Long fichaId,
            @RequestParam BigDecimal quantidadeUsada) {
        FichaTecnicaDomain atualizada = fichaService.atualizarQuantidade(fichaId, quantidadeUsada);
        return ResponseEntity.ok(atualizada);
    }

    // Remover item da ficha técnica
    @DeleteMapping("/{fichaId}")
    public ResponseEntity<Void> remover(@PathVariable Long fichaId) {
        fichaService.removerItem(fichaId);
        return ResponseEntity.noContent().build();
    }
}
