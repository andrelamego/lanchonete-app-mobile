package com.fatec.lanchonetemobile.adapters.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import com.fatec.lanchonetemobile.LanchoneteApp;
import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.CategoriaDTO;
import com.fatec.lanchonetemobile.application.dto.FornecedorDTO;
import com.fatec.lanchonetemobile.application.dto.ProdutoDTO;
import com.fatec.lanchonetemobile.application.exception.ClienteInvalidoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.application.mapper.CategoriaMapper;
import com.fatec.lanchonetemobile.application.mapper.FornecedorMapper;
import com.fatec.lanchonetemobile.config.AppBuilder;

import java.sql.SQLException;
import java.util.List;

public class FormProdutoActivity extends AppCompatActivity {

    private EditText etNome;
    private EditText etQtd;
    private EditText etValorUnit;
    private Spinner spCategoria;
    private AutoCompleteTextView acFornecedor;

    private int produtoId = 0;

    private ImageView ivBack;
    private TextView pageTitle;
    private Button btnSalvar;

    private List<FornecedorDTO> fornecedores;
    private FornecedorDTO fornecedor;
    private FornecedorMapper fornecedorMapper = new FornecedorMapper();

    private CategoriaMapper categoriaMapper = new CategoriaMapper();
    private List<CategoriaDTO> categorias;
    private CadastroFacade cadastroFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_produto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_form_produto), (v, insets) -> {
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

        pageTitle = findViewById(R.id.pageTitleFormProduto);

        etNome = findViewById(R.id.etNomeFormProduto);
        etQtd = findViewById(R.id.etQtdFormProduto);
        etValorUnit = findViewById(R.id.etPrecoFormProduto);
        spCategoria = findViewById(R.id.spCategFormProduto);
        acFornecedor = findViewById(R.id.acFornFormProduto);


        ivBack = findViewById(R.id.ivBackFormProduto);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        btnSalvar = findViewById(R.id.btnSalvarFormProduto);

        carregarSpinnerCargos();
        carregarSpinnerFornecedores();


        acFornecedor.setOnItemClickListener((parent, view, position, id) -> {
                    fornecedor = (FornecedorDTO) parent.getItemAtPosition(position);
                    acFornecedor.setText(fornecedor.getNome());
                });

        //----------------------AJUSTA DROPDOWN PARA APARECER MESMO COM O TECLADO-------------------
        acFornecedor.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.post(() -> {
                    // Pega a posição do AutoCompleteTextView na tela
                    int[] location = new int[2];
                    acFornecedor.getLocationOnScreen(location);
                    int yPosition = location[1];

                    // Calcula o espaço disponível acima
                    int espacoAcima = yPosition;

                    // Define altura do dropdown (máximo 300dp)
                    int dropdownHeight = Math.min(dpToPx(300), espacoAcima - dpToPx(50));

                    // Offset negativo: altura do campo + altura do dropdown
                    int offset = -(acFornecedor.getHeight() + dropdownHeight);

                    acFornecedor.setDropDownHeight(dropdownHeight);
                    acFornecedor.setDropDownVerticalOffset(offset);
                });
            }
        });

        // Também ao clicar
        acFornecedor.setOnClickListener(v -> {
            v.post(() -> {
                int[] location = new int[2];
                acFornecedor.getLocationOnScreen(location);
                int yPosition = location[1];

                int espacoAcima = yPosition;
                int dropdownHeight = Math.min(dpToPx(300), espacoAcima - dpToPx(50));
                int offset = -(acFornecedor.getHeight() + dropdownHeight);

                acFornecedor.setDropDownHeight(dropdownHeight);
                acFornecedor.setDropDownVerticalOffset(offset);
            });
        });
        //------------------------------------------------------------------------------------------


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PRODUTO_ID")) {
            produtoId = intent.getIntExtra("PRODUTO_ID", 0);

            try {
                ProdutoDTO produto = cadastroFacade.buscarProduto(produtoId);

                etNome.setText(produto.getNome());
                etQtd.setText(String.valueOf(produto.getQtdEstoque()));
                etValorUnit.setText(String.valueOf(produto.getValorUn()));
                spCategoria.setSelection(categorias.indexOf(produto.getCategoriaDTO()));
                acFornecedor.setText(produto.getFornecedorNome());
                fornecedor = produto.getFornecedorDTO();


                pageTitle.setText(R.string.AtualizaProduto);
                btnSalvar.setText(R.string.botaoEditarProduto);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        btnSalvar.setOnClickListener(e -> {
            if(produtoId == 0) {
                ProdutoDTO produto = new ProdutoDTO(
                        0,
                        etNome.getText().toString(),
                        Integer.parseInt(etQtd.getText().toString()),
                        Double.parseDouble(etValorUnit.getText().toString()),
                        categoriaMapper.toEntity((CategoriaDTO) spCategoria.getSelectedItem()),
                        fornecedorMapper.toEntity(fornecedor)
                );

                try {
                    cadastroFacade.novoProduto(produto);
                    mostrarAlerta("Produto cadastrado!", "Produto cadastrado com sucesso!");
                } catch (ClienteInvalidoException ex) {
                    mostrarAlerta("Produto inválido!", "Produto já está cadastrado no sistema.");
                } catch (SQLException sql) {
                    mostrarAlerta("Erro ao cadastrar Produto!", "Erro ao cadastrar Produto no sistema.");
                }
            }
            else{
                ProdutoDTO produto = new ProdutoDTO(
                        produtoId,
                        etNome.getText().toString(),
                        Integer.parseInt(etQtd.getText().toString()),
                        Double.parseDouble(etValorUnit.getText().toString()),
                        categoriaMapper.toEntity((CategoriaDTO) spCategoria.getSelectedItem()),
                        fornecedorMapper.toEntity(fornecedor)
                );

                try {
                    cadastroFacade.atualizarProduto(produto);
                    mostrarAlerta("Produto atualizado!", "Produto atualizado com sucesso!");
                } catch (SQLException sql) {
                    mostrarAlerta("Erro ao atualizar Produto!", "Erro ao atualizar Produto no sistema.");
                }
            }
        });

    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private void carregarSpinnerCargos() {
        try {
            categorias = cadastroFacade.listarCategorias();

            ArrayAdapter adapter = new ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    categorias
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategoria.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarSpinnerFornecedores() {
        try {
            fornecedores = cadastroFacade.listarFornecedores();

            ArrayAdapter<FornecedorDTO> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    fornecedores
            );

            acFornecedor.setAdapter(adapter);

        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao carregar produtos", Toast.LENGTH_SHORT).show();
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
