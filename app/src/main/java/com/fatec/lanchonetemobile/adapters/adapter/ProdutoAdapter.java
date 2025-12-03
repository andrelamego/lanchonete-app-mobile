package com.fatec.lanchonetemobile.adapters.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.ProdutoDTO;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {
    public interface OnProdutoClickListener {
        void onProdutoClick(ProdutoDTO produto, int position);
    }

    private final List<ProdutoDTO> produtos;
    private final ProdutoAdapter.OnProdutoClickListener listener;

    public ProdutoAdapter(List<ProdutoDTO> produtos, ProdutoAdapter.OnProdutoClickListener listener) {
        this.produtos = produtos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProdutoAdapter.ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_list_produto, parent, false);
        return new ProdutoAdapter.ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoAdapter.ProdutoViewHolder holder, int position) {
        ProdutoDTO produto = produtos.get(position);
        holder.bind(produto, position, listener);
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {

        TextView tvIDProduto;
        TextView tvNomeProduto;
        TextView tvValorUnitProduto;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDProduto = itemView.findViewById(R.id.tvIDProduto);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvValorUnitProduto = itemView.findViewById(R.id.tvValorUnitProduto);
        }

        public void bind(ProdutoDTO produtoDTO, int position, ProdutoAdapter.OnProdutoClickListener listener) {
            tvIDProduto.setText("ID: " + produtoDTO.getId());
            tvNomeProduto.setText("Nome: " + produtoDTO.getNome());
            tvValorUnitProduto.setText("ValorUnit: " + produtoDTO.getValorUn());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProdutoClick(produtoDTO, position);
                }
            });
        }
    }
}
