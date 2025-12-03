package com.fatec.lanchonetemobile.adapters.repository;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.fatec.lanchonetemobile.application.repository.RepositoryNoReturn;
import com.fatec.lanchonetemobile.domain.entity.Categoria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository implements RepositoryNoReturn<Categoria> {
    private SQLiteDatabase connection;

    public CategoriaRepository(SQLiteDatabase connection){
        this.connection = connection;
    }

    @Override
    public void salvar(Categoria entidade) throws SQLException {
        String sql = "INSERT INTO Categoria(Nome, Descricao) VALUES(?, ?)";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindString(1, entidade.getNome());
        ss.bindString(2, entidade.getDescricao());
        ss.execute();
        ss.close();
    }

    @Override
    public void atualizar(Categoria entidade) throws SQLException {
        String sql = "UPDATE Categoria SET Nome = ?, Descricao = ? WHERE ID = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindString(1, entidade.getNome());
        ss.bindString(2, entidade.getDescricao());
        ss.bindLong(3, entidade.getId());
        ss.execute();
        ss.close();
    }

    @Override
    public void excluir(Categoria entidade) throws SQLException {
        String sql = "DELETE FROM Categoria WHERE ID = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindLong(1, entidade.getId());
        ss.execute();
        ss.close();
    }

    @SuppressLint("Range")
    @Override
    public Categoria buscarPorID(Categoria entidade) throws SQLException {
        String sql = "SELECT ID, Nome, Descricao FROM Categoria WHERE ID = ?";

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql, new String[]{String.valueOf(entidade.getId())});
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            entidade.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

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
    public List<Categoria> listar() throws SQLException {
        String sql = "SELECT ID, Nome, Descricao FROM Categoria";

        List<Categoria> entidades = new ArrayList<>();
        Cursor cursor = connection.rawQuery(sql, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Categoria entidade = new Categoria();
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            entidade.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

            entidades.add(entidade);
            cursor.moveToNext();
        }

        cursor.close();
        return entidades;
    }

    @SuppressLint("Range")
    @Override
    public Categoria buscarPorChaveSecundaria(Categoria entidade) throws SQLException {
       String sql = "SELECT ID, Nome, Descricao FROM Categoria WHERE Nome LIKE ?" ;

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql, new String[]{entidade.getNome() + "%"});
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            entidade.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

            cont++;
        }

        if(cont == 0){
            entidade = null;
        }

        cursor.close();
        return entidade;
    }
}