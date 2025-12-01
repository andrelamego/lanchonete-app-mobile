package com.fatec.lanchonetemobile.application.dto;

import com.fatec.lanchonetemobile.application.mapper.ProdutoMapper;
import com.fatec.lanchonetemobile.domain.entity.Produto;

public record ItemPedidoDTO(
        int nPedido,
        Produto produto,
        int qtd,
        double valorUnit,
        double valorTotal
) {
    static ProdutoMapper produtoMapper = new ProdutoMapper();

    public int getNPedido() {
        return nPedido;
    }

    public ProdutoDTO getProdutoDTO() {
        return produtoMapper.toDTO(produto);
    }

    public int getIdProduto(){
        return produto.getId();
    }

    public String getNomeProduto(){
        return produto.getNome();
    }

    public int getQtd() {
        return qtd;
    }

    public double getValorUnit(){
        return valorUnit;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
