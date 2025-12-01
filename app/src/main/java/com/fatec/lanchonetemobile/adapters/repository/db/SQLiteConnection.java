package com.fatec.lanchonetemobile.adapters.repository.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class SQLiteConnection extends SQLiteOpenHelper implements IDBConnection {
    private static final String DATABASE = "Lanchonete.db";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE_CARGO =
            "CREATE TABLE Cargo( " +
                    "ID INT NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "Nome VARCHAR(50) NOT NULL, " +
                    "Salario DECIMAL(7,2) NOT NULL CHECK(Salario > 0), " +
                    "Descricao VARCHAR(100) NOT NULL " +
                    ")";
    private static final String CREATE_TABLE_FUNCIONARIO =
            "CREATE TABLE Funcionario( " +
                    "ID INT NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "Nome VARCHAR(100) NOT NULL, " +
                    "Telefone VARCHAR(11) CHECK(LEN(Telefone) >= 10), " +
                    "Email VARCHAR(200) NOT NULL, " +
                    "DataContrato DATE NOT NULL DEFAULT(GETDATE()), " +
                    "ID_Cargo INT NOT NULL, " +
                    "FOREIGN KEY(ID_Cargo) REFERENCES Cargo(ID) " +
                    ")";
    private static final String CREATE_TABLE_CLIENTE =
            "CREATE TABLE Cliente( " +
                    "ID INT NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "Nome VARCHAR(100) NOT NULL, " +
                    "Telefone VARCHAR(11) NOT NULL CHECK(LEN(Telefone) >=10), " +
                    "CPF CHAR(11) NOT NULL CHECK(LEN(CPF) = 11), " +
                    "Logradouro VARCHAR(200) NOT NULL, " +
                    "Numero INT NOT NULL CHECK(Numero >= 0), " +
                    "CEP CHAR(8) NOT NULL CHECK(LEN(CEP) = 8), " +
                    "Complemento VARCHAR(255) " +
                    ")";
    private static final String CREATE_TABLE_PEDIDO =
            "CREATE TABLE Pedido( " +
                    "Num_Pedido INT NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "ValorTotal DECIMAL(7,2) NOT NULL CHECK(ValorTotal > 0), " +
                    "DataPedido DATE NOT NULL DEFAULT(GETDATE()), " +
                    "StatusPedido VARCHAR(20) NOT NULL DEFAULT('Em Preparo'), " +
                    "ID_Cliente INT NOT NULL, " +
                    "FOREIGN KEY(ID_Cliente) REFERENCES Cliente(ID) " +
                    ")";
    private static final String CREATE_TABLE_CATEGORIA =
            "CREATE TABLE Categoria( " +
                    "ID INT NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "Nome VARCHAR(50) NOT NULL, " +
                    "Descricao VARCHAR(100) NOT NULL " +
                    ")";
    private static final String CREATE_TABLE_FORNECEDOR =
            "CREATE TABLE Fornecedor( " +
                    "ID INT NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "Nome VARCHAR(100) NOT NULL, " +
                    "Telefone VARCHAR(11) NOT NULL CHECK(LEN(Telefone) >=10), " +
                    "CNPJ CHAR(14) NOT NULL CHECK(LEN(CNPJ) = 14), " +
                    "Logradouro VARCHAR(200) NOT NULL, " +
                    "Numero INT NOT NULL CHECK(Numero >= 0), " +
                    "CEP CHAR(8) NOT NULL CHECK(LEN(CEP) = 8), " +
                    "Complemento VARCHAR(255) " +
                    ")";
    private static final String CREATE_TABLE_PRODUTO =
            "CREATE TABLE Produto( " +
                    "ID INT NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "Nome VARCHAR(50) NOT NULL, " +
                    "QtdEstoque INT NOT NULL CHECK(QtdEstoque >= 0), " +
                    "ValorUnit DECIMAL(7,2) NOT NULL CHECK(ValorUnit > 0), " +
                    "ID_Categoria INT NOT NULL, " +
                    "ID_Fornecedor INT NOT NULL, " +
                    "PRIMARY KEY(ID), " +
                    "FOREIGN KEY(ID_Categoria) REFERENCES Categoria(ID), " +
                    "FOREIGN KEY(ID_Fornecedor) REFERENCES Fornecedor(ID) " +
                    ")";
    private static final String CREATE_TABLE_ITEM_PEDIDO =
            "CREATE TABLE Item_Pedido( " +
                    "Num_Pedido INT NOT NULL, " +
                    "ID_Produto INT NOT NULL, " +
                    "Qtd INT NOT NULL, " +
                    "ValorUnit DECIMAL(7,2) NOT NULL, " +
                    "ValorTotalItem DECIMAL(7,2) NOT NULL, " +
                    "PRIMARY KEY(Num_Pedido, ID_Produto), " +
                    "FOREIGN KEY(Num_Pedido) REFERENCES Pedido(Num_Pedido), " +
                    "FOREIGN KEY(ID_Produto) REFERENCES Produto(ID) " +
                    ")";
    public SQLiteConnection(){
        super(null, DATABASE, null, DB_VERSION);
    }

    @Override
    public SQLiteDatabase getConnection() throws ClassNotFoundException, SQLException {
        return this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CARGO);
        db.execSQL(CREATE_TABLE_FUNCIONARIO);
        db.execSQL(CREATE_TABLE_CLIENTE);
        db.execSQL(CREATE_TABLE_PEDIDO);
        db.execSQL(CREATE_TABLE_CATEGORIA);
        db.execSQL(CREATE_TABLE_FORNECEDOR);
        db.execSQL(CREATE_TABLE_PRODUTO);
        db.execSQL(CREATE_TABLE_ITEM_PEDIDO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS Cargo");
            db.execSQL("DROP TABLE IF EXISTS Funcionario");
            db.execSQL("DROP TABLE IF EXISTS Cliente");
            db.execSQL("DROP TABLE IF EXISTS Pedido");
            db.execSQL("DROP TABLE IF EXISTS Categoria");
            db.execSQL("DROP TABLE IF EXISTS Fornecedor");
            db.execSQL("DROP TABLE IF EXISTS Produto");
            db.execSQL("DROP TABLE IF EXISTS Item_Pedido");
            onCreate(db);
        }
    }
}
