package com.fatec.lanchonetemobile.adapters.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.LanchoneteApp;
import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.adapters.adapter.ItemPedidoAdapter;
import com.fatec.lanchonetemobile.application.dto.CargoDTO;
import com.fatec.lanchonetemobile.application.dto.ClienteDTO;
import com.fatec.lanchonetemobile.application.dto.ItemPedidoDTO;
import com.fatec.lanchonetemobile.application.dto.PedidoDTO;
import com.fatec.lanchonetemobile.application.dto.ProdutoDTO;
import com.fatec.lanchonetemobile.application.exception.PedidoInvalidoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.application.facade.PedidoFacade;
import com.fatec.lanchonetemobile.application.mapper.ClienteMapper;
import com.fatec.lanchonetemobile.application.mapper.ItemPedidoMapper;
import com.fatec.lanchonetemobile.application.mapper.ProdutoMapper;
import com.fatec.lanchonetemobile.config.AppBuilder;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FormPedidoActivity extends AppCompatActivity {

    private ImageView btnBack;

    private AutoCompleteTextView acCliente;
    private EditText etData;
    private Spinner spStatus;
    private Double valorTotal;
    private List<String> status;

    private Button btnAddProduto;
    private Button btnSalvar;

    private PedidoDTO pedido;


    private ProdutoDTO produto;
    private List<ProdutoDTO> produtos;
    private ProdutoMapper produtoMapper = new ProdutoMapper();
    private AutoCompleteTextView acProduto;


    private ClienteDTO cliente;
    private List<ClienteDTO> clientes;
    private ClienteMapper clienteMapper = new ClienteMapper();

    private RecyclerView rvItemPedido;
    private List<ItemPedidoDTO> itens;
    private ItemPedidoAdapter adapter;
    private ItemPedidoMapper itemMapper = new ItemPedidoMapper();

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

        status = new ArrayList<>();
        status.add(String.valueOf(R.string.EmPreparo));
        status.add(String.valueOf(R.string.Entregando));
        status.add(String.valueOf(R.string.Finalizado));
        status.add(String.valueOf(R.string.Cancelado));

        btnAddProduto = findViewById(R.id.btnAddItemPedido);
        btnAddProduto.setOnClickListener(e -> mostrarDialogFormItem());

        btnSalvar = findViewById(R.id.btnSalvarFormPedido);

        btnBack = findViewById(R.id.ivBackFormPedido);
        btnBack.setOnClickListener(e -> {
            finish();
        });



        //ADICIONA CLIENTES AO AUTOCOMPLETE
        carregarSpinnerClientes();
        carregarSpinnerStatus();

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

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PEDIDO_ID")) {
            pedidoId = intent.getIntExtra("PEDIDO_ID", 0);

            try {
                PedidoDTO pedido = pedidoFacade.buscarPedido(pedidoId);

                acCliente.setText(pedido.getNomeCliente());
                etData.setText(String.valueOf(pedido.getDataPedido()));
                valorTotal = pedido.getValorTotal();
                spStatus.setSelection(status.indexOf(pedido.getStatus()));
                itens = pedido.itensPedido().stream().map(itemMapper :: toDTO).collect(Collectors.toList());
                cliente = pedido.getClienteDTO();

                btnSalvar.setText(R.string.botaoEditarPedido);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            // LOGICA PARA CRIAR PEDIDO VAZIO

//            List<ItemPedidoDTO> listaVazia = new ArrayList<>();
//
//            try {
//                pedido = pedidoFacade.criarPedido(new PedidoDTO(0,
//                        0,
//                        listaVazia.stream().map(itemMapper::toEntity).collect(Collectors.toList()),
//                        null,
//                        null,
//                        null));
//
//                pedido = pedidoFacade.criarPedido(pedido);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
        }

        btnSalvar.setOnClickListener(e -> {
            try {
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());

                SimpleDateFormat formatoSql = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                java.util.Date dataUtil = formatoEntrada.parse(etData.getText().toString());
                String dataFormatada = formatoSql.format(dataUtil);
                if (pedidoId == 0) {
                    PedidoDTO pedido = new PedidoDTO(
                            0,
                            valorTotal,
                            itens.stream().map(itemMapper::toEntity).collect(Collectors.toList()),
                            Date.valueOf(dataFormatada),
                            spStatus.getSelectedItem().toString(),
                            clienteMapper.toEntity(cliente)
                    );

                    try {
                        pedidoFacade.criarPedido(pedido);
                        mostrarAlerta("Pedido cadastrado!", "Pedido cadastrado com sucesso!");
                    } catch (PedidoInvalidoException ex) {
                        mostrarAlerta("Pedido inválido!", "Pedido já está cadastrado no sistema.");
                    } catch (SQLException sql) {
                        mostrarAlerta("Erro ao cadastrar pedido!", "Erro ao cadastrar pedido no sistema.");
                    }
                }
                else{
                    PedidoDTO pedido = new PedidoDTO(
                            pedidoId,
                            valorTotal,
                            itens.stream().map(itemMapper::toEntity).collect(Collectors.toList()),
                            Date.valueOf(dataFormatada),
                            spStatus.getSelectedItem().toString(),
                            clienteMapper.toEntity(cliente)
                    );

                    try {
                        pedidoFacade.atualizarPedido(pedido);
                        mostrarAlerta("Pedido atualizado!", "Pedido atualizado com sucesso!");
                    } catch (PedidoInvalidoException ex) {
                        mostrarAlerta("Pedido inválido!", "Pedido já está atualizado no sistema.");
                    } catch (SQLException sql) {
                        mostrarAlerta("Erro ao cadastrar pedido!", "Erro ao cadastrar pedido no sistema.");
                    }
                }
            } catch(ParseException parse){
                parse.printStackTrace();
                Toast.makeText(this, "Data inválida! Use o formato dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            }

        });

        itens = new ArrayList<>();

        rvItemPedido = findViewById(R.id.rvItemPedido);
        rvItemPedido.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemPedidoAdapter(itens, (item, position) -> mostrarDialogItem(item, position));
        rvItemPedido.setAdapter(adapter);
    }

    private void mostrarDialogItem(ItemPedidoDTO item, int position) {
    }

    private void mostrarDialogFormItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_form_item_pedido, null);
        builder.setView(dialogView);

        acProduto = dialogView.findViewById(R.id.acProdutoItemFormDialog);
        EditText etQtdItem = dialogView.findViewById(R.id.etQtdItemFormDialog);

        Button btnAdd = dialogView.findViewById(R.id.btnAddItemFormDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseItemFormDialog);

        AlertDialog dialog = builder.create();

        //CARREGAR PRODUTOS NO AUTOCOMPLETE
        carregarSpinnerProdutos();

        acProduto.setOnItemClickListener((parent, view, position, id) -> {
            produto = (ProdutoDTO) parent.getItemAtPosition(position);
            acProduto.setText(produto.getNome());
        });

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnAdd.setOnClickListener(v -> {
            try {
                int qtd = Integer.parseInt(etQtdItem.getText().toString());
                if (qtd > 0) {
                    ItemPedidoDTO item = new ItemPedidoDTO(
                            0,
                            produtoMapper.toEntity(produto),
                            qtd,
                            produto.valorUn(),
                            produto.valorUn() * qtd
                    );


                    pedidoFacade.adicionarItem(item);
                    itens.add(item);
                    adapter.notifyItemInserted(itens.size() - 1);
                    dialog.dismiss();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        dialog.show();
    }

    private void carregarSpinnerStatus() {
        ArrayAdapter adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                status
        );

        spStatus.setAdapter(adapter);
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

    private void carregarSpinnerProdutos() {
        try {
            produtos = cadastroFacade.listarProdutos();

            ArrayAdapter<ProdutoDTO> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    produtos
            );

            acProduto.setAdapter(adapter);

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
