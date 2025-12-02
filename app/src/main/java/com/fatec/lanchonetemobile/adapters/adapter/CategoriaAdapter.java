package com.fatec.lanchonetemobile.adapters.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.CategoriaDTO;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {
    public interface OnCategoriaClickListener {
        void onCategoriaClick(CategoriaDTO categoria, int position);
    }

    private final List<CategoriaDTO> categorias;
    private final CategoriaAdapter.OnCategoriaClickListener listener;

    public CategoriaAdapter(List<CategoriaDTO> categorias, CategoriaAdapter.OnCategoriaClickListener listener) {
        this.categorias = categorias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoriaAdapter.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_list_categoria, parent, false);
        return new CategoriaAdapter.CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.CategoriaViewHolder holder, int position) {
        CategoriaDTO categoria = categorias.get(position);
        holder.bind(categoria, position, listener);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public static class CategoriaViewHolder extends RecyclerView.ViewHolder {

        TextView tvIDCategoria;
        TextView tvNomeCategoria;
        TextView tvDescricaoCategoria;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDCategoria = itemView.findViewById(R.id.tvIDCategoria);
            tvNomeCategoria = itemView.findViewById(R.id.tvNomeCategoria);
            tvDescricaoCategoria = itemView.findViewById(R.id.tvDescricaoCategoria);
        }

        public void bind(CategoriaDTO categoriaDTO, int position, CategoriaAdapter.OnCategoriaClickListener listener) {
            tvIDCategoria.setText("ID: " + categoriaDTO.getId());
            tvNomeCategoria.setText("Nome: " + categoriaDTO.getNome());
            tvDescricaoCategoria.setText("Descrição: " + categoriaDTO.getDescricao());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCategoriaClick(categoriaDTO, position);
                }
            });
        }
    }
}
