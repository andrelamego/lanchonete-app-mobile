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

import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.adapters.adapter.PedidoAdapter;
import com.fatec.lanchonetemobile.application.dto.PedidoDTO;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GerenciarPedidoActivity extends AppCompatActivity {

    private MaterialCardView btnAddPedido;
    private MaterialCardView btnBuscarPedido;

    private ImageView ivBack;

    private RecyclerView rvPedidos;

    private PedidoAdapter adapter;
    private List<PedidoDTO> pedidos = new ArrayList<>();

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

        btnAddPedido = findViewById(R.id.btnAddPedido);
        btnBuscarPedido = findViewById(R.id.btnBuscarPedido);

        rvPedidos = findViewById(R.id.rvPedidos);
        rvPedidos.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PedidoAdapter(pedidos, (pedido, position) -> mostrarDialogPedido(pedido, position));

        ivBack = findViewById(R.id.ivBackGerenciarPedido);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        btnAddPedido.setOnClickListener(e -> {
            Intent intent = new Intent(this, FormPedidoActivity.class);
            startActivity(intent);
        });

        carregarPedidos();
    }

    //TODO: IMPLEMENTAR CARREGAMENTO DE PEDIDOS
    private void carregarPedidos(){

    }

    private void mostrarDialogPedido(PedidoDTO pedido, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_pedido_detalhes, null);
        builder.setView(dialogView);

        TextView tvNumeroPedido = dialogView.findViewById(R.id.tvNumPedido);
        TextView tvCliente = dialogView.findViewById(R.id.tvClientePedido);
        TextView tvData = dialogView.findViewById(R.id.tvDataPedido);
        TextView tvValor = dialogView.findViewById(R.id.tvTotalPedido);
        Button btnEditar = dialogView.findViewById(R.id.btnEditarPedido);
        Button btnExcluir = dialogView.findViewById(R.id.btnDeletarPedido);
        Button btnFechar = dialogView.findViewById(R.id.btnClosePedido);

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
