package com.checkmattech.food.service;

import com.checkmattech.food.*;
import com.checkmattech.food.domain.MovimentacaoEstoqueDomain;
import com.checkmattech.food.domain.ProdutoDomain;
import com.checkmattech.food.domain.TipoMovimentoDomain;
import com.checkmattech.food.exception.ResourceNotFoundException;
import com.checkmattech.food.repository.MovimentacaoEstoqueRepository;
import com.checkmattech.food.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public ProdutoService(ProdutoRepository produtoRepository, MovimentacaoEstoqueRepository movimentacaoEstoqueRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
    }

    public ProdutoDomain criarProduto(ProdutoDomain Produto){
        if(Produto.getEstoqueAtual() == null) Produto.setEstoqueAtual(BigDecimal.ZERO);
        if(Produto.getEstoqueMinimo() == null) Produto.setEstoqueMinimo(BigDecimal.ZERO);
        return produtoRepository.save(Produto);
    }

    public ProdutoDomain buscarID(Long id){
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não  encontrado"));
    }

    public List<ProdutoDomain> listarTodos() {
        return produtoRepository.findAll();
    }

    public ProdutoDomain atualizar(Long id, ProdutoDomain dados) {
        ProdutoDomain existente = buscarID(id);
        existente.setNome(dados.getNome());
        existente.setUnidadeMedida(dados.getUnidadeMedida());
        existente.setEstoqueMinimo(dados.getEstoqueMinimo());
        existente.setEstoqueAtual(dados.getEstoqueAtual());
        return produtoRepository.save(existente);
    }

    public void excluir(Long id) {
        ProdutoDomain existente = buscarID(id);
        produtoRepository.delete(existente);
    }

    @Transactional
    public MovimentacaoEstoqueDomain entradaEstoque(Long produtoID, BigDecimal quantidade, String observacao){
        if(quantidade  == null || quantidade.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Quantidade nao pode ser menor que 0");

        ProdutoDomain produto = buscarID(produtoID);
        produto.adicionarEstoque(quantidade);
        produtoRepository.save(produto);

        MovimentacaoEstoqueDomain mov = new MovimentacaoEstoqueDomain(produto, quantidade, TipoMovimentoDomain.Entrada, observacao);
        movimentacaoEstoqueRepository.save(mov);
        return mov;
    }

    @Transactional
    public MovimentacaoEstoqueDomain saidaEstoque(Long  produtoID, BigDecimal quantidade, String observacao){
        if(quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Quantidade nao pode ser menor que 0");

        ProdutoDomain produto = buscarID(produtoID);
        if(produto.getEstoqueAtual().compareTo(quantidade) < 0) throw new IllegalArgumentException("Estoque insuficiente");

        produto.removerEstoque(quantidade);
        produtoRepository.save(produto);

        MovimentacaoEstoqueDomain mov = new MovimentacaoEstoqueDomain(produto, quantidade, TipoMovimentoDomain.Sainda, observacao);
        movimentacaoEstoqueRepository.save(mov);
        return mov;
    }
}
