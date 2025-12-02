package com.fatec.lanchonetemobile.adapters.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fatec.lanchonetemobile.LanchoneteApp;
import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.CargoDTO;
import com.fatec.lanchonetemobile.application.exception.CargoInvalidoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.application.facade.CadastroFacadeImpl;
import com.fatec.lanchonetemobile.config.AppBuilder;

import java.sql.SQLException;

public class FormCargoActivity extends AppCompatActivity {

    private ImageView ivBack;

    private EditText etNome;
    private EditText etSalario;
    private EditText etDescricao;

    private Button btnSalvar;

    private CadastroFacade cadastroFacade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_cargo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.formCargoActivity), (v, insets) -> {
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

        ivBack = findViewById(R.id.ivBackFormCargo);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        etNome = findViewById(R.id.etNomeFormCargo);
        etSalario = findViewById(R.id.etSalarioFormCargo);
        etDescricao = findViewById(R.id.etDescricaoFormCargo);

        btnSalvar = findViewById(R.id.btnSalvarFormCargo);
        btnSalvar.setOnClickListener(e -> {
            CargoDTO cargo = new CargoDTO(
                    0,
                    etNome.getText().toString(),
                    Double.parseDouble(etSalario.getText().toString()),
                    etDescricao.getText().toString()
            );

            try {
                cadastroFacade.novoCargo(cargo);
                mostrarAlerta("Cargo cadastrado!", "Cargo cadastrado com sucesso!");
            } catch (CargoInvalidoException ex) {
                mostrarAlerta("Cargo inválido!", "Cargo já está cadastrado no sistema.");
            } catch (SQLException sql){
                mostrarAlerta("Erro ao cadastrar cargo!", "Erro ao cadastrar cargo no sistema.");
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
