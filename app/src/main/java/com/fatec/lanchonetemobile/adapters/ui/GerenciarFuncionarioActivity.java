package com.fatec.lanchonetemobile.adapters.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.LanchoneteApp;
import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.adapters.adapter.CargoAdapter;
import com.fatec.lanchonetemobile.adapters.adapter.FuncionarioAdapter;
import com.fatec.lanchonetemobile.adapters.adapter.PedidoAdapter;
import com.fatec.lanchonetemobile.application.dto.CargoDTO;
import com.fatec.lanchonetemobile.application.dto.FuncionarioDTO;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.config.AppBuilder;
import com.google.android.material.card.MaterialCardView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GerenciarFuncionarioActivity extends AppCompatActivity {
    private MaterialCardView btnAddFuncionario;
    private MaterialCardView btnBuscarFuncionario;

    private ImageView ivBack;

    private RecyclerView rvFuncionarios;

    private FuncionarioAdapter adapter;
    private List<FuncionarioDTO> funcionarios = new ArrayList<>();

    private CadastroFacade cadastroFacade;

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

        //--------------------INJETA DEPENDENCIAS--------------------
        LanchoneteApp app = (LanchoneteApp) getApplication();
        AppBuilder builder = app.getAppBuilder();

        if (builder == null) {
            throw new IllegalStateException("AppBuilder está null. Verifique LanchoneteApp.onCreate e o AndroidManifest.");
        }

        cadastroFacade = builder.getCadastroFacade();
        //-----------------------------------------------------------

        btnAddFuncionario = findViewById(R.id.btnAddFunc);
        btnAddFuncionario.setOnClickListener(e -> {
            Intent inten = new Intent(this, FormFuncionarioActivity.class);
            startActivity(inten);
        });

        btnBuscarFuncionario = findViewById(R.id.btnBuscarFunc);

        rvFuncionarios = findViewById(R.id.rvListaGerenciarFunc);

        ivBack = findViewById(R.id.ivBackGerenciarFunc);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        funcionarios = new ArrayList<>();

        rvFuncionarios = findViewById(R.id.rvListaGerenciarFunc);
        rvFuncionarios.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FuncionarioAdapter(funcionarios, (funcionario, position) -> mostrarDialogFuncionario(funcionario, position));
        rvFuncionarios.setAdapter(adapter);

        carregarFuncionarios();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarFuncionarios();
    }

    private void carregarFuncionarios(){
        try {
            List<FuncionarioDTO> novosFuncionarios = cadastroFacade.listarFuncionarios();
            if (novosFuncionarios != null) {
                funcionarios.clear();
                funcionarios.addAll(novosFuncionarios);
                adapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarDialogFuncionario(FuncionarioDTO funcionario, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_funcionario, null);
        builder.setView(dialogView);

        TextView tvIDFuncionario = dialogView.findViewById(R.id.tvIDFuncDialog);
        TextView tvNomeFuncionario = dialogView.findViewById(R.id.tvNomeFuncDialog);
        TextView tvTelefoneFuncionario = dialogView.findViewById(R.id.tvTelefoneFuncDialog);
        TextView tvEmailFuncionario = dialogView.findViewById(R.id.tvEmailFuncDialog);
        TextView tvDataFuncionario = dialogView.findViewById(R.id.tvDataFuncDialog);
        TextView tvCargoFuncionario = dialogView.findViewById(R.id.tvCargoFuncDialog);

        Button btnEditar = dialogView.findViewById(R.id.btnUpdateFuncDialog);
        Button btnExcluir = dialogView.findViewById(R.id.btnExcluirFuncDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseFuncDialog);

        tvIDFuncionario.setText("Funcionario #" + funcionario.getId());
        tvNomeFuncionario.setText("Nome: " + funcionario.getNome());
        tvTelefoneFuncionario.setText("Telefone: " + funcionario.getTel());
        tvEmailFuncionario.setText("Email: " + funcionario.getEmail());
        tvDataFuncionario.setText("Data de Contrato: " + funcionario.getDataContrato());
        tvCargoFuncionario.setText("Cargo: " + funcionario.getCargo());

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormFuncionarioActivity.class);
            intent.putExtra("FUNCIONARIO_ID", funcionario.getId());
            startActivity(intent);
            dialog.dismiss();
        });

        btnExcluir.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmar exclusão")
                    .setMessage("Deseja realmente excluir este pedido?")
                    .setPositiveButton("Sim", (d, which) -> {
                        funcionarios.remove(position);
                        try {
                            cadastroFacade.removerFuncionario(funcionario.getId());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyItemRemoved(position);
                        dialog.dismiss();
                    })
                    .setNegativeButton("Não", null)
                    .show();
        });

        dialog.show();
    }
}
