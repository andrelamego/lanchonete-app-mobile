package com.fatec.lanchonetemobile.adapters.ui;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.adapters.adapter.PedidoAdapter;
import com.fatec.lanchonetemobile.application.dto.ClienteDTO;
import com.fatec.lanchonetemobile.application.dto.ProdutoDTO;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class GerenciarClienteActivity extends AppCompatActivity {
    private MaterialCardView btnAddCliente;
    private MaterialCardView btnBuscarCliente;

    private ImageView ivBack;

    private RecyclerView rvClientes;

    private PedidoAdapter adapter;
    private List<ClienteDTO> clientes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gerenciarClienteActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAddCliente = findViewById(R.id.btnAddCliente);
        btnBuscarCliente = findViewById(R.id.btnBuscarCliente);

        rvClientes = findViewById(R.id.rvListaGerenciarCliente);

        ivBack = findViewById(R.id.ivBackGerenciarCliente);
        ivBack.setOnClickListener(e -> {
            finish();
        });
    }
}
