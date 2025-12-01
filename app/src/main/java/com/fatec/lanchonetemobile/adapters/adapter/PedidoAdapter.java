package com.fatec.lanchonetemobile.adapters.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.PedidoDTO;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {
    public interface OnPedidoClickListener {
        void onPedidoClick(PedidoDTO pedido, int position);
    }

    private final List<PedidoDTO> pedidos;
    private final OnPedidoClickListener listener;

    public PedidoAdapter(List<PedidoDTO> pedidos, OnPedidoClickListener listener) {
        this.pedidos = pedidos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_list_pedido, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        PedidoDTO pedido = pedidos.get(position);
        holder.bind(pedido, position, listener);
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {

        TextView tvNumeroPedido;
        TextView tvCliente;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumeroPedido = itemView.findViewById(R.id.tvNumeroPedido);
            tvCliente = itemView.findViewById(R.id.tvCliente);
        }

        public void bind(PedidoDTO pedido, int position, OnPedidoClickListener listener) {
            tvNumeroPedido.setText("PedidoDTO #" + pedido.getNPedido());
            tvCliente.setText("Cliente: " + pedido.getNomeCliente());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPedidoClick(pedido, position);
                }
            });
        }
    }
}