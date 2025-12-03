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
import com.fatec.lanchonetemobile.application.dto.PedidoDTO;
import com.fatec.lanchonetemobile.application.dto.ProdutoDTO;
import com.fatec.lanchonetemobile.application.exception.PedidoNaoEncontradoException;
import com.fatec.lanchonetemobile.application.exception.ProdutoNaoEncontradoException;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.application.facade.PedidoFacade;
import com.fatec.lanchonetemobile.config.AppBuilder;
import com.google.android.material.card.MaterialCardView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GerenciarPedidoActivity extends AppCompatActivity {

    private MaterialCardView btnAddPedido;
    private MaterialCardView btnBuscarPedido;

    private ImageView ivBack;

    private RecyclerView rvPedidos;

    private PedidoAdapter adapter;
    private List<PedidoDTO> pedidos;

    private CadastroFacade cadastroFacade;
    private PedidoFacade pedidoFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_pedido);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gerenciarPedidoActivity), (v, insets) -> {
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

        btnAddPedido = findViewById(R.id.btnAddPedido);
        btnAddPedido.setOnClickListener(e -> {
            Intent intent = new Intent(this, FormPedidoActivity.class);
            startActivity(intent);
        });

        btnBuscarPedido = findViewById(R.id.btnBuscarPedido);
        btnBuscarPedido.setOnClickListener(e -> {
            mostrarBuscarDialog();
        });

        rvPedidos = findViewById(R.id.rvPedidos);

        ivBack = findViewById(R.id.ivBackGerenciarPedido);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        pedidos = new ArrayList<>();

        rvPedidos = findViewById(R.id.rvPedidos);
        rvPedidos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PedidoAdapter(pedidos, (pedido, position) -> mostrarDialogPedido(pedido, position));
        rvPedidos.setAdapter(adapter);

        carregarPedidos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarPedidos();
    }

    private void carregarPedidos(){
        try {
            List<PedidoDTO> novosPedidos = pedidoFacade.listarPedidos();
            if (novosPedidos != null) {
                pedidos.clear();
                pedidos.addAll(novosPedidos);
                adapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarBuscarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_buscar_pedido, null);
        builder.setView(dialogView);

        EditText etID = dialogView.findViewById(R.id.etIDBuscarPedidoDialog);

        Button btnBuscar = dialogView.findViewById(R.id.btnBuscarPedidoDialog);
        ImageView btnFechar = dialogView.findViewById(R.id.btnCloseBuscarPedidoDialog);

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnBuscar.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(etID.getText().toString());
                PedidoDTO pedido = pedidoFacade.buscarPedido(id);

                mostrarDialogPedido(pedido, 0);
            } catch (PedidoNaoEncontradoException p) {
                Toast.makeText(this, "Pedido não encontrado", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        dialog.show();
    }

    private void mostrarDialogPedido(PedidoDTO pedido, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_pedido, null);
        builder.setView(dialogView);

        TextView tvNumeroPedido = dialogView.findViewById(R.id.tvNumPedidoDialog);
        TextView tvCliente = dialogView.findViewById(R.id.tvClientePedidoDialog);
        TextView tvData = dialogView.findViewById(R.id.tvDataPedidoDialog);
        TextView tvValor = dialogView.findViewById(R.id.tvValorTotalPedidoDialog);
        Button btnEditar = dialogView.findViewById(R.id.btnUpdatePedidoDialog);
        Button btnExcluir = dialogView.findViewById(R.id.btnExcluirPedidoDialog);
        Button btnFechar = dialogView.findViewById(R.id.btnClosePedidoDialog);

        tvNumeroPedido.setText(R.string.orderListLabel + " " + pedido.getNPedido());
        tvCliente.setText(R.string.orderListCustomerLabel + " " + pedido.getNomeCliente());
        tvData.setText(R.string.dataPedidoLabel + " " + pedido.getDataPedido());
        tvValor.setText(R.string.valorTotal + String.format(Locale.getDefault(), "%.2f", pedido.getValorTotal()));

        AlertDialog dialog = builder.create();

        btnFechar.setOnClickListener(v -> dialog.dismiss());

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormPedidoActivity.class);
            intent.putExtra("PEDIDO_ID", pedido.getNPedido());
            startActivity(intent);
            dialog.dismiss();
        });

        btnExcluir.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmar exclusão")
                    .setMessage("Deseja realmente excluir este pedido?")
                    .setPositiveButton("Sim", (d, which) -> {
                        pedidos.remove(position);
                        adapter.notifyItemRemoved(position);
                        dialog.dismiss();
                    })
                    .setNegativeButton("Não", null)
                    .show();
        });

        dialog.show();
    }
}
