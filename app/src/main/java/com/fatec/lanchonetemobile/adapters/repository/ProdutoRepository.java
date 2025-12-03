package com.fatec.lanchonetemobile.adapters.repository;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.fatec.lanchonetemobile.application.repository.RepositoryReturn;
import com.fatec.lanchonetemobile.domain.entity.Categoria;
import com.fatec.lanchonetemobile.domain.entity.Fornecedor;
import com.fatec.lanchonetemobile.domain.entity.Produto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository implements RepositoryReturn<Produto> {
    private SQLiteDatabase connection;

    public ProdutoRepository(SQLiteDatabase connection){
        this.connection = connection; 
    }

    @Override
    public int salvar(Produto entidade) throws SQLException {
        String sql = "INSERT INTO Produto(Nome, QtdEstoque, ValorUnit, ID_Categoria, ID_Fornecedor) VALUES (?, ?, ?, ?, ?)";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindString(1, entidade.getNome());
        ss.bindLong(2, entidade.getQntdEstoq());
        ss.bindDouble(3, entidade.getValorUn());
        ss.bindLong(4, entidade.getCategoria().getId());
        ss.bindLong(5, entidade.getFornecedor().getId());
        ss.execute();

        int idProduto = 0;
        Cursor cursor = connection.rawQuery("SELECT last_insert_rowid()", null);
        cursor.moveToFirst();

        if(cursor.isAfterLast()){
            idProduto = cursor.getInt(0);
        }

        cursor.close();
        ss.close();
        return idProduto;
    }

    @Override
    public void atualizar(Produto entidade) throws SQLException {
        String sql = "UPDATE Produto SET Nome = ?, QtdEstoque = ?, ValorUnit = ?, ID_Categoria = ?, ID_Fornecedor = ? WHERE ID = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindString(1, entidade.getNome());
        ss.bindLong(2, entidade.getQntdEstoq());
        ss.bindDouble(3, entidade.getValorUn());
        ss.bindLong(4, entidade.getCategoria().getId());
        ss.bindLong(5, entidade.getFornecedor().getId());
        ss.bindLong(6, entidade.getId());
        ss.execute();
        ss.close();
    }

    @Override
    public void excluir(Produto entidade) throws SQLException {
        String sql = "DELETE FROM Produto WHERE ID = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindLong(1, entidade.getId());
        ss.execute();
        ss.close();
    }

    @SuppressLint("Range")
    @Override
    public Produto buscarPorID(Produto entidade) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.ID AS ID_Produto, p.Nome AS Nome_Produto, p.QtdEstoque, p.ValorUnit, ");
        sql.append("c.ID AS ID_Categoria, c.Nome AS Nome_Categoria, c.Descricao, ");
        sql.append("f.ID AS ID_Fornecedor, f.Nome AS Nome_Fornecedor, f.Telefone, f.CNPJ, f.Logradouro, f.Numero, f.CEP, f.Complemento ");
        sql.append("FROM Produto p INNER JOIN Categoria c ");
        sql.append("ON p.ID_Categoria = c.ID ");
        sql.append("INNER JOIN Fornecedor f ");
        sql.append("ON p.ID_Fornecedor = f.ID ");
        sql.append("WHERE p.ID = ?");

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql.toString(), new String[]{String.valueOf(entidade.getId())});
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(cursor.getColumnIndex("ID_Categoria")));
            categoria.setNome(cursor.getString(cursor.getColumnIndex("Nome_Categoria")));
            categoria.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(cursor.getInt(cursor.getColumnIndex("ID_Fornecedor")));
            fornecedor.setNome(cursor.getString(cursor.getColumnIndex("Nome_Fornecedor")));
            fornecedor.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            fornecedor.setCnpj(cursor.getString(cursor.getColumnIndex("CNPJ")));
            fornecedor.setLogradouro(cursor.getString(cursor.getColumnIndex("Logradouro")));
            fornecedor.setNumero(cursor.getInt(cursor.getColumnIndex("Numero")));
            fornecedor.setCep(cursor.getString(cursor.getColumnIndex("CEP")));
            fornecedor.setComplemento(cursor.getString(cursor.getColumnIndex("Complemento")));

            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID_Produto")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome_Produto")));
            entidade.setQntdEstoq(cursor.getInt(cursor.getColumnIndex("QtdEstoque")));
            entidade.setValorUn(cursor.getDouble(cursor.getColumnIndex("ValorUnit")));
            entidade.setCategoria(categoria);
            entidade.setFornecedor(fornecedor);

            cont++;
        }

        if(cont == 0){
            entidade = null;
        }

        cursor.close();
        return entidade;
    }

    @SuppressLint("Range")
    @Override
    public List<Produto> listar() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.ID AS ID_Produto, p.Nome AS Nome_Produto, p.QtdEstoque, p.ValorUnit, ");
        sql.append("c.ID AS ID_Categoria, c.Nome AS Nome_Categoria, c.Descricao, ");
        sql.append("f.ID AS ID_Fornecedor, f.Nome AS Nome_Fornecedor, f.Telefone, f.CNPJ, f.Logradouro, f.Numero, f.CEP, f.Complemento ");
        sql.append("FROM Produto p INNER JOIN Categoria c ");
        sql.append("ON p.ID_Categoria = c.ID ");
        sql.append("INNER JOIN Fornecedor f ");
        sql.append("ON p.ID_Fornecedor = f.ID ");

        List<Produto> entidades = new ArrayList<>();
        Cursor cursor = connection.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(cursor.getColumnIndex("ID_Categoria")));
            categoria.setNome(cursor.getString(cursor.getColumnIndex("Nome_Categoria")));
            categoria.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(cursor.getInt(cursor.getColumnIndex("ID_Fornecedor")));
            fornecedor.setNome(cursor.getString(cursor.getColumnIndex("Nome_Fornecedor")));
            fornecedor.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            fornecedor.setCnpj(cursor.getString(cursor.getColumnIndex("CNPJ")));
            fornecedor.setLogradouro(cursor.getString(cursor.getColumnIndex("Logradouro")));
            fornecedor.setNumero(cursor.getInt(cursor.getColumnIndex("Numero")));
            fornecedor.setCep(cursor.getString(cursor.getColumnIndex("CEP")));
            fornecedor.setComplemento(cursor.getString(cursor.getColumnIndex("Complemento")));

            Produto entidade = new Produto();
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID_Produto")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome_Produto")));
            entidade.setQntdEstoq(cursor.getInt(cursor.getColumnIndex("QtdEstoque")));
            entidade.setValorUn(cursor.getDouble(cursor.getColumnIndex("ValorUnit")));
            entidade.setCategoria(categoria);
            entidade.setFornecedor(fornecedor);

            entidades.add(entidade);
            cursor.moveToNext();
        }

        cursor.close();
        return entidades;
    }

    @SuppressLint("Range")
    @Override
    public Produto buscarPorChaveSecundaria(Produto entidade) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.ID AS ID_Produto, p.Nome AS Nome_Produto, p.QtdEstoque, p.ValorUnit, ");
        sql.append("c.ID AS ID_Categoria, c.Nome AS Nome_Categoria, c.Descricao, ");
        sql.append("f.ID AS ID_Fornecedor, f.Nome AS Nome_Fornecedor, f.Telefone, f.CNPJ, f.Logradouro, f.Numero, f.CEP, f.Complemento ");
        sql.append("FROM Produto p INNER JOIN Categoria c ");
        sql.append("ON p.ID_Categoria = c.ID ");
        sql.append("INNER JOIN Fornecedor f ");
        sql.append("ON p.ID_Fornecedor = f.ID ");
        sql.append("WHERE p.Nome LIKE ?");

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql.toString(), new String[]{entidade.getNome() + "%"});
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(cursor.getColumnIndex("ID_Categoria")));
            categoria.setNome(cursor.getString(cursor.getColumnIndex("Nome_Categoria")));
            categoria.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(cursor.getInt(cursor.getColumnIndex("ID_Fornecedor")));
            fornecedor.setNome(cursor.getString(cursor.getColumnIndex("Nome_Fornecedor")));
            fornecedor.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            fornecedor.setCnpj(cursor.getString(cursor.getColumnIndex("CNPJ")));
            fornecedor.setLogradouro(cursor.getString(cursor.getColumnIndex("Logradouro")));
            fornecedor.setNumero(cursor.getInt(cursor.getColumnIndex("Numero")));
            fornecedor.setCep(cursor.getString(cursor.getColumnIndex("CEP")));
            fornecedor.setComplemento(cursor.getString(cursor.getColumnIndex("Complemento")));

            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID_Produto")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome_Produto")));
            entidade.setQntdEstoq(cursor.getInt(cursor.getColumnIndex("QtdEstoque")));
            entidade.setValorUn(cursor.getDouble(cursor.getColumnIndex("ValorUnit")));
            entidade.setCategoria(categoria);
            entidade.setFornecedor(fornecedor);

            cont++;
        }

        if(cont == 0){
            entidade = null;
        }

        cursor.close();
        return entidade;
    }
}
