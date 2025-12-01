package com.fatec.lanchonetemobile.adapters.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fatec.lanchonetemobile.R;

public class MainActivity extends AppCompatActivity {

    private Button btnProdutos;
    private Button btnFuncionarios;
    private Button btnClientes;
    private Button btnPedidos;
    private ConstraintLayout btnFornecedores;
    private Button btnCargos;
    private Button btnCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        btnProdutos = findViewById(R.id.btnProdutos);
//        btnFuncionarios = findViewById(R.id.btnFuncionarios);
//        btnClientes = findViewById(R.id.btnClientes);
//        btnPedidos = findViewById(R.id.btnPedidos);
        btnFornecedores = findViewById(R.id.btnFornecedores);
//        btnCargos = findViewById(R.id.btnCargos);
//        btnCategorias = findViewById(R.id.btnCategorias);
//
//        btnProdutos.setOnClickListener(e -> {
//            Intent intent = new Intent(this, ProdutoActivity.class);
//            startActivity(intent);
//        });
//
//        btnFuncionarios.setOnClickListener(e -> {
//            Intent intent = new Intent(this, FuncionarioActivity.class);
//            startActivity(intent);
//        });
//
//        btnClientes.setOnClickListener(e -> {
//            Intent intent = new Intent(this, ClienteActivity.class);
//            startActivity(intent);
//        });
//
//        btnPedidos.setOnClickListener(e -> {
//            Intent intent = new Intent(this, PedidoActivity.class);
//            startActivity(intent);
//        });
//
        btnFornecedores.setOnClickListener(e -> {
            Intent intent = new Intent(this, FornecedorActivity.class);
            startActivity(intent);
        });
//
//        btnCargos.setOnClickListener(e -> {
//            Intent intent = new Intent(this, CargoActivity.class);
//            startActivity(intent);
//        });
//
//        btnCategorias.setOnClickListener(e -> {
//            Intent intent = new Intent(this, CategoriaActivity.class);
//            startActivity(intent);
//        });
    }
}