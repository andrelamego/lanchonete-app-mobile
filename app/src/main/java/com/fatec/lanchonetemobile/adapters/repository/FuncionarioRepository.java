package com.fatec.lanchonetemobile.adapters.repository;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.fatec.lanchonetemobile.application.repository.RepositoryNoReturn;
import com.fatec.lanchonetemobile.domain.entity.Cargo;
import com.fatec.lanchonetemobile.domain.entity.Funcionario;

import java.sql.Date;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepository implements RepositoryNoReturn<Funcionario> {
    private SQLiteDatabase connection;

    public FuncionarioRepository(SQLiteDatabase connection){
        this.connection = connection; 
    }

    @Override
    public void salvar(Funcionario entidade) throws SQLException {
        String sql = "INSERT INTO Funcionario(Nome, Telefone, Email , DataContrato, ID_Cargo) VALUES (?, ?, ?, ?, ?)";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindString(1, entidade.getNome());
        ss.bindString(2, entidade.getTel());
        ss.bindString(3, entidade.getEmail());
        ss.bindString(4, entidade.getDataContrato().toString());
        ss.bindLong(5, entidade.getCargo().getId());
        ss.execute();
        ss.close();
    }

    @Override
    public void atualizar(Funcionario entidade) throws SQLException {
        String sql = "UPDATE Funcionario SET Nome = ?, Telefone = ?, Email = ?, DataContrato = ?, ID_Cargo = ? WHERE ID = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindString(1, entidade.getNome());
        ss.bindString(2, entidade.getTel());
        ss.bindString(3, entidade.getEmail());
        ss.bindString(4, entidade.getDataContrato().toString());
        ss.bindLong(5, entidade.getCargo().getId());
        ss.bindLong(6, entidade.getId());
        ss.execute();
        ss.close();
    }

    @Override
    public void excluir(Funcionario entidade) throws SQLException {
        String sql = "DELETE FROM Funcionario WHERE ID = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindLong(1, entidade.getId());
        ss.execute();
        ss.close();
    }

    @SuppressLint("Range")
    @Override
    public Funcionario buscarPorID(Funcionario entidade) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT f.ID AS ID_Func, f.Nome AS Nome_Func, f.Telefone, f.Email, f.DataContrato, ");
        sql.append("c.ID AS ID_Cargo, c.Nome AS Nome_Cargo, c.Salario, c.Descricao ");
        sql.append("FROM Funcionario f INNER JOIN Cargo c ");
        sql.append("ON f.ID_Cargo = c.ID ");
        sql.append("WHERE f.ID = ").append(entidade.getId());

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            Cargo cargo = new Cargo();
            cargo.setId(cursor.getInt(cursor.getColumnIndex("ID_Cargo")));
            cargo.setNome(cursor.getString(cursor.getColumnIndex("Nome_Cargo")));
            cargo.setSalario(cursor.getDouble(cursor.getColumnIndex("Salario")));
            cargo.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID_Func")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome_Func")));
            entidade.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            entidade.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
            entidade.setDataContrato(Date.valueOf(cursor.getString(cursor.getColumnIndex("DataContrato"))));
            entidade.setCargo(cargo);
            
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
    public List<Funcionario> listar() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT f.ID AS ID_Func, f.Nome AS Nome_Func, f.Telefone, f.Email, f.DataContrato, ");
        sql.append("c.ID AS ID_Cargo, c.Nome AS Nome_Cargo, c.Salario, c.Descricao ");
        sql.append("FROM Funcionario f INNER JOIN Cargo c ");
        sql.append("ON f.ID_Cargo = c.ID");

        List<Funcionario> entidades = new ArrayList<>();
        Cursor cursor = connection.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Cargo cargo = new Cargo();
            cargo.setId(cursor.getInt(cursor.getColumnIndex("ID_Cargo")));
            cargo.setNome(cursor.getString(cursor.getColumnIndex("Nome_Cargo")));
            cargo.setSalario(cursor.getDouble(cursor.getColumnIndex("Salario")));
            cargo.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

            Funcionario entidade = new Funcionario();
            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID_Func")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome_Func")));
            entidade.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            entidade.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
            entidade.setDataContrato(Date.valueOf(cursor.getString(cursor.getColumnIndex("DataContrato"))));
            entidade.setCargo(cargo);

            entidades.add(entidade);
            cursor.moveToNext();
        }

        cursor.close();
        return entidades;
    }

    @SuppressLint("Range")
    @Override
    public Funcionario buscarPorChaveSecundaria(Funcionario entidade) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT f.ID AS ID_Func, f.Nome AS Nome_Func, f.Telefone, f.Email, f.DataContrato, ");
        sql.append("c.ID AS ID_Cargo, c.Nome AS Nome_Cargo, c.Salario, c.Descricao ");
        sql.append("FROM Funcionario f INNER JOIN Cargo c ");
        sql.append("ON f.ID_Cargo = c.ID ");
        sql.append("WHERE f.Email = ").append(entidade.getEmail());

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            Cargo cargo = new Cargo();
            cargo.setId(cursor.getInt(cursor.getColumnIndex("ID_Cargo")));
            cargo.setNome(cursor.getString(cursor.getColumnIndex("Nome_Cargo")));
            cargo.setSalario(cursor.getDouble(cursor.getColumnIndex("Salario")));
            cargo.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));

            entidade.setId(cursor.getInt(cursor.getColumnIndex("ID_Func")));
            entidade.setNome(cursor.getString(cursor.getColumnIndex("Nome_Func")));
            entidade.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            entidade.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
            entidade.setDataContrato(Date.valueOf(cursor.getString(cursor.getColumnIndex("DataContrato"))));
            entidade.setCargo(cargo);

            cont++;
        }

        if(cont == 0){
            entidade = null;
        }

        cursor.close();
        return entidade;
    }
}
