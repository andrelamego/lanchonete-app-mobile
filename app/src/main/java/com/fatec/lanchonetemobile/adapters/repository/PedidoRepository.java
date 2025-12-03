package com.fatec.lanchonetemobile.adapters.repository;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.fatec.lanchonetemobile.application.repository.RepositoryReturn;
import com.fatec.lanchonetemobile.domain.entity.Cliente;
import com.fatec.lanchonetemobile.domain.entity.Pedido;

import java.sql.Date;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository implements RepositoryReturn<Pedido> {
    private SQLiteDatabase connection;

    public PedidoRepository(SQLiteDatabase connection){
        this.connection = connection; 
    }

    @Override
    public int salvar(Pedido entidade) throws SQLException{
        String sql = "INSERT INTO Pedido(ValorTotal, DataPedido, StatusPedido, ID_Cliente) VALUES (?, ?, ?, ?)";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindDouble(1, entidade.getValorTotal());
        ss.bindString(2, entidade.getData().toString());
        ss.bindString(3, entidade.getStatus());
        ss.bindLong(4, entidade.getCliente().getId());
        ss.execute();

        int numPedido = 0;
        Cursor cursor = connection.rawQuery("SELECT last_insert_rowid()", null);
        cursor.moveToFirst();

        if(cursor.isAfterLast()){
            numPedido = cursor.getInt(0);
        }

        cursor.close();
        ss.close();
        return numPedido;
    }   

    @Override
    public void atualizar(Pedido entidade) throws SQLException {
        String sql = "UPDATE Pedido SET ValorTotal = ?, DataPedido = ?, StatusPedido = ?, ID_Cliente = ? WHERE Num_Pedido = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindDouble(1, entidade.getValorTotal());
        ss.bindString(2, entidade.getData().toString());
        ss.bindString(3, entidade.getStatus());
        ss.bindLong(4, entidade.getCliente().getId());
        ss.bindLong(5, entidade.getnPedido());
        ss.execute();
        ss.close();
    }

    //Por conta da regra de negócio, não será possível excluir um pedido
    //Mas o método_ foi implementado para garantir que todas as operações de CRUD
    //estejam presentes na classe de repositório, permitindo assim futuras que alterações
    //na regra de negócio sejam implementadas facilmente.
    @Override
    public void excluir(Pedido entidade) throws SQLException {
        String sql = "DELETE FROM Pedido WHERE Num_Pedido = ?";
        SQLiteStatement ss = connection.compileStatement(sql);
        ss.bindLong(1, entidade.getnPedido());
        ss.execute();
        ss.close();
    }

    @SuppressLint("Range")
    @Override
    public Pedido buscarPorID(Pedido entidade) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.Num_Pedido, p.ValorTotal, p.DataPedido, p.StatusPedido, ");
        sql.append("c.ID, c.Nome, c.Telefone, c.Logradouro, c.Numero, c.CEP, c.Complemento ");
        sql.append("FROM Pedido p INNER JOIN Cliente c ");
        sql.append("ON p.ID_Cliente = c.ID ");
        sql.append("WHERE p.Num_Pedido = ").append(entidade.getnPedido());

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            Cliente cliente = new Cliente();
            cliente.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            cliente.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            cliente.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            cliente.setLogradouro(cursor.getString(cursor.getColumnIndex("Logradouro")));
            cliente.setNumero(cursor.getInt(cursor.getColumnIndex("Numero")));
            cliente.setCep(cursor.getString(cursor.getColumnIndex("CEP")));
            cliente.setComplemento(cursor.getString(cursor.getColumnIndex("Complemento")));

            entidade.setnPedido(cursor.getInt(cursor.getColumnIndex("Num_Pedido")));
            entidade.setValorTotal(cursor.getDouble(cursor.getColumnIndex("ValorTotal")));
            entidade.setData(Date.valueOf(cursor.getString(cursor.getColumnIndex("DataPedido"))));
            entidade.setStatus(cursor.getString(cursor.getColumnIndex("StatusPedido")));
            entidade.setCliente(cliente);

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
    public List<Pedido> listar() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.Num_Pedido, p.ValorTotal, p.DataPedido, p.StatusPedido, ");
        sql.append("c.ID, c.Nome, c.Telefone, c.Logradouro, c.Numero, c.CEP, c.Complemento ");
        sql.append("FROM Pedido p INNER JOIN Cliente c ");
        sql.append("ON p.ID_Cliente = c.ID");

        List<Pedido> entidades = new ArrayList<>();
        Cursor cursor = connection.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Cliente cliente = new Cliente();
            cliente.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            cliente.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            cliente.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            cliente.setLogradouro(cursor.getString(cursor.getColumnIndex("Logradouro")));
            cliente.setNumero(cursor.getInt(cursor.getColumnIndex("Numero")));
            cliente.setCep(cursor.getString(cursor.getColumnIndex("CEP")));
            cliente.setComplemento(cursor.getString(cursor.getColumnIndex("Complemento")));

            Pedido entidade = new Pedido();
            entidade.setnPedido(cursor.getInt(cursor.getColumnIndex("Num_Pedido")));
            entidade.setValorTotal(cursor.getDouble(cursor.getColumnIndex("ValorTotal")));
            entidade.setData(Date.valueOf(cursor.getString(cursor.getColumnIndex("DataPedido"))));
            entidade.setStatus(cursor.getString(cursor.getColumnIndex("StatusPedido")));
            entidade.setCliente(cliente);

            entidades.add(entidade);
            cursor.moveToNext();
        }

        cursor.close();
        return entidades;
    }

    @SuppressLint("Range")
    @Override
    public Pedido buscarPorChaveSecundaria(Pedido entidade) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.Num_Pedido, p.ValorTotal, p.DataPedido, p.StatusPedido, ");
        sql.append("c.ID, c.Nome, c.Telefone, c.Logradouro, c.Numero, c.CEP, c.Complemento ");
        sql.append("FROM Pedido p INNER JOIN Cliente c ");
        sql.append("ON p.ID_Cliente = c.ID ");
        sql.append("WHERE p.DataPedido = ").append(entidade.getData());

        int cont = 0;
        Cursor cursor = connection.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            Cliente cliente = new Cliente();
            cliente.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            cliente.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
            cliente.setTel(cursor.getString(cursor.getColumnIndex("Telefone")));
            cliente.setLogradouro(cursor.getString(cursor.getColumnIndex("Logradouro")));
            cliente.setNumero(cursor.getInt(cursor.getColumnIndex("Numero")));
            cliente.setCep(cursor.getString(cursor.getColumnIndex("CEP")));
            cliente.setComplemento(cursor.getString(cursor.getColumnIndex("Complemento")));

            entidade.setnPedido(cursor.getInt(cursor.getColumnIndex("Num_Pedido")));
            entidade.setValorTotal(cursor.getDouble(cursor.getColumnIndex("ValorTotal")));
            entidade.setData(Date.valueOf(cursor.getString(cursor.getColumnIndex("DataPedido"))));
            entidade.setStatus(cursor.getString(cursor.getColumnIndex("StatusPedido")));
            entidade.setCliente(cliente);

            cont++;
        }

        if(cont == 0){
            entidade = null;
        }

        cursor.close();
        return entidade;
    }
}
