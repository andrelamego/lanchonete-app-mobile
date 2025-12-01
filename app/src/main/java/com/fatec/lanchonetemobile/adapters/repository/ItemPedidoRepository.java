package com.fatec.lanchonetemobile.adapters.repository;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fatec.lanchonetemobile.application.repository.RepositoryListById;
import com.fatec.lanchonetemobile.domain.entity.Categoria;
import com.fatec.lanchonetemobile.domain.entity.ItemPedido;
import com.fatec.lanchonetemobile.domain.entity.Produto;

public class ItemPedidoRepository implements RepositoryListById<ItemPedido>{
    private SQLiteDatabase connection;

    public ItemPedidoRepository(SQLiteDatabase connection){
        this.connection = connection;
    }

    @Override
    public void salvar(ItemPedido entidade) throws SQLException {
        String sql = "INSERT INTO Item_Pedido(Num_Pedido, ID_Produto, Qtd, ValorUnit, ValorTotalItem) VALUES (?, ?, ?, ?, ?)";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindLong(1, entidade.getNumPedido());
        ss.bindLong(2, entidade.getProduto().getId());
        ss.bindLong(3, entidade.getQtd());
        ss.bindDouble(4, entidade.getValorUnit());
        ss.bindDouble(5, entidade.getValorTotal());
        ss.execute();
        ss.close();
    }

    @Override
    public void atualizar(ItemPedido entidade) throws SQLException {
        String sql = "UPDATE Item_Pedido SET Num_Pedido = ?, ID_Produto = ?, Qtd = ?, ValorUnit = ?, ValorTotalItem = ? " +
                    "WHERE Num_Pedido = ? AND ID_Produto = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindLong(1, entidade.getNumPedido());
        ss.bindLong(2, entidade.getProduto().getId());
        ss.bindLong(3, entidade.getQtd());
        ss.bindDouble(4, entidade.getValorUnit());
        ss.bindDouble(5, entidade.getValorTotal());
        ss.bindLong(6, entidade.getNumPedido());
        ss.bindLong(7, entidade.getProduto().getId());
        ss.execute();
        ss.close();
    }

    @Override
    public void excluir(ItemPedido entidade) throws SQLException {
        String sql = "DELETE FROM Item_Pedido WHERE Num_Pedido = ? AND ID_Produto = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindLong(1, entidade.getNumPedido());
        ss.bindLong(2, entidade.getProduto().getId());
        ss.execute();
        ss.close();
    }

    @SuppressLint("Range")
    @Override
    public ItemPedido buscarPorID(ItemPedido entidade) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ip.Num_Pedido, ip.Qtd, ip.ValorUnit, ip.ValorTotalItem, ");
        sql.append("p.ID AS ID_Produto, p.Nome AS Nome_Produto, p.QtdEstoque, p.ValorUnit, ");
        sql.append("c.ID AS ID_Categoria, c.Nome, c.Descricao ");
        sql.append("FROM Item_Pedido ip INNER JOIN Produto p ");
        sql.append("ON ip.ID_Produto = p.ID ");
        sql.append("INNER JOIN Categoria c ");
        sql.append("ON p.ID_Categoria = c.ID ");
        sql.append("WHERE ip.Num_Pedido = ").append(entidade.getNumPedido());
        sql.append("AND ip.ID_Produto = ").append(entidade.getProduto().getId());

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(cursor.getColumnIndex("ID_Categoria")));
            categoria.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            categoria.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));
            
            Produto produto = new Produto();
            produto.setId(cursor.getInt(cursor.getColumnIndex("ID_Produto")));
            produto.setNome(cursor.getString(cursor.getColumnIndex("Nome_Produto")));
            produto.setQntdEstoq(cursor.getInt(cursor.getColumnIndex("QtdEstoque")));
            produto.setValorUn(cursor.getDouble(cursor.getColumnIndex("ValorUnit")));
            produto.setCategoria(categoria);
            
            entidade.setNumPedido(cursor.getInt(cursor.getColumnIndex("Num_Pedido")));
            entidade.setQtd(cursor.getInt(cursor.getColumnIndex("Qtd")));
            entidade.setValorUnit(cursor.getDouble(cursor.getColumnIndex("ValorUnit")));
            entidade.setValorTotal(cursor.getDouble(cursor.getColumnIndex("ValorTotalItem")));
            entidade.setProduto(produto);

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
    public List<ItemPedido> listar() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ip.Num_Pedido, ip.Qtd, ip.ValorUnit, ip.ValorTotalItem, ");
        sql.append("p.ID AS ID_Produto, p.Nome AS Nome_Produto, p.QtdEstoque, p.ValorUnit, ");
        sql.append("c.ID AS ID_Categoria, c.Nome, c.Descricao ");
        sql.append("FROM Item_Pedido ip INNER JOIN Produto p ");
        sql.append("ON ip.ID_Produto = p.ID ");
        sql.append("INNER JOIN Categoria c ");
        sql.append("ON p.ID_Categoria = c.ID");

        List<ItemPedido> entidades = new ArrayList<>();
        Cursor cursor = connection.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(cursor.getColumnIndex("ID_Categoria")));
            categoria.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            categoria.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

            Produto produto = new Produto();
            produto.setId(cursor.getInt(cursor.getColumnIndex("ID_Produto")));
            produto.setNome(cursor.getString(cursor.getColumnIndex("Nome_Produto")));
            produto.setQntdEstoq(cursor.getInt(cursor.getColumnIndex("QtdEstoque")));
            produto.setValorUn(cursor.getDouble(cursor.getColumnIndex("ValorUnit")));
            produto.setCategoria(categoria);

            ItemPedido entidade = new ItemPedido();
            entidade.setNumPedido(cursor.getInt(cursor.getColumnIndex("Num_Pedido")));
            entidade.setQtd(cursor.getInt(cursor.getColumnIndex("Qtd")));
            entidade.setValorUnit(cursor.getDouble(cursor.getColumnIndex("ValorUnit")));
            entidade.setValorTotal(cursor.getDouble(cursor.getColumnIndex("ValorTotalItem")));
            entidade.setProduto(produto);

            entidades.add(entidade);
            cursor.moveToNext();
        }

        cursor.close();
        return entidades;
    }

    @SuppressLint("Range")
    @Override
    public List<ItemPedido> listarPorId(int nPedido) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ip.Num_Pedido, ip.Qtd, ip.ValorUnit, ip.ValorTotalItem, ");
        sql.append("p.ID AS ID_Produto, p.Nome AS Nome_Produto, p.QtdEstoque, p.ValorUnit, ");
        sql.append("c.ID AS ID_Categoria, c.Nome, c.Descricao ");
        sql.append("FROM Item_Pedido ip INNER JOIN Produto p ");
        sql.append("ON ip.ID_Produto = p.ID ");
        sql.append("INNER JOIN Categoria c ");
        sql.append("ON p.ID_Categoria = c.ID ");
        sql.append("WHERE ip.Num_Pedido = ").append(nPedido);

        List<ItemPedido> entidades = new ArrayList<>();
        Cursor cursor = connection.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(cursor.getColumnIndex("ID_Categoria")));
            categoria.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            categoria.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

            Produto produto = new Produto();
            produto.setId(cursor.getInt(cursor.getColumnIndex("ID_Produto")));
            produto.setNome(cursor.getString(cursor.getColumnIndex("Nome_Produto")));
            produto.setQntdEstoq(cursor.getInt(cursor.getColumnIndex("QtdEstoque")));
            produto.setValorUn(cursor.getDouble(cursor.getColumnIndex("ValorUnit")));
            produto.setCategoria(categoria);

            ItemPedido entidade = new ItemPedido();
            entidade.setNumPedido(cursor.getInt(cursor.getColumnIndex("Num_Pedido")));
            entidade.setQtd(cursor.getInt(cursor.getColumnIndex("Qtd")));
            entidade.setValorUnit(cursor.getDouble(cursor.getColumnIndex("ValorUnit")));
            entidade.setValorTotal(cursor.getDouble(cursor.getColumnIndex("ValorTotalItem")));
            entidade.setProduto(produto);

            entidades.add(entidade);
            cursor.moveToNext();
        }

        cursor.close();
        return entidades;
    }

    @SuppressLint("Range")
    @Override
    public ItemPedido buscarPorChaveSecundaria(ItemPedido entidade) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ip.Num_Pedido, ip.Qtd, ip.ValorUnit, ip.ValorTotalItem, ");
        sql.append("p.ID AS ID_Produto, p.Nome AS Nome_Produto, p.QtdEstoque, p.ValorUnit, ");
        sql.append("c.ID AS ID_Categoria, c.Nome, c.Descricao ");
        sql.append("FROM Item_Pedido ip INNER JOIN Produto p ");
        sql.append("ON ip.ID_Produto = p.ID ");
        sql.append("INNER JOIN Categoria c ");
        sql.append("ON p.ID_Categoria = c.ID ");
        sql.append("WHERE p.Nome LIKE ").append(entidade.getProduto().getNome());

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(cursor.getColumnIndex("ID_Categoria")));
            categoria.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            categoria.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

            Produto produto = new Produto();
            produto.setId(cursor.getInt(cursor.getColumnIndex("ID_Produto")));
            produto.setNome(cursor.getString(cursor.getColumnIndex("Nome_Produto")));
            produto.setQntdEstoq(cursor.getInt(cursor.getColumnIndex("QtdEstoque")));
            produto.setValorUn(cursor.getDouble(cursor.getColumnIndex("ValorUnit")));
            produto.setCategoria(categoria);

            entidade.setNumPedido(cursor.getInt(cursor.getColumnIndex("Num_Pedido")));
            entidade.setQtd(cursor.getInt(cursor.getColumnIndex("Qtd")));
            entidade.setValorUnit(cursor.getDouble(cursor.getColumnIndex("ValorUnit")));
            entidade.setValorTotal(cursor.getDouble(cursor.getColumnIndex("ValorTotalItem")));
            entidade.setProduto(produto);

            cont++;
        }

        if(cont == 0){
            entidade = null;
        }

        cursor.close();
        return entidade;
    }
}
