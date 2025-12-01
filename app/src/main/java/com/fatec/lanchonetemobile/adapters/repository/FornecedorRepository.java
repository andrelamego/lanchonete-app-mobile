package com.fatec.lanchonetemobile.adapters.repository;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.fatec.lanchonetemobile.application.repository.RepositoryNoReturn;
import com.fatec.lanchonetemobile.domain.entity.Fornecedor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FornecedorRepository implements RepositoryNoReturn<Fornecedor>{
    private SQLiteDatabase connection;

    public FornecedorRepository(SQLiteDatabase connection){
        this.connection = connection; 
    }

    @Override
    public void salvar(Fornecedor entidade) throws SQLException {
        String sql = "INSERT INTO Fornecedor(Nome, Telefone, CNPJ, Logradouro, Numero, CEP, Complemento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindString(1, entidade.getNome());
        ss.bindString(2, entidade.getTel());
        ss.bindString(3, entidade.getCnpj());
        ss.bindString(4, entidade.getLogradouro());
        ss.bindLong(5, entidade.getNumero());
        ss.bindString(6, entidade.getCep());
        ss.bindString(7, entidade.getComplemento());
        ss.execute();
        ss.close();
    }

    @Override
    public void atualizar(Fornecedor entidade) throws SQLException {
        String sql = "UPDATE Fornecedor SET Nome = ?, Telefone = ?, CNPJ = ?, Logradouro = ?, " +
                    "Numero = ?, CEP = ?, Complemento = ? WHERE ID = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindString(1, entidade.getNome());
        ss.bindString(2, entidade.getTel());
        ss.bindString(3, entidade.getCnpj());
        ss.bindString(4, entidade.getLogradouro());
        ss.bindLong(5, entidade.getNumero());
        ss.bindString(6, entidade.getCep());
        ss.bindString(7, entidade.getComplemento());
        ss.bindLong(8, entidade.getId());
        ss.execute();
        ss.close();
    }

    @Override
    public void excluir(Fornecedor entidade) throws SQLException {
        String sql = "DELETE FROM Fornecedor WHERE ID = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindLong(1, entidade.getId());
        ss.execute();
        ss.close();
    }

    @SuppressLint("Range")
    @Override
    public Fornecedor buscarPorID(Fornecedor entidade) throws SQLException {
        String sql = "SELECT ID, Nome, Telefone, CNPJ, Logradouro, Numero, CEP, Complemento FROM Fornecedor WHERE ID = " + entidade.getId();

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql, null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            entidade.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            entidade.setCnpj(cursor.getString(cursor.getColumnIndex("CNPJ")));
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

    @SuppressLint("Range")
    @Override
    public List<Fornecedor> listar() throws SQLException {
        String sql = "SELECT ID, Nome, Telefone, CNPJ, Logradouro, Numero, CEP, Complemento FROM Fornecedor";

        List<Fornecedor> entidades = new ArrayList<>();
        Cursor cursor = connection.rawQuery(sql, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Fornecedor entidade = new Fornecedor();
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            entidade.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            entidade.setCnpj(cursor.getString(cursor.getColumnIndex("CNPJ")));
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
    public Fornecedor buscarPorChaveSecundaria(Fornecedor entidade) throws SQLException {
        String sql = "SELECT ID, Nome, Telefone, CNPJ, Logradouro, Numero, CEP, Complemento FROM Fornecedor WHERE CNPJ = " + entidade.getCnpj();

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql, null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            entidade.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            entidade.setCnpj(cursor.getString(cursor.getColumnIndex("CNPJ")));
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