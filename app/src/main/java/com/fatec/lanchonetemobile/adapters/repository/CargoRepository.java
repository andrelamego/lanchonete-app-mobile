package com.fatec.lanchonetemobile.adapters.repository;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.fatec.lanchonetemobile.application.repository.RepositoryNoReturn;
import com.fatec.lanchonetemobile.domain.entity.Cargo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoRepository implements RepositoryNoReturn<Cargo> {
    private SQLiteDatabase connection;

    public CargoRepository(SQLiteDatabase connection){
        this.connection = connection; 
    }

    @Override
    public void salvar(Cargo entidade) throws SQLException{
        String sql = "INSERT INTO Cargo(Nome, Salario, Descricao) VALUES(?, ?, ?)";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindString(1, entidade.getNome());
        ss.bindDouble(2, entidade.getSalario());
        ss.bindString(3, entidade.getDescricao());
        ss.execute();
        ss.close();
    }

    @Override
    public void atualizar(Cargo entidade) throws SQLException{
        String sql = "UPDATE Cargo SET Nome = ?, Salario = ?, Descricao = ? WHERE ID = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindString(1, entidade.getNome());
        ss.bindDouble(2, entidade.getSalario());
        ss.bindString(3, entidade.getDescricao());
        ss.bindLong(4, entidade.getId());
        ss.execute();
        ss.close();
    }

    @Override
    public void excluir(Cargo entidade) throws SQLException{
        String sql = "DELETE FROM Cargo WHERE ID = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindLong(1, entidade.getId());
        ss.execute();
        ss.close();
    }

    @SuppressLint("Range")
    @Override
    public Cargo buscarPorID(Cargo entidade) throws SQLException{
        String sql = "SELECT ID, Nome, Salario, Descricao FROM Cargo WHERE ID = " + entidade.getId();

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql, null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            entidade.setSalario(cursor.getDouble(cursor.getColumnIndex("Salario")));
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
    public List<Cargo> listar() throws SQLException{
        String sql = "SELECT ID, Nome, Salario, Descricao FROM Cargo";

        List<Cargo> entidades = new ArrayList<>();
        Cursor cursor = connection.rawQuery(sql, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Cargo entidade = new Cargo();
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            entidade.setSalario(cursor.getDouble(cursor.getColumnIndex("Salario")));
            entidade.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

            entidades.add(entidade);
            cursor.moveToNext();
        }

        cursor.close();
        return entidades;
    }

    @SuppressLint("Range")
    @Override
    public Cargo buscarPorChaveSecundaria(Cargo entidade) {

        String sql = "SELECT ID, Nome, Salario, Descricao FROM Cargo WHERE Nome LIKE ?";
        String[] args = new String[]{ entidade.getNome() + "%" };

        Cursor cursor = connection.rawQuery(sql, args);

        if (cursor.moveToFirst()) {
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            entidade.setSalario(cursor.getDouble(cursor.getColumnIndex("Salario")));
            entidade.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));
        } else {
            entidade = null;
        }

        cursor.close();
        return entidade;
    }
}
