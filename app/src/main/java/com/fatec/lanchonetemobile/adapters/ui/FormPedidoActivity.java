package com.fatec.lanchonetemobile.adapters.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.LanchoneteApp;
import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.CategoriaDTO;
import com.fatec.lanchonetemobile.application.dto.ClienteDTO;
import com.fatec.lanchonetemobile.application.dto.FornecedorDTO;
import com.fatec.lanchonetemobile.application.dto.ItemPedidoDTO;
import com.fatec.lanchonetemobile.application.dto.PedidoDTO;
import com.fatec.lanchonetemobile.application.dto.ProdutoDTO;
import com.fatec.lanchonetemobile.application.exception.ClienteInvalidoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.application.facade.PedidoFacade;
import com.fatec.lanchonetemobile.config.AppBuilder;
import com.fatec.lanchonetemobile.domain.entity.Cliente;

import java.sql.SQLException;
import java.util.List;

public class FormPedidoActivity extends AppCompatActivity {

    private ImageView btnBack;

    private AutoCompleteTextView acCliente;
    private EditText etData;
    private Spinner spStatus;

    private Button btnAddProduto;
    private Button btnSalvar;


    private ClienteDTO cliente;
    private List<ClienteDTO> clientes;

    private RecyclerView rvItemPedido;
    private List<ItemPedidoDTO> itens;

    private int pedidoId;
    
    private CadastroFacade cadastroFacade;
    private PedidoFacade pedidoFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_pedido);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.formPedidoActivity), (v, insets) -> {
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
        pedidoFacade = builder.getPedidoFacade();
        //-----------------------------------------------------------

        acCliente = findViewById(R.id.etClienteFormPedido);
        etData = findViewById(R.id.etDataFormPedido);
        spStatus = findViewById(R.id.spStatusFormPedido);
        rvItemPedido = findViewById(R.id.rvItemPedido);

        btnAddProduto = findViewById(R.id.btnAddItemPedido);
        btnSalvar = findViewById(R.id.btnSalvarFormPedido);

        btnBack = findViewById(R.id.ivBackFormPedido);
        btnBack.setOnClickListener(e -> {
            finish();
        });

        //ADICIONA CLIENTES AO AUTOCOMPLETE
        carregarSpinnerClientes();

        acCliente.setOnItemClickListener((parent, view, position, id) -> {
            cliente = (ClienteDTO) parent.getItemAtPosition(position);
            acCliente.setText(cliente.getNome());
        });

        //----------------------AJUSTA DROPDOWN PARA APARECER MESMO COM O TECLADO-------------------
        acCliente.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.post(() -> {
                    // Pega a posição do AutoCompleteTextView na tela
                    int[] location = new int[2];
                    acCliente.getLocationOnScreen(location);
                    int yPosition = location[1];

                    // Calcula o espaço disponível acima
                    int espacoAcima = yPosition;

                    // Define altura do dropdown (máximo 300dp)
                    int dropdownHeight = Math.min(dpToPx(300), espacoAcima - dpToPx(50));

                    // Offset negativo: altura do campo + altura do dropdown
                    int offset = -(acCliente.getHeight() + dropdownHeight);

                    acCliente.setDropDownHeight(dropdownHeight);
                    acCliente.setDropDownVerticalOffset(offset);
                });
            }
        });

        // Também ao clicar
        acCliente.setOnClickListener(v -> {
            v.post(() -> {
                int[] location = new int[2];
                acCliente.getLocationOnScreen(location);
                int yPosition = location[1];

                int espacoAcima = yPosition;
                int dropdownHeight = Math.min(dpToPx(300), espacoAcima - dpToPx(50));
                int offset = -(acCliente.getHeight() + dropdownHeight);

                acCliente.setDropDownHeight(dropdownHeight);
                acCliente.setDropDownVerticalOffset(offset);
            });
        });
        //------------------------------------------------------------------------------------------

        //TODO: AJUSTAR PARA RECUPERAR DADOS DO PEDIDO, INCLUSIVE LISTA DE ITENS
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PEDIDO_ID")) {
            pedidoId = intent.getIntExtra("PEDIDO_ID", 0);

            try {
                PedidoDTO pedido = pedidoFacade.buscarPedido(pedidoId);

                acCliente.setText(pedido.getNomeCliente());
                etData.setText(String.valueOf(pedido.getDataPedido()));

                //TODO: ADICIONAR STRING botaoEditarPedido AO STRINGS.XML
                //btnSalvar.setText(R.string.botaoEditarPedido);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        //TODO: IMPLEMENTAR BOTÃO SALVAR
        btnSalvar.setOnClickListener(e -> {

        });
    }

    //TODO: IMPLEMENTAR CARREGAMENTO DO SPINNER DE STATUS
    private void carregarSpinnerStatus() {

    }

    private void carregarSpinnerClientes() {
        try {
            clientes = cadastroFacade.listarClientes();

            ArrayAdapter<ClienteDTO> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    clientes
            );

            acCliente.setAdapter(adapter);

        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao carregar clientes", Toast.LENGTH_SHORT).show();
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setMessage(mensagem)
                .setPositiveButton("OK", null)
                .show();
    }
}
