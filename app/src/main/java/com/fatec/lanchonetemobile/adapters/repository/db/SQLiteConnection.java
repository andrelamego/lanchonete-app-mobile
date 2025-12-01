package com.fatec.lanchonetemobile.adapters.repository.db;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteConnection implements IDBConnection{
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}
