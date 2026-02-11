package com.checkmattech.food.service;

import com.checkmattech.food.domain.*;
import com.checkmattech.food.repository.FichaTecnicaRepository;
import com.checkmattech.food.repository.PratoRepository;
import com.checkmattech.food.repository.ProdutoRepository;
import com.checkmattech.food.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PratoService {

    private final PratoRepository pratoRepo;
    private final ProdutoRepository produtoRepo;
    private final FichaTecnicaRepository fichaTecnicaRepo;

    public PratoService(PratoRepository pratoRepo,
                        ProdutoRepository produtoRepo,
                        FichaTecnicaRepository fichaTecnicaRepo) {
        this.pratoRepo = pratoRepo;
        this.produtoRepo = produtoRepo;
        this.fichaTecnicaRepo = fichaTecnicaRepo;
    }

    public List<PratoDomain> listarTodos() {
        return pratoRepo.findAll();
    }

    public PratoDomain criarPrato(PratoDomain prato) {
        return pratoRepo.save(prato);
    }

    public PratoDomain buscarPorId(Long id) {
        return pratoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prato não encontrado: " + id));
    }

    public PratoDomain atualizar(Long id, PratoDomain dados) {
        PratoDomain existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setPrecoVenda(dados.getPrecoVenda());
        return pratoRepo.save(existente);
    }

    public void excluir(Long id) {
        PratoDomain existente = buscarPorId(id);
        pratoRepo.delete(existente);
    }

    @Transactional
    public FichaTecnicaDomain adicionarItemFicha(Long pratoId, Long produtoId, BigDecimal qtdUsada) {

        PratoDomain prato = buscarPorId(pratoId);

        ProdutoDomain produto = produtoRepo.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + produtoId));

        FichaTecnicaDomain ficha = new FichaTecnicaDomain(prato, produto, qtdUsada);

        return fichaTecnicaRepo.save(ficha);
    }

    public List<FichaTecnicaDomain> listarFichaDoPrato(Long pratoId) {
        return fichaTecnicaRepo.findByPratoId(pratoId);
    }

    public BigDecimal calcularCustoDoPrato(Long pratoId) {
        List<FichaTecnicaDomain> itens = fichaTecnicaRepo.findByPratoId(pratoId);

        BigDecimal total = BigDecimal.ZERO;

       /* for (FichaTecnicaDomain ficha : itens) {
            if (ficha.getProduto().getCustoUnitario() != null) {
                total = total.add(
                        ficha.getProduto().getCustoUnitario()
                                .multiply(ficha.getQuantidadeUsada())
                );
            }
        }*/

        return total;
    }
}
