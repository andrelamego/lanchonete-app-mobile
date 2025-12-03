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
import com.fatec.lanchonetemobile.adapters.adapter.CategoriaAdapter;
import com.fatec.lanchonetemobile.application.dto.CargoDTO;
import com.fatec.lanchonetemobile.application.dto.CategoriaDTO;
import com.fatec.lanchonetemobile.application.exception.FuncionarioNaoEncontradoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.config.AppBuilder;
import com.google.android.material.card.MaterialCardView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenciarCategoriaActivity extends AppCompatActivity {
    private MaterialCardView btnAddCategoria;
    private MaterialCardView btnBuscarCategoria;

    private ImageView ivBack;

    private RecyclerView rvCategorias;

    private CategoriaAdapter adapter;
    private List<CategoriaDTO> categorias = new ArrayList<>();
    private CadastroFacade cadastroFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_categoria);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gerenciarCategoriaActivity), (v, insets) -> {
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

        btnAddCategoria = findViewById(R.id.btnAddCateg);
        btnBuscarCategoria = findViewById(R.id.btnBuscarCateg);

        rvCategorias = findViewById(R.id.rvListaGerenciarCateg);

        ivBack = findViewById(R.id.ivBackGerenciarCateg);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        categorias = new ArrayList<>();

        rvCategorias = findViewById(R.id.rvListaGerenciarCateg);
        rvCategorias.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoriaAdapter(categorias, (categoria, position) -> mostrarDialogCategoria(categoria, position));
        rvCategorias.setAdapter(adapter);

        carregarCategorias();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarCategorias();
    }

    private void carregarCategorias(){
        try {
            List<CategoriaDTO> novasCategorias = cadastroFacade.listarCategorias();
            if (novasCategorias != null) {
                categorias.clear();
                categorias.addAll(novasCategorias);
                adapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarBuscarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_buscar_categoria, null);
        builder.setView(dialogView);

        EditText etID = dialogView.findViewById(R.id.etIDBuscarCategoriaDialog);

        Button btnBuscar = dialogView.findViewById(R.id.btnBuscarCategoriaDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseBuscarCategoriaDialog);

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnBuscar.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(etID.getText().toString());
                CategoriaDTO categoria = cadastroFacade.buscarCategoria(id);

                mostrarDialogCategoria(categoria, 0);
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

    private void mostrarDialogCategoria(CategoriaDTO categoria, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_categoria, null);
        builder.setView(dialogView);

        TextView tvIDCategoria = dialogView.findViewById(R.id.tvIDCategoriaDialog);
        TextView tvNomeCategoria = dialogView.findViewById(R.id.tvNomeCategoriaDialog);
        TextView tvDescricaoCategoria = dialogView.findViewById(R.id.tvDescricaoCategoriaDialog);

        Button btnEditar = dialogView.findViewById(R.id.btnUpdateCategoriaDialog);
        Button btnExcluir = dialogView.findViewById(R.id.btnExcluirCategoriaDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseCategoriaDialog);

        tvIDCategoria.setText("categoria #" + categoria.getId());
        tvNomeCategoria.setText("Nome: " + categoria.getNome());
        tvDescricaoCategoria.setText("Descrição: " + categoria.getDescricao());

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormCategoriaActivity.class);
            intent.putExtra("CARGO_ID", categoria.getId());
            startActivity(intent);
            dialog.dismiss();
        });

        btnExcluir.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmar exclusão")
                    .setMessage("Deseja realmente excluir este pedido?")
                    .setPositiveButton("Sim", (d, which) -> {
                        categorias.remove(position);
                        try {
                            cadastroFacade.removerCategoria(categoria.getId());
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
