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
import com.fatec.lanchonetemobile.application.dto.ClienteDTO;
import com.fatec.lanchonetemobile.application.exception.ClienteInvalidoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.config.AppBuilder;

import java.sql.SQLException;

public class FormClienteActivity extends AppCompatActivity {
    private ImageView ivBack;

    private EditText etNome;
    private EditText etTelefone;
    private EditText etCPF;
    private EditText etLogradouro;
    private EditText etNumero;
    private EditText etCEP;
    private EditText etComplemento;

    int clienteId = 0;
    private TextView pageTitle;
    private Button btnSalvar;

    private CadastroFacade cadastroFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_form_cliente), (v, insets) -> {
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

        pageTitle = findViewById(R.id.pageTitleFormCliente);

        etNome = findViewById(R.id.etNomeFormCliente);
        etTelefone = findViewById(R.id.etTelefoneFormCliente);
        etCPF = findViewById(R.id.etCPFFormCliente);
        etLogradouro = findViewById(R.id.etLogradouroFormCliente);
        etNumero = findViewById(R.id.etNumeroFormCliente);
        etCEP = findViewById(R.id.etCEPFormCliente);
        etComplemento = findViewById(R.id.etComplementoFormCliente);

        ivBack = findViewById(R.id.ivBackFormCliente);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        btnSalvar = findViewById(R.id.btnSalvarFormCliente);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("CLIENTE_ID")) {
            clienteId = intent.getIntExtra("CLIENTE_ID", 0);

            try {
                ClienteDTO cliente = cadastroFacade.buscarCliente(clienteId);

                etNome.setText(cliente.getNome());
                etTelefone.setText(cliente.getTel());
                etCPF.setText(cliente.getCpf());
                etLogradouro.setText(cliente.getLogradouro());
                etNumero.setText(String.valueOf(cliente.getNumero()));
                etCEP.setText(cliente.getCep());
                etComplemento.setText(cliente.getComplemento());

                pageTitle.setText(R.string.AtualizaCliente);
                btnSalvar.setText(R.string.botaoEditarCliente);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        btnSalvar.setOnClickListener(e -> {
            if(clienteId == 0) {
                ClienteDTO cliente = new ClienteDTO(
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
                    cadastroFacade.novoCliente(cliente);
                    mostrarAlerta("Cliente cadastrado!", "Cliente cadastrado com sucesso!");
                } catch (ClienteInvalidoException ex) {
                    mostrarAlerta("Cliente inválido!", "Cliente já está cadastrado no sistema.");
                } catch (SQLException sql) {
                    mostrarAlerta("Erro ao cadastrar Cliente!", "Erro ao cadastrar Cliente no sistema.");
                }
            }
            else{
                ClienteDTO cliente = new ClienteDTO(
                        clienteId,
                        etNome.getText().toString(),
                        etTelefone.getText().toString(),
                        etCPF.getText().toString(),
                        etLogradouro.getText().toString(),
                        Integer.parseInt(etNumero.getText().toString()),
                        etCEP.getText().toString(),
                        etComplemento.getText().toString()
                );

                try {
                    cadastroFacade.atualizarCliente(cliente);
                    mostrarAlerta("Cliente atualizado!", "Cliente atualizado com sucesso!");
                } catch (ClienteInvalidoException ex) {
                    mostrarAlerta("Cliente inválido!", "Cliente já está atualizado no sistema.");
                } catch (SQLException sql) {
                    mostrarAlerta("Erro ao cadastrar Cliente!", "Erro ao cadastrar Cliente no sistema.");
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
