package com.checkmattech.food.controller;

import com.checkmattech.food.domain.PratoDomain;
import com.checkmattech.food.service.PratoService;
import com.checkmattech.food.domain.FichaTecnicaDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;


@RestController
@RequestMapping("/api/pratos")
public class PratoController {
    private final PratoService pratoService;

    public  PratoController(PratoService pratoService) {
        this.pratoService = pratoService;
    }

    @PostMapping
    public ResponseEntity<PratoDomain> criarPrato (@RequestBody PratoDomain prato){
        return ResponseEntity.ok(pratoService.criarPrato(prato));
    }

    @PostMapping("/{pratoID}/itens")
    public ResponseEntity<FichaTecnicaDomain> addItem (@PathVariable Long pratoId, @RequestParam Long produtoId, @RequestParam BigDecimal quantidade){
        return ResponseEntity.ok(pratoService.adicionarItemFicha(pratoId, produtoId, quantidade));
    }

    @GetMapping("/{pratoID}/ficha")
    public ResponseEntity<List<FichaTecnicaDomain>> listarFichas (@PathVariable Long pratoID){
        return ResponseEntity.ok(pratoService.listarFichaDoPrato(pratoID));
    }

}
