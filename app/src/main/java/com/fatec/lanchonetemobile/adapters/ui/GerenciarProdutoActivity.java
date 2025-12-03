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
import com.fatec.lanchonetemobile.adapters.adapter.PedidoAdapter;
import com.fatec.lanchonetemobile.adapters.adapter.ProdutoAdapter;
import com.fatec.lanchonetemobile.application.dto.FuncionarioDTO;
import com.fatec.lanchonetemobile.application.dto.ProdutoDTO;
import com.fatec.lanchonetemobile.application.exception.ProdutoNaoEncontradoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.config.AppBuilder;
import com.google.android.material.card.MaterialCardView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenciarProdutoActivity extends AppCompatActivity {

    private MaterialCardView btnAddProduto;
    private MaterialCardView btnBuscarProduto;

    private ImageView ivBack;

    private List<ProdutoDTO> produtos;
    private RecyclerView rvProdutos;

    private ProdutoAdapter adapter;
    private CadastroFacade cadastroFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_produto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gerenciarProdutoActivity), (v, insets) -> {
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

        btnAddProduto = findViewById(R.id.btnAddProduto);
        btnAddProduto.setOnClickListener(e -> {
            Intent intent = new Intent(this, FormProdutoActivity.class);
            startActivity(intent);
        });

        btnBuscarProduto = findViewById(R.id.btnBuscarProduto);
        btnBuscarProduto.setOnClickListener(e -> {
            mostrarBuscarDialog();
        });


        rvProdutos = findViewById(R.id.rvListaGerenciarProduto);

        ivBack = findViewById(R.id.ivBackGerenciarProduto);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        produtos = new ArrayList<>();

        rvProdutos = findViewById(R.id.rvListaGerenciarProduto);
        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProdutoAdapter(produtos, (produto, position) -> mostrarDialogProduto(produto, position));
        rvProdutos.setAdapter(adapter);

        carregarProdutos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProdutos();
    }

    private void carregarProdutos(){
        try {
            List<ProdutoDTO> novosProdutos = cadastroFacade.listarProdutos();
            if (novosProdutos != null) {
                produtos.clear();
                produtos.addAll(novosProdutos);
                adapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarBuscarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_buscar_produto, null);
        builder.setView(dialogView);

        EditText etID = dialogView.findViewById(R.id.etIDBuscarProdutoDialog);

        Button btnBuscar = dialogView.findViewById(R.id.btnBuscarProdutoDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseBuscarProdutoDialog);

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnBuscar.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(etID.getText().toString());
                ProdutoDTO produto = cadastroFacade.buscarProduto(id);

                mostrarDialogProduto(produto, 0);
            } catch (ProdutoNaoEncontradoException p) {
                Toast.makeText(this, "Produto não encontrado", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        dialog.show();
    }

    private void mostrarDialogProduto(ProdutoDTO produto, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_produto, null);
        builder.setView(dialogView);

        TextView tvIDProduto = dialogView.findViewById(R.id.tvIDProdutoDialog);
        TextView tvNomeProduto = dialogView.findViewById(R.id.tvNomeProdutoDialog);
        TextView tvEstoqueProduto = dialogView.findViewById(R.id.tvEstoqueProdutoDialog);
        TextView tvValorUnitProduto = dialogView.findViewById(R.id.tvValorUnitProdutoDialog);
        TextView tvCategoriaProduto = dialogView.findViewById(R.id.tvCategoriaProdutoDialog);
        TextView tvFornecedorProduto = dialogView.findViewById(R.id.tvFornProdutoDialog);

        Button btnEditar = dialogView.findViewById(R.id.btnUpdateProdutoDialog);
        Button btnExcluir = dialogView.findViewById(R.id.btnExcluirProdutoDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseProdutoDialog);

        tvIDProduto.setText("Produto #" + produto.getId());
        tvNomeProduto.setText("Nome: " + produto.getNome());
        tvEstoqueProduto.setText("Estoque: " + produto.getQtdEstoque());
        tvValorUnitProduto.setText("Valor Unitário: " + produto.getValorUn());
        tvCategoriaProduto.setText("Categoria: " + produto.getCategoriaNome());
        tvFornecedorProduto.setText("Fornecedor: " + produto.getFornecedorNome());

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormProdutoActivity.class);
            intent.putExtra("PRODUTO_ID", produto.getId());
            startActivity(intent);
            dialog.dismiss();
        });

        btnExcluir.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmar exclusão")
                    .setMessage("Deseja realmente excluir este produto?")
                    .setPositiveButton("Sim", (d, which) -> {
                        produtos.remove(position);
                        try {
                            cadastroFacade.removerProduto(produto.getId());
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
