package com.fatec.lanchonetemobile.adapters.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fatec.lanchonetemobile.LanchoneteApp;
import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.CategoriaDTO;
import com.fatec.lanchonetemobile.application.exception.CategoriaInvalidaException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.config.AppBuilder;

import java.sql.SQLException;

public class FormCategoriaActivity extends AppCompatActivity {
    private ImageView ivBack;

    private EditText etNome;
    private EditText etDescricao;

    int categoriaId = 0;
    private TextView pageTitle;
    private Button btnSalvar;

    private CadastroFacade cadastroFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_categoria);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_form_categoria), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //--------------------INJETA DEPENDENCIAS--------------------
        LanchoneteApp app = (LanchoneteApp) getApplication();
        AppBuilder builder = app.getAppBuilder();

        if (builder == null) {
            throw new IllegalStateException("AppBuilder está null. Verifique LanchoneteApp.onCreate e o AndroidManifest.");
        }

        cadastroFacade = builder.getCadastroFacade();
        //-----------------------------------------------------------

        pageTitle = findViewById(R.id.pageTitleFormCateg);

        ivBack = findViewById(R.id.ivBackFormCateg);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        etNome = findViewById(R.id.etNomeFormCateg);
        etDescricao = findViewById(R.id.etDescricaoFormCateg);

        btnSalvar = findViewById(R.id.btnSalvarCateg);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("CATEGORIA_ID")) {
            categoriaId = intent.getIntExtra("CATEGORIA_ID", 0);

            try {
                CategoriaDTO categoria = cadastroFacade.buscarCategoria(categoriaId);

                etNome.setText(categoria.getNome());
                etDescricao.setText(categoria.getDescricao());

                pageTitle.setText(R.string.AtualizaCategoria);
                btnSalvar.setText(R.string.botaoEditarCategoria);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        btnSalvar.setOnClickListener(e -> {
            if(categoriaId == 0) {
                CategoriaDTO categoria = new CategoriaDTO(
                        0,
                        etNome.getText().toString(),
                        etDescricao.getText().toString()
                );

                try {
                    cadastroFacade.novaCategoria(categoria);
                    mostrarAlerta("Categoria cadastrada!", "Categoria cadastrada com sucesso!");
                } catch (CategoriaInvalidaException ex) {
                    mostrarAlerta("Categoria inválida!", "Categoria já está cadastrada no sistema.");
                } catch (SQLException sql) {
                    mostrarAlerta("Erro ao cadastrar Categoria!", "Erro ao cadastrar Categoria no sistema.");
                }
            }
            else{
                CategoriaDTO categoria = new CategoriaDTO(
                        categoriaId,
                        etNome.getText().toString(),
                        etDescricao.getText().toString()
                );

                try {
                    cadastroFacade.atualizarCategoria(categoria);
                    mostrarAlerta("Categoria atualizada!", "Categoria atualizada com sucesso!");
                } catch (SQLException sql) {
                    mostrarAlerta("Erro ao atualizar categoria!", "Erro ao atualizar categoria no sistema.");
                }
            }
        });
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setMessage(mensagem)
                .setPositiveButton("OK", null)
                .show();
    }
}
