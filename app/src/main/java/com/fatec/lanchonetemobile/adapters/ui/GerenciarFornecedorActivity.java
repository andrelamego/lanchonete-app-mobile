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
import com.fatec.lanchonetemobile.application.dto.FornecedorDTO;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class GerenciarFornecedorActivity extends AppCompatActivity {
    private MaterialCardView btnAddFornecedor;
    private MaterialCardView btnBuscarFornecedor;

    private ImageView ivBack;

    private RecyclerView rvFornecedores;

    private PedidoAdapter adapter;
    private List<FornecedorDTO> fornecedores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_fornecedor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gerenciarFornecedorActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAddFornecedor = findViewById(R.id.btnAddForn);
        btnBuscarFornecedor = findViewById(R.id.btnBuscarForn);

        rvFornecedores = findViewById(R.id.rvListaGerenciarForn);

        ivBack = findViewById(R.id.ivBackGerenciarForn);
        ivBack.setOnClickListener(e -> {
            finish();
        });
    }
}
