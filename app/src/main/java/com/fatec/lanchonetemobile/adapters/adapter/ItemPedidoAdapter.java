package com.fatec.lanchonetemobile.adapters.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.ItemPedidoDTO;

import java.util.List;

public class ItemPedidoAdapter extends RecyclerView.Adapter<ItemPedidoAdapter.ItemPedidoViewHolder> {
    public interface OnItemPedidoClickListener {
        void onItemPedidoClick(ItemPedidoDTO itemPedido, int position);
    }

    private final List<ItemPedidoDTO> itensPedido;
    private final ItemPedidoAdapter.OnItemPedidoClickListener listener;

    public ItemPedidoAdapter(List<ItemPedidoDTO> itensPedido, ItemPedidoAdapter.OnItemPedidoClickListener listener) {
        this.itensPedido = itensPedido;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemPedidoAdapter.ItemPedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_list_item_pedido, parent, false);
        return new ItemPedidoAdapter.ItemPedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPedidoAdapter.ItemPedidoViewHolder holder, int position) {
        ItemPedidoDTO itemPedido = itensPedido.get(position);
        holder.bind(itemPedido, position, listener);
    }

    @Override
    public int getItemCount() {
        return itensPedido.size();
    }

    public static class ItemPedidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvQtdItemPedido;
        TextView tvValorUnitItemPedido;
        TextView tvValorTotalItemPedido;

        public ItemPedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQtdItemPedido = itemView.findViewById(R.id.tvQuantidadeItem);
            tvValorUnitItemPedido = itemView.findViewById(R.id.tvValorUnItem);
            tvValorTotalItemPedido = itemView.findViewById(R.id.tvValorTotalItem);
        }

        public void bind(ItemPedidoDTO itemPedidoDTO, int position, ItemPedidoAdapter.OnItemPedidoClickListener listener) {
            tvQtdItemPedido.setText("Quantidade: " + itemPedidoDTO.getQtd());
            tvValorUnitItemPedido.setText("Valor Unitário: " + itemPedidoDTO.getValorUnit());
            tvValorTotalItemPedido.setText("Valor Total: " + itemPedidoDTO.getValorTotal());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemPedidoClick(itemPedidoDTO, position);
                }
            });
        }
    }
}