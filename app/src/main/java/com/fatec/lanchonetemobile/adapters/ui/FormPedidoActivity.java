package com.fatec.lanchonetemobile.adapters.ui;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.ItemPedidoDTO;

import java.util.List;

public class FormPedidoActivity extends AppCompatActivity {

    private ImageView btnBack;

    private AutoCompleteTextView etCliente;
    private EditText etData;
    private Spinner spStatus;

    private RecyclerView rvItemPedido;
    private List<ItemPedidoDTO> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_pedido);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.formPedidoActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.ivBackFormPedido);
        btnBack.setOnClickListener(e -> {
            finish();
        });

        etCliente = findViewById(R.id.etClienteFormPedido);
        etData = findViewById(R.id.etDataFormPedido);
        spStatus = findViewById(R.id.spStatusFormPedido);

        rvItemPedido = findViewById(R.id.rvItemPedido);

    }
}
