package com.fatec.lanchonetemobile.adapters.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fatec.lanchonetemobile.R;

public class MainActivity extends AppCompatActivity {

    private Button btnCadProduto;
    private Button btnCadFuncionario;
    private Button btnCadCliente;
    private Button btnCadPedido;
    private Button btnCadFornecedor;
    private Button btnCadCargo;
    private Button btnCadCategoria;
    private Button btnGerenciarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCadProduto = findViewById(R.id.btnCadProduto);
        btnCadFuncionario = findViewById(R.id.btnCadFuncionario);
        btnCadCliente = findViewById(R.id.btnCadCliente);
        btnCadPedido = findViewById(R.id.btnCadPedido);
        btnCadFornecedor = findViewById(R.id.btnCadFornecedor);
        btnCadCargo = findViewById(R.id.btnCadCargo);
        btnCadCategoria = findViewById(R.id.btnCadCategoria);
        btnGerenciarPedido = findViewById(R.id.btnGerenciarPedido);

        btnCadProduto.setOnClickListener(e -> {
            Intent intent = new Intent(this, ProdutoActivity.class);
            startActivity(intent);
        });

        btnCadFuncionario.setOnClickListener(e -> {
            Intent intent = new Intent(this, FuncionarioActivity.class);
            startActivity(intent);
        });

        btnCadCliente.setOnClickListener(e -> {
            Intent intent = new Intent(this, ClienteActivity.class);
            startActivity(intent);
        });

        btnCadPedido.setOnClickListener(e -> {
            Intent intent = new Intent(this, PedidoActivity.class);
            startActivity(intent);
        });

        btnCadFornecedor.setOnClickListener(e -> {
            Intent intent = new Intent(this, FornecedorActivity.class);
            startActivity(intent);
        });

        btnCadCargo.setOnClickListener(e -> {
            Intent intent = new Intent(this, CargoActivity.class);
            startActivity(intent);
        });

        btnCadCategoria.setOnClickListener(e -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            startActivity(intent);
        });

        btnGerenciarPedido.setOnClickListener(e -> {
            Intent intent = new Intent(this, GerenciarPedidoActivity.class);
            startActivity(intent);
        });
    }
}