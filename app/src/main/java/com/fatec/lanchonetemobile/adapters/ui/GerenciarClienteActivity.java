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
import com.fatec.lanchonetemobile.adapters.adapter.ClienteAdapter;
import com.fatec.lanchonetemobile.application.dto.ClienteDTO;
import com.fatec.lanchonetemobile.application.exception.FuncionarioNaoEncontradoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.config.AppBuilder;
import com.google.android.material.card.MaterialCardView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenciarClienteActivity extends AppCompatActivity {
    private MaterialCardView btnAddCliente;
    private MaterialCardView btnBuscarCliente;

    private ImageView ivBack;

    private RecyclerView rvClientes;

    private ClienteAdapter adapter;
    private List<ClienteDTO> clientes = new ArrayList<>();
    private CadastroFacade cadastroFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gerenciarClienteActivity), (v, insets) -> {
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

        btnAddCliente = findViewById(R.id.btnAddCliente);
        btnBuscarCliente = findViewById(R.id.btnBuscarCliente);

        rvClientes = findViewById(R.id.rvListaGerenciarCliente);

        ivBack = findViewById(R.id.ivBackGerenciarCliente);
        ivBack.setOnClickListener(e -> {
            finish();
        });


        clientes = new ArrayList<>();

        rvClientes = findViewById(R.id.rvListaGerenciarCliente);
        rvClientes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClienteAdapter(clientes, (cliente, position) -> mostrarDialogCliente(cliente, position));
        rvClientes.setAdapter(adapter);

        carregarClientes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarClientes();
    }

    private void carregarClientes() {
        try {
            List<ClienteDTO> novosClientes = cadastroFacade.listarClientes();
            if (novosClientes != null) {
                clientes.clear();
                clientes.addAll(novosClientes);
                adapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarBuscarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_buscar_cliente, null);
        builder.setView(dialogView);

        EditText etID = dialogView.findViewById(R.id.etIDBuscarClienteDialog);

        Button btnBuscar = dialogView.findViewById(R.id.btnBuscarClienteDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseBuscarClienteDialog);

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnBuscar.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(etID.getText().toString());
                ClienteDTO cliente = cadastroFacade.buscarCliente(id);

                mostrarDialogCliente(cliente, 0);
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

    private void mostrarDialogCliente(ClienteDTO cliente, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_cliente, null);
        builder.setView(dialogView);

        TextView tvIDCliente = dialogView.findViewById(R.id.tvIDClienteDialog);
        TextView tvNomeCliente = dialogView.findViewById(R.id.tvNomeClienteDialog);
        TextView tvTelefoneCliente = dialogView.findViewById(R.id.tvTelefoneClienteDialog);
        TextView tvCPFCliente = dialogView.findViewById(R.id.tvCPFClienteDialog);
        TextView tvLogradouroCliente = dialogView.findViewById(R.id.tvLogradouroClienteDialog);
        TextView tvNumeroCliente = dialogView.findViewById(R.id.tvNumeroClienteDialog);
        TextView tvCEPCliente = dialogView.findViewById(R.id.tvCEPClienteDialog);
        TextView tvComplementoCliente = dialogView.findViewById(R.id.tvComplementoClienteDialog);

        Button btnEditar = dialogView.findViewById(R.id.btnUpdateClienteDialog);
        Button btnExcluir = dialogView.findViewById(R.id.btnExcluirClienteDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseClienteDialog);

        tvIDCliente.setText("Cliente #" + cliente.getId());
        tvNomeCliente.setText("Nome: " + cliente.getNome());
        tvCPFCliente.setText("CPF: " + cliente.getCpf());
        tvTelefoneCliente.setText("Telefone: " + cliente.getTel());
        tvLogradouroCliente.setText("Logradouro: " + cliente.getLogradouro());
        tvNumeroCliente.setText("Numero: " + cliente.getNumero());
        tvCEPCliente.setText("CEP: " + cliente.getCep());
        tvComplementoCliente.setText("Complemento: " + cliente.getComplemento());

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormClienteActivity.class);
            intent.putExtra("CLIENTE_ID", cliente.getId());
            startActivity(intent);
            dialog.dismiss();
        });

        btnExcluir.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmar exclusão")
                    .setMessage("Deseja realmente excluir este pedido?")
                    .setPositiveButton("Sim", (d, which) -> {
                        clientes.remove(position);
                        try {
                            cadastroFacade.removerCliente(cliente.getId());
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
