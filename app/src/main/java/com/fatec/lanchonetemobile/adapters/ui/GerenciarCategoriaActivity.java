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
import com.fatec.lanchonetemobile.application.dto.CategoriaDTO;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class GerenciarCategoriaActivity extends AppCompatActivity {
    private MaterialCardView btnAddCategoria;
    private MaterialCardView btnBuscarCategoria;

    private ImageView ivBack;

    private RecyclerView rvCategorias;

    private PedidoAdapter adapter;
    private List<CategoriaDTO> categorias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_categoria);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gerenciarCategoriaActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAddCategoria = findViewById(R.id.btnAddCateg);
        btnBuscarCategoria = findViewById(R.id.btnBuscarCateg);

        rvCategorias = findViewById(R.id.rvListaGerenciarCateg);

        ivBack = findViewById(R.id.ivBackGerenciarCateg);
        ivBack.setOnClickListener(e -> {
            finish();
        });
    }
}
