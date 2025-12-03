package com.fatec.lanchonetemobile.adapters.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.fatec.lanchonetemobile.application.dto.CargoDTO;
import com.fatec.lanchonetemobile.application.exception.CargoNaoEncontradoException;
import com.fatec.lanchonetemobile.application.exception.FuncionarioNaoEncontradoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.config.AppBuilder;
import com.google.android.material.card.MaterialCardView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenciarCargoActivity extends AppCompatActivity {
    private MaterialCardView btnAddCargo;
    private MaterialCardView btnBuscarCargo;

    private ImageView ivBack;

    private RecyclerView rvCargos;
    private CargoAdapter adapter;
    private List<CargoDTO> cargos;

    private CadastroFacade cadastroFacade;

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

        //--------------------INJETA DEPENDENCIAS--------------------
        LanchoneteApp app = (LanchoneteApp) getApplication();
        AppBuilder builder = app.getAppBuilder();

        if (builder == null) {
            throw new IllegalStateException("AppBuilder está null. Verifique LanchoneteApp.onCreate e o AndroidManifest.");
        }

        cadastroFacade = builder.getCadastroFacade();
        //-----------------------------------------------------------

        btnAddCargo = findViewById(R.id.btnAddCargo);
        btnAddCargo.setOnClickListener(e -> {
            Intent intent = new Intent(this, FormCargoActivity.class);
            startActivity(intent);
        });

        btnBuscarCargo = findViewById(R.id.btnBuscarCargo);
        btnBuscarCargo.setOnClickListener(e -> {
            mostrarBuscarDialog();
        });


        rvCargos = findViewById(R.id.rvListaGerenciarCargo);

        ivBack = findViewById(R.id.ivBackGerenciarCargo);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        cargos = new ArrayList<>();

        rvCargos = findViewById(R.id.rvListaGerenciarCargo);
        rvCargos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CargoAdapter(cargos, (cargo, position) -> mostrarDialogCargo(cargo, position));
        rvCargos.setAdapter(adapter);

        carregarCargos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarCargos();
    }

    private void carregarCargos(){
        try {
            List<CargoDTO> novosCargos = cadastroFacade.listarCargos();
            if (novosCargos != null) {
                cargos.clear();
                cargos.addAll(novosCargos);
                adapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarBuscarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_buscar_cargo, null);
        builder.setView(dialogView);

        EditText etID = dialogView.findViewById(R.id.etIDBuscarCargoDialog);

        Button btnBuscar = dialogView.findViewById(R.id.btnBuscarCargoDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseBuscarCargoDialog);

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnBuscar.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(etID.getText().toString());
                CargoDTO cargo = cadastroFacade.buscarCargo(id);

                mostrarDialogCargo(cargo, 0);
            } catch (CargoNaoEncontradoException f) {
                Toast.makeText(this, "Cargo não encontrado", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        dialog.show();
    }

    private void mostrarDialogCargo(CargoDTO cargo, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_cargo, null);
        builder.setView(dialogView);

        TextView tvIDCargo = dialogView.findViewById(R.id.tvIDCargoDialog);
        TextView tvNomeCargo = dialogView.findViewById(R.id.tvNomeCargoDialog);
        TextView tvSalarioCargo = dialogView.findViewById(R.id.tvSalarioCargoDialog);
        TextView tvDescricaoCargo = dialogView.findViewById(R.id.tvDescricaoCargoDialog);

        Button btnEditar = dialogView.findViewById(R.id.btnUpdateCargoDialog);
        Button btnExcluir = dialogView.findViewById(R.id.btnExcluirCargoDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseCargoDialog);

        tvIDCargo.setText("Cargo #" + cargo.getId());
        tvNomeCargo.setText("Nome: " + cargo.getNome());
        tvSalarioCargo.setText("Salário: " + cargo.getSalario());
        tvDescricaoCargo.setText("Descrição: " + cargo.getDescricao());

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormCargoActivity.class);
            intent.putExtra("CARGO_ID", cargo.getId());
            startActivity(intent);
            dialog.dismiss();
        });

        btnExcluir.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmar exclusão")
                    .setMessage("Deseja realmente excluir este pedido?")
                    .setPositiveButton("Sim", (d, which) -> {
                        cargos.remove(position);
                        try {
                            cadastroFacade.removerCargo(cargo.getId());
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
