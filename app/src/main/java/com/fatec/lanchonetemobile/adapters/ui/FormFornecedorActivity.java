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
import com.fatec.lanchonetemobile.application.dto.FornecedorDTO;
import com.fatec.lanchonetemobile.application.exception.FornecedorInvalidoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.config.AppBuilder;

import java.sql.SQLException;

public class FormFornecedorActivity extends AppCompatActivity {
    private ImageView ivBack;

    private EditText etNome;
    private EditText etTelefone;
    private EditText etCPF;
    private EditText etLogradouro;
    private EditText etNumero;
    private EditText etCEP;
    private EditText etComplemento;

    int fornecedorId = 0;
    private TextView pageTitle;
    private Button btnSalvar;

    private CadastroFacade cadastroFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_fornecedor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_form_fornecedor), (v, insets) -> {
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

        pageTitle = findViewById(R.id.pageTitleFormFornecedor);

        etNome = findViewById(R.id.etNomeFormFornecedor);
        etTelefone = findViewById(R.id.etTelefoneFormFornecedor);
        etCPF = findViewById(R.id.etCPFFormFornecedor);
        etLogradouro = findViewById(R.id.etLogradouroFormFornecedor);
        etNumero = findViewById(R.id.etNumeroFormFornecedor);
        etCEP = findViewById(R.id.etCEPFormFornecedor);
        etComplemento = findViewById(R.id.etComplementoFormFornecedor);

        ivBack = findViewById(R.id.ivBackFormFornecedor);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        btnSalvar = findViewById(R.id.btnSalvarFormFornecedor);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("FORNECEDOR_ID")) {
            fornecedorId = intent.getIntExtra("FORNECEDOR_ID", 0);

            try {
                FornecedorDTO fornecedor = cadastroFacade.buscarFornecedor(fornecedorId);

                etNome.setText(fornecedor.getNome());
                etTelefone.setText(fornecedor.getTel());
                etCPF.setText(fornecedor.getCnpj());
                etLogradouro.setText(fornecedor.getLogradouro());
                etNumero.setText(fornecedor.getNumero());
                etCEP.setText(fornecedor.getCep());
                etComplemento.setText(fornecedor.getComplemento());

                pageTitle.setText(R.string.AtualizaFornecedor);
                btnSalvar.setText(R.string.botaoEditarFornecedor);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        btnSalvar.setOnClickListener(e -> {
            if(fornecedorId == 0) {
                FornecedorDTO fornecedor = new FornecedorDTO(
                        0,
                        etNome.getText().toString(),
                        etTelefone.getText().toString(),
                        etCPF.getText().toString(),
                        etLogradouro.getText().toString(),
                        Integer.parseInt(etNumero.getText().toString()),
                        etCEP.getText().toString(),
                        etComplemento.getText().toString()
                );

                try {
                    cadastroFacade.novoFornecedor(fornecedor);
                    mostrarAlerta("Fornecedor cadastrado!", "Fornecedor cadastrado com sucesso!");
                } catch (FornecedorInvalidoException ex) {
                    mostrarAlerta("Fornecedor inválido!", "Fornecedor já está cadastrado no sistema.");
                } catch (SQLException sql) {
                    mostrarAlerta("Erro ao cadastrar Fornecedor!", "Erro ao cadastrar Fornecedor no sistema.");
                }
            }
            else{
                FornecedorDTO fornecedor = new FornecedorDTO(
                        0,
                        etNome.getText().toString(),
                        etTelefone.getText().toString(),
                        etCPF.getText().toString(),
                        etLogradouro.getText().toString(),
                        Integer.parseInt(etNumero.getText().toString()),
                        etCEP.getText().toString(),
                        etComplemento.getText().toString()
                );

                try {
                    cadastroFacade.atualizarFornecedor(fornecedor);
                    mostrarAlerta("Fornecedor atualizado!", "Fornecedor atualizado com sucesso!");
                } catch (FornecedorInvalidoException ex) {
                    mostrarAlerta("Fornecedor inválido!", "Fornecedor já está atualizado no sistema.");
                } catch (SQLException sql) {
                    mostrarAlerta("Erro ao cadastrar Fornecedor!", "Erro ao cadastrar Fornecedor no sistema.");
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
