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

import com.fatec.lanchonetemobile.LanchoneteApp;
import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.adapters.adapter.CargoAdapter;
import com.fatec.lanchonetemobile.adapters.adapter.PedidoAdapter;
import com.fatec.lanchonetemobile.application.dto.CargoDTO;
import com.fatec.lanchonetemobile.application.dto.PedidoDTO;
import com.fatec.lanchonetemobile.application.facade.CadastroFacade;
import com.fatec.lanchonetemobile.config.AppBuilder;
import com.google.android.material.card.MaterialCardView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        rvCargos = findViewById(R.id.rvListaGerenciarCargo);

        ivBack = findViewById(R.id.ivBackGerenciarCargo);
        ivBack.setOnClickListener(e -> {
            finish();
        });

        cargos = new ArrayList<>();

        rvCargos = findViewById(R.id.rvListaGerenciarCargo);
        rvCargos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CargoAdapter(cargos, (cargo, position) -> mostrarDialogPedido(cargo, position));
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

    private void mostrarDialogPedido(CargoDTO cargo, int position) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_pedido_detalhes, null);
//        builder.setView(dialogView);
//
//        TextView tvNumeroPedido = dialogView.findViewById(R.id.tvNumPedido);
//        TextView tvCliente = dialogView.findViewById(R.id.tvClientePedido);
//        TextView tvData = dialogView.findViewById(R.id.tvDataPedido);
//        TextView tvValor = dialogView.findViewById(R.id.tvTotalPedido);
//        Button btnEditar = dialogView.findViewById(R.id.btnEditarPedido);
//        Button btnExcluir = dialogView.findViewById(R.id.btnDeletarPedido);
//        Button btnFechar = dialogView.findViewById(R.id.btnClosePedido);
//
//        tvNumeroPedido.setText(R.string.orderListLabel + " " + pedido.getNPedido());
//        tvCliente.setText(R.string.orderListCustomerLabel + " " + pedido.getNomeCliente());
//        tvData.setText(R.string.dataPedidoLabel + " " + pedido.getDataPedido());
//        tvValor.setText(R.string.valorTotal + String.format(Locale.getDefault(), "%.2f", pedido.getValorTotal()));
//
//        AlertDialog dialog = builder.create();
//
//        btnFechar.setOnClickListener(v -> dialog.dismiss());
//
//        btnEditar.setOnClickListener(v -> {
//            Intent intent = new Intent(this, FormPedidoActivity.class);
//            intent.putExtra("PEDIDO_ID", pedido.getNPedido());
//            startActivity(intent);
//            dialog.dismiss();
//        });
//
//        btnExcluir.setOnClickListener(v -> {
//            new AlertDialog.Builder(this)
//                    .setTitle("Confirmar exclusão")
//                    .setMessage("Deseja realmente excluir este pedido?")
//                    .setPositiveButton("Sim", (d, which) -> {
//                        pedidos.remove(position);
//                        adapter.notifyItemRemoved(position);
//                        dialog.dismiss();
//                    })
//                    .setNegativeButton("Não", null)
//                    .show();
//        });
//
//        dialog.show();
    }
}
