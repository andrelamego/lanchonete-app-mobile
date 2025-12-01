package com.fatec.lanchonetemobile.adapters.repository;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.fatec.lanchonetemobile.domain.entity.Cliente;
import com.fatec.lanchonetemobile.application.repository.RepositoryNoReturn;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository implements RepositoryNoReturn<Cliente> {
    private SQLiteDatabase connection;

    public ClienteRepository(SQLiteDatabase connection){
        this.connection = connection; 
    }

    @Override
    public void salvar(Cliente entidade) throws SQLException {
        String sql = "INSERT INTO Cliente(Nome, Telefone, CPF, Logradouro, Numero, CEP, Complemento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindString(1, entidade.getNome());
        ss.bindString(2, entidade.getTel());
        ss.bindString(3, entidade.getCpf());
        ss.bindString(4, entidade.getLogradouro());
        ss.bindLong(5, entidade.getNumero());
        ss.bindString(6, entidade.getCep());
        ss.bindString(7, entidade.getComplemento());
        ss.execute();
        ss.close();
    }

    @Override
    public void atualizar(Cliente entidade) throws SQLException {
        String sql = "UPDATE Cliente SET Nome = ?, Telefone = ?, CPF = ?, Logradouro = ?, " +
                    "Numero = ?, CEP = ?, Complemento = ? WHERE ID = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindString(1, entidade.getNome());
        ss.bindString(2, entidade.getTel());
        ss.bindString(3, entidade.getCpf());
        ss.bindString(4, entidade.getLogradouro());
        ss.bindLong(5, entidade.getNumero());
        ss.bindString(6, entidade.getCep());
        ss.bindString(7, entidade.getComplemento());
        ss.bindLong(8, entidade.getId());
        ss.execute();
        ss.close();
    }

    @Override
    public void excluir(Cliente entidade) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE ID = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindLong(1, entidade.getId());
        ss.execute();
        ss.close();
    }

    @SuppressLint("Range")
    @Override
    public Cliente buscarPorID(Cliente entidade) throws SQLException {
        String sql = "SELECT ID, Nome, Telefone, CPF, Logradouro, Numero, CEP, Complemento FROM Cliente WHERE ID = " + entidade.getId();
        int cont = 0;
        Cursor cursor = connection.rawQuery(sql, null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            entidade.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            entidade.setCpf(cursor.getString(cursor.getColumnIndex("CPF")));
            entidade.setLogradouro(cursor.getString(cursor.getColumnIndex("Logradouro")));
            entidade.setNumero(cursor.getInt(cursor.getColumnIndex("Numero")));
            entidade.setCep(cursor.getString(cursor.getColumnIndex("CEP")));
            entidade.setComplemento(cursor.getString(cursor.getColumnIndex("Complemento")));

            cont++;
        }

        if(cont == 0){
            return null;
        }

        cursor.close();
        return entidade;
    }

    @SuppressLint("Range")
    @Override
    public List<Cliente> listar() throws SQLException {
        String sql = "SELECT ID, Nome, Telefone, CPF, Logradouro, Numero, CEP, Complemento FROM Cliente";

        List<Cliente> entidades = new ArrayList<>();
        Cursor cursor = connection.rawQuery(sql, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Cliente entidade = new Cliente();
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            entidade.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            entidade.setCpf(cursor.getString(cursor.getColumnIndex("CPF")));
            entidade.setLogradouro(cursor.getString(cursor.getColumnIndex("Logradouro")));
            entidade.setNumero(cursor.getInt(cursor.getColumnIndex("Numero")));
            entidade.setCep(cursor.getString(cursor.getColumnIndex("CEP")));
            entidade.setComplemento(cursor.getString(cursor.getColumnIndex("Complemento")));

            entidades.add(entidade);
            cursor.moveToNext();
        }

        cursor.close();
        return entidades;
    }
    
    @SuppressLint("Range")
    @Override
    public Cliente buscarPorChaveSecundaria(Cliente entidade) throws SQLException {
        String sql = "SELECT ID, Nome, Telefone, CPF, Logradouro, Numero, CEP, Complemento FROM Cliente WHERE CPF = " + entidade.getCpf();

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql, null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            entidade.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            entidade.setCpf(cursor.getString(cursor.getColumnIndex("CPF")));
            entidade.setLogradouro(cursor.getString(cursor.getColumnIndex("Logradouro")));
            entidade.setNumero(cursor.getInt(cursor.getColumnIndex("Numero")));
            entidade.setCep(cursor.getString(cursor.getColumnIndex("CEP")));
            entidade.setComplemento(cursor.getString(cursor.getColumnIndex("Complemento")));

            cont++;
        }

        if(cont == 0){
            entidade = null;
        }

        cursor.close();
        return entidade;
    }
}