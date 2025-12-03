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
import com.fatec.lanchonetemobile.adapters.adapter.FornecedorAdapter;
import com.fatec.lanchonetemobile.application.dto.FornecedorDTO;
import com.fatec.lanchonetemobile.application.exception.FuncionarioNaoEncontradoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.config.AppBuilder;
import com.google.android.material.card.MaterialCardView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenciarFornecedorActivity extends AppCompatActivity {
    private MaterialCardView btnAddFornecedor;
    private MaterialCardView btnBuscarFornecedor;

    private ImageView ivBack;

    private RecyclerView rvFornecedores;

    private FornecedorAdapter adapter;
    private List<FornecedorDTO> fornecedores = new ArrayList<>();
    private CadastroFacade cadastroFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_fornecedor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gerenciarFornecedorActivity), (v, insets) -> {
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

        btnAddFornecedor = findViewById(R.id.btnAddFornecedor);
        btnBuscarFornecedor = findViewById(R.id.btnBuscarFornecedor);

        rvFornecedores = findViewById(R.id.rvListaGerenciarFornecedor);

        ivBack = findViewById(R.id.ivBackGerenciarFornecedor);
        ivBack.setOnClickListener(e -> {
            finish();
        });


        fornecedores = new ArrayList<>();

        rvFornecedores = findViewById(R.id.rvListaGerenciarForn);
        rvFornecedores.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FornecedorAdapter(fornecedores, (fornecedor, position) -> mostrarDialogFornecedor(fornecedor, position));
        rvFornecedores.setAdapter(adapter);

        carregarFornecedores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarFornecedores();
    }

    private void carregarFornecedores() {
        try {
            List<FornecedorDTO> novosFornecedors = cadastroFacade.listarFornecedores();
            if (novosFornecedors != null) {
                fornecedores.clear();
                fornecedores.addAll(novosFornecedors);
                adapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarBuscarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_buscar_fornecedor, null);
        builder.setView(dialogView);

        EditText etID = dialogView.findViewById(R.id.etIDBuscarFornecedorDialog);

        Button btnBuscar = dialogView.findViewById(R.id.btnBuscarFornecedorDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseBuscarFornecedorDialog);

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnBuscar.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(etID.getText().toString());
                FornecedorDTO fornecedor = cadastroFacade.buscarFornecedor(id);

                mostrarDialogFornecedor(fornecedor, 0);
            } catch (FuncionarioNaoEncontradoException f) {
                Toast.makeText(this, "Funcionário não encontrado", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        dialog.show();
    }

    private void mostrarDialogFornecedor(FornecedorDTO fornecedor, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_fornecedor, null);
        builder.setView(dialogView);

        TextView tvIDFornecedor = dialogView.findViewById(R.id.tvIDFornecedorDialog);
        TextView tvNomeFornecedor = dialogView.findViewById(R.id.tvNomeFornecedorDialog);
        TextView tvTelefoneFornecedor = dialogView.findViewById(R.id.tvTelefoneFornecedorDialog);
        TextView tvCPFFornecedor = dialogView.findViewById(R.id.tvCPFFornecedorDialog);
        TextView tvLogradouroFornecedor = dialogView.findViewById(R.id.tvLogradouroFornecedorDialog);
        TextView tvNumeroFornecedor = dialogView.findViewById(R.id.tvNumeroFornecedorDialog);
        TextView tvCEPFornecedor = dialogView.findViewById(R.id.tvCEPFornecedorDialog);
        TextView tvComplementoFornecedor = dialogView.findViewById(R.id.tvComplementoFornecedorDialog);

        Button btnEditar = dialogView.findViewById(R.id.btnUpdateFornecedorDialog);
        Button btnExcluir = dialogView.findViewById(R.id.btnExcluirFornecedorDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseFornecedorDialog);

        tvIDFornecedor.setText("Fornecedor #" + fornecedor.getId());
        tvNomeFornecedor.setText("Nome: " + fornecedor.getNome());
        tvCPFFornecedor.setText("CPF: " + fornecedor.getCnpj());
        tvTelefoneFornecedor.setText("Telefone: " + fornecedor.getTel());
        tvLogradouroFornecedor.setText("Logradouro: " + fornecedor.getLogradouro());
        tvNumeroFornecedor.setText("Numero: " + fornecedor.getNumero());
        tvCEPFornecedor.setText("CEP: " + fornecedor.getCep());
        tvComplementoFornecedor.setText("Complemento: " + fornecedor.getComplemento());

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormFornecedorActivity.class);
            intent.putExtra("FORNECEDOR_ID", fornecedor.getId());
            startActivity(intent);
            dialog.dismiss();
        });

        btnExcluir.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmar exclusão")
                    .setMessage("Deseja realmente excluir este pedido?")
                    .setPositiveButton("Sim", (d, which) -> {
                        fornecedores.remove(position);
                        try {
                            cadastroFacade.removerFornecedor(fornecedor.getId());
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
