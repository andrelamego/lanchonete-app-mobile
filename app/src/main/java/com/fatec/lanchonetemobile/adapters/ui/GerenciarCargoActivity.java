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
import com.fatec.lanchonetemobile.application.dto.CargoDTO;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class GerenciarCargoActivity extends AppCompatActivity {
    private MaterialCardView btnAddCargo;
    private MaterialCardView btnBuscarCargo;

    private ImageView ivBack;

    private RecyclerView rvCargos;

    private PedidoAdapter adapter;
    private List<CargoDTO> cargos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_cargo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gerenciarCargoActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAddCargo = findViewById(R.id.btnAddCargo);
        btnBuscarCargo = findViewById(R.id.btnBuscarCargo);

        rvCargos = findViewById(R.id.rvListaGerenciarCargo);

        ivBack = findViewById(R.id.ivBackGerenciarCargo);
        ivBack.setOnClickListener(e -> {
            finish();
        });
    }
}
