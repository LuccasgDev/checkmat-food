package com.checkmattech.food.controller;

import com.checkmattech.food.domain.PratoDomain;
import com.checkmattech.food.service.PratoService;
import com.checkmattech.food.domain.FichaTecnicaDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/pratos")
public class PratoController {
    private final PratoService pratoService;

    public PratoController(PratoService pratoService) {
        this.pratoService = pratoService;
    }

    @GetMapping
    public ResponseEntity<List<PratoDomain>> listarPratos() {
        return ResponseEntity.ok(pratoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PratoDomain> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pratoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PratoDomain> criarPrato(@RequestBody PratoDomain prato) {
        PratoDomain criado = pratoService.criarPrato(prato);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.getId())
                .toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PratoDomain> atualizarPrato(@PathVariable Long id,
                                                      @RequestBody PratoDomain prato) {
        PratoDomain atualizado = pratoService.atualizar(id, prato);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPrato(@PathVariable Long id) {
        pratoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{pratoId}/itens")
    public ResponseEntity<FichaTecnicaDomain> addItem(
            @PathVariable Long pratoId,
            @RequestParam Long produtoId,
            @RequestParam BigDecimal quantidade) {
        return ResponseEntity.ok(pratoService.adicionarItemFicha(pratoId, produtoId, quantidade));
    }

    @GetMapping("/{pratoId}/ficha")
    public ResponseEntity<List<FichaTecnicaDomain>> listarFichas(@PathVariable Long pratoId) {
        return ResponseEntity.ok(pratoService.listarFichaDoPrato(pratoId));
    }
}
