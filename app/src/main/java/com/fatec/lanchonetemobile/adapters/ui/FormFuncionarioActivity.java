package com.fatec.lanchonetemobile.adapters.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fatec.lanchonetemobile.LanchoneteApp;
import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.CargoDTO;
import com.fatec.lanchonetemobile.application.dto.FuncionarioDTO;
import com.fatec.lanchonetemobile.application.exception.CargoInvalidoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.application.mapper.CargoMapper;
import com.fatec.lanchonetemobile.config.AppBuilder;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class FormFuncionarioActivity extends AppCompatActivity {

    private ImageView ivBack;

    private EditText etNome;
    private EditText etTelefone;
    private EditText etData;
    private EditText etEmail;
    private Spinner spCargos;

    private TextView pageTitle;
    private Button btnSalvar;

    private int funcionarioId = 0;
    private List<CargoDTO> cargos;
    private CadastroFacade cadastroFacade;
    private CargoMapper cargoMapper = new CargoMapper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_funcionario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_form_funcionario), (v, insets) -> {
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

        pageTitle = findViewById(R.id.pageTitleFormFunc);

        etNome = findViewById(R.id.etNomeFormFunc);
        etTelefone = findViewById(R.id.etTelefoneFormFunc);
        etData = findViewById(R.id.etDataFormFunc);
        etEmail = findViewById(R.id.etEmailFormFunc);
        spCargos = findViewById(R.id.spCargoFormFunc);

        ivBack = findViewById(R.id.ivBackFormFunc);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        btnSalvar = findViewById(R.id.btnSalvarFormFunc);
        btnSalvar.setOnClickListener(e -> {
            try {

                // Formato que o usuário digita (ajuste conforme seu caso)
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                // Formato que o SQL espera
                SimpleDateFormat formatoSql = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                // Converte o texto para Date e depois para o formato SQL
                java.util.Date dataUtil = formatoEntrada.parse(etData.getText().toString());
                String dataFormatada = formatoSql.format(dataUtil);

                FuncionarioDTO funcionario = new FuncionarioDTO(
                        0,
                        etNome.getText().toString(),
                        etTelefone.getText().toString(),
                        etEmail.getText().toString(),
                        Date.valueOf(dataFormatada),
                        cargoMapper.toEntity((CargoDTO) spCargos.getSelectedItem())
                );

                try {
                    cadastroFacade.novoFuncionario(funcionario);
                    mostrarAlerta("Cargo cadastrado!", "Cargo cadastrado com sucesso!");
                } catch (CargoInvalidoException ex) {
                    mostrarAlerta("Cargo inválido!", "Cargo já está cadastrado no sistema.");
                } catch (SQLException sql) {
                    mostrarAlerta("Erro ao cadastrar cargo!", "Erro ao cadastrar cargo no sistema.");
                }
            } catch (ParseException parse) {
                parse.printStackTrace();
                Toast.makeText(this, "Data inválida! Use o formato dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            }
        });

        carregarSpinnerCargos();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("FUNCIONARIO_ID")) {
            funcionarioId = intent.getIntExtra("FUNCIONARIO_ID", 0);

            try {
                FuncionarioDTO funcionario = cadastroFacade.buscarFuncionario(funcionarioId);

                etNome.setText(funcionario.getNome());
                etTelefone.setText(funcionario.getTel());
                etEmail.setText(funcionario.getEmail());
                etData.setText(funcionario.getDataContrato().toString());
                spCargos.setSelection(cargos.indexOf(funcionario.getCargoDTO()));

                pageTitle.setText(R.string.AtualizaFuncionario);
                btnSalvar.setText(R.string.botaoEditarFuncionario);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        btnSalvar.setOnClickListener(e -> {
            try {

                // Formato que o usuário digita (ajuste conforme seu caso)
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                // Formato que o SQL espera
                SimpleDateFormat formatoSql = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                // Converte o texto para Date e depois para o formato SQL
                java.util.Date dataUtil = formatoEntrada.parse(etData.getText().toString());
                String dataFormatada = formatoSql.format(dataUtil);

                if(funcionarioId == 0) {
                    FuncionarioDTO funcionario = new FuncionarioDTO(
                            0,
                            etNome.getText().toString(),
                            etTelefone.getText().toString(),
                            etEmail.getText().toString(),
                            Date.valueOf(dataFormatada),
                            cargoMapper.toEntity((CargoDTO) spCargos.getSelectedItem())
                    );

                    try {
                        cadastroFacade.novoFuncionario(funcionario);
                        mostrarAlerta("Funcionario cadastrado!", "Funcionario cadastrado com sucesso!");
                    } catch (CargoInvalidoException ex) {
                        mostrarAlerta("Funcionario inválido!", "Funcionario já está cadastrado no sistema.");
                    } catch (SQLException sql) {
                        mostrarAlerta("Erro ao cadastrar Funcionario!", "Erro ao cadastrar Funcionario no sistema.");
                    }
                }
                else {
                    FuncionarioDTO funcionario = new FuncionarioDTO(
                            funcionarioId,
                            etNome.getText().toString(),
                            etTelefone.getText().toString(),
                            etEmail.getText().toString(),
                            Date.valueOf(dataFormatada),
                            cargoMapper.toEntity((CargoDTO) spCargos.getSelectedItem())
                    );

                    try {
                        cadastroFacade.atualizarFuncionario(funcionario);
                        mostrarAlerta("Funcionario atualizado!", "Funcionario atualizado com sucesso!");
                    } catch (SQLException sql) {
                        mostrarAlerta("Erro ao atualizar Funcionario!", "Erro ao atualizar Funcionario no sistema.");
                    }
                }
            } catch (ParseException parse) {
                parse.printStackTrace();
                Toast.makeText(this, "Data inválida! Use o formato dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarSpinnerCargos() {
        try {
            cargos = cadastroFacade.listarCargos();

            ArrayAdapter adapter = new ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    cargos
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCargos.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setMessage(mensagem)
                .setPositiveButton("OK", null)
                .show();
    }
}