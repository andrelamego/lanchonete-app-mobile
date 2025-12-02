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
import com.fatec.lanchonetemobile.application.dto.FuncionarioDTO;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class GerenciarFuncionarioActivity extends AppCompatActivity {
    private MaterialCardView btnAddFuncionario;
    private MaterialCardView btnBuscarFuncionario;

    private ImageView ivBack;

    private RecyclerView rvFuncionarios;

    private PedidoAdapter adapter;
    private List<FuncionarioDTO> funcionarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_funcionario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gerenciarFuncionarioActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAddFuncionario = findViewById(R.id.btnAddFunc);
        btnBuscarFuncionario = findViewById(R.id.btnBuscarFunc);

        rvFuncionarios = findViewById(R.id.rvListaGerenciarFunc);

        ivBack = findViewById(R.id.ivBackGerenciarFunc);
        ivBack.setOnClickListener(e -> {
            finish();
        });
    }
}
