package com.fatec.lanchonetemobile.application.dto;

import com.fatec.lanchonetemobile.application.mapper.CategoriaMapper;
import com.fatec.lanchonetemobile.application.mapper.FornecedorMapper;
import com.fatec.lanchonetemobile.domain.entity.Categoria;
import com.fatec.lanchonetemobile.domain.entity.Fornecedor;

public record ProdutoDTO(
        int id,
        String nome,
        int qtdEstoque,
        double valorUn,
        Categoria categoria,
        Fornecedor fornecedor
) {
    static CategoriaMapper categoriaMapper = new CategoriaMapper();
    static FornecedorMapper fornecedorMapper = new FornecedorMapper();

    public int getId(){ return id; }
    public String getNome() { return nome; }
    public int getQtdEstoque() { return qtdEstoque; }
    public double getValorUn() { return valorUn; }
    public CategoriaDTO getCategoriaDTO() { return categoriaMapper.toDTO(categoria); }
    public String getCategoriaNome() { return categoria.getNome(); }
    public FornecedorDTO getFornecedorDTO() { return fornecedorMapper.toDTO(fornecedor); }
    public String getFornecedorNome() { return fornecedor.getNome(); }
}
