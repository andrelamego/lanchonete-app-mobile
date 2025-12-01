package com.fatec.lanchonetemobile.adapters.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fatec.lanchonetemobile.R;

public class FuncionarioActivity extends AppCompatActivity {

    private EditText etID;
    private EditText etNome;
    private EditText etTelefone;
    private EditText etEmail;
    private EditText etDataAdmissao;
    private Spinner spCargo;

    private Button btnSalvar;
    private ImageView imgBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_funcionario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.funcionarioActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgBackArrow = findViewById(R.id.backArrowFuncionario);
        imgBackArrow.setOnClickListener(e -> {
            finish();
        });

        btnSalvar = findViewById(R.id.btnSalvarFuncionario);
    }
}
