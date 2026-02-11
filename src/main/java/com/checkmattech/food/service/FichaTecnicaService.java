package com.checkmattech.food.service;

import com.checkmattech.food.domain.FichaTecnicaDomain;
import com.checkmattech.food.domain.PratoDomain;
import com.checkmattech.food.domain.ProdutoDomain;
import com.checkmattech.food.exception.ResourceNotFoundException;
import com.checkmattech.food.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FichaTecnicaService {
    private final FichaTecnicaRepository fichaTecnicaRepo;
    private final PratoRepository pratoRepo;
    private final ProdutoRepository produtoRepo;

    public FichaTecnicaService(FichaTecnicaRepository FichaRepo, ProdutoRepository produtoRepo, PratoRepository pratoRepo) {

        this.fichaTecnicaRepo = FichaRepo;
        this.pratoRepo = pratoRepo;
        this.produtoRepo = produtoRepo;
    }

    @Transactional
    public FichaTecnicaDomain adicionarItem(Long pratoId, Long produtoID, BigDecimal quantidade){
        if(quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Quantidade usada deve ser maior que zero");

        PratoDomain prato = pratoRepo.findById(pratoId)
                .orElseThrow(() -> new ResourceNotFoundException("Prato não encontrado: "+ pratoId));

        ProdutoDomain produto = produtoRepo.findById(produtoID)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: "+ produtoID));

        FichaTecnicaDomain ficha = new FichaTecnicaDomain(prato, produto, quantidade);
        return fichaTecnicaRepo.save(ficha);
    }

    public List<FichaTecnicaDomain> listarPorPrato(Long pratoID){
        return fichaTecnicaRepo.findByPratoId(pratoID);
    }

    @Transactional
    public void removerItem(Long FichaID){
        FichaTecnicaDomain ficha = fichaTecnicaRepo.findById(FichaID)
                .orElseThrow(() -> new ResourceNotFoundException("Ficha inexistente: "+ FichaID));
        fichaTecnicaRepo.delete(ficha);
    }

    @Transactional
    public FichaTecnicaDomain atualizarQuantidade(Long fichaId, BigDecimal novaQuantidade) {
        if (novaQuantidade == null || novaQuantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantidade usada deve ser maior que zero");
        }
        FichaTecnicaDomain ficha = fichaTecnicaRepo.findById(fichaId)
                .orElseThrow(() -> new ResourceNotFoundException("Ficha inexistente: " + fichaId));
        ficha.setQuantidadeUsada(novaQuantidade);
        return fichaTecnicaRepo.save(ficha);
    }
}
