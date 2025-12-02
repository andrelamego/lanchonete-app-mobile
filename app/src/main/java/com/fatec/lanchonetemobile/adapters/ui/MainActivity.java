package com.fatec.lanchonetemobile.adapters.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fatec.lanchonetemobile.R;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout btnProdutos;
    private ConstraintLayout btnFuncionarios;
    private ConstraintLayout btnClientes;
    private ConstraintLayout btnPedidos;
    private ConstraintLayout btnFornecedores;
    private ConstraintLayout btnCargos;
    private ConstraintLayout btnCategorias;

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

        btnProdutos = findViewById(R.id.btnProdutos);
        btnFuncionarios = findViewById(R.id.btnFuncionarios);
        btnClientes = findViewById(R.id.btnClientes);
        btnPedidos = findViewById(R.id.btnPedidos);
        btnFornecedores = findViewById(R.id.btnFornecedores);
        btnCargos = findViewById(R.id.btnCargos);
        btnCategorias = findViewById(R.id.btnCategorias);

        btnProdutos.setOnClickListener(e -> {
            Intent intent = new Intent(this, GerenciarProdutoActivity.class);
            startActivity(intent);
        });

        btnFuncionarios.setOnClickListener(e -> {
            Intent intent = new Intent(this, GerenciarFuncionarioActivity.class);
            startActivity(intent);
        });

        btnClientes.setOnClickListener(e -> {
            Intent intent = new Intent(this, GerenciarClienteActivity.class);
            startActivity(intent);
        });

        btnPedidos.setOnClickListener(e -> {
            Intent intent = new Intent(this, GerenciarPedidoActivity.class);
            startActivity(intent);
        });

        btnFornecedores.setOnClickListener(e -> {
            Intent intent = new Intent(this, GerenciarFornecedorActivity.class);
            startActivity(intent);
        });

        btnCargos.setOnClickListener(e -> {
            Intent intent = new Intent(this, GerenciarCargoActivity.class);
            startActivity(intent);
        });

        btnCategorias.setOnClickListener(e -> {
            Intent intent = new Intent(this, GerenciarCategoriaActivity.class);
            startActivity(intent);
        });
    }
}