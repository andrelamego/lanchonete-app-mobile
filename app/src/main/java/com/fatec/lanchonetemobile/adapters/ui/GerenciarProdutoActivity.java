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
import com.fatec.lanchonetemobile.application.dto.ProdutoDTO;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class GerenciarProdutoActivity extends AppCompatActivity {

    private MaterialCardView btnAddProduto;
    private MaterialCardView btnBuscarProduto;

    private ImageView ivBack;

    private RecyclerView rvProdutos;

    private PedidoAdapter adapter;
    private List<ProdutoDTO> produtos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_produto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gerenciarProdutoActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAddProduto = findViewById(R.id.btnAddProduto);
        btnBuscarProduto = findViewById(R.id.btnBuscarProduto);

        rvProdutos = findViewById(R.id.rvListaGerenciarProduto);

        ivBack = findViewById(R.id.ivBackGerenciarProduto);
        ivBack.setOnClickListener(e -> {
            finish();
        });
    }
}
