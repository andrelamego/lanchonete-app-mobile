package com.fatec.lanchonetemobile.domain.entity;

public class ProdutoFornecedor {
    private int IdProduto;
    private int idFornecedor;
    private String nomeProduto;
    private String nomeFornecedor;

    public int getIdProduto() {
        return IdProduto;
    }
    public int getIdFornecedor() {
        return idFornecedor;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public String getNomeFornecedor() {
        return nomeFornecedor;
    }
    public void setIdProduto(int idProduto) {
        IdProduto = idProduto;
    }
    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
}
