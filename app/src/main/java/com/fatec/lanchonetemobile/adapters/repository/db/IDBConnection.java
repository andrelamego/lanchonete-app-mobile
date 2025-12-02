package com.fatec.lanchonetemobile.adapters.repository.db;

import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDBConnection {

    /**
     * Estabelece e retorna uma conexão com o banco de dados.
     *
     * @return um objeto {@link Connection} representando a conexão com o banco.
     * @throws ClassNotFoundException se a classe do driver do banco de dados não puder ser localizada.
     * @throws SQLException           se ocorrer um erro no acesso ao banco de dados ou se a conexão for inválida.
     */
    SQLiteDatabase getConnection();
}
