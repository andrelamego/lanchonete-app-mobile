package com.fatec.lanchonetemobile.adapters.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.FornecedorDTO;

import java.util.List;

public class FornecedorAdapter extends RecyclerView.Adapter<FornecedorAdapter.FornecedorViewHolder> {
    public interface OnFornecedorClickListener {
        void onFornecedorClick(FornecedorDTO fornecedor, int position);
    }

    private final List<FornecedorDTO> fornecedors;
    private final FornecedorAdapter.OnFornecedorClickListener listener;

    public FornecedorAdapter(List<FornecedorDTO> fornecedors, FornecedorAdapter.OnFornecedorClickListener listener) {
        this.fornecedors = fornecedors;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FornecedorAdapter.FornecedorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_list_fornecedor, parent, false);
        return new FornecedorAdapter.FornecedorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FornecedorAdapter.FornecedorViewHolder holder, int position) {
        FornecedorDTO fornecedor = fornecedors.get(position);
        holder.bind(fornecedor, position, listener);
    }

    @Override
    public int getItemCount() {
        return fornecedors.size();
    }

    public static class FornecedorViewHolder extends RecyclerView.ViewHolder {

        TextView tvIDFornecedor;
        TextView tvNomeFornecedor;
        TextView tvTelefoneFornecedor;
        TextView tvCNPJFornecedor;
        TextView tvLogradouroFornecedor;
        TextView tvNumeroFornecedor;
        TextView tvCEPFornecedor;
        TextView tvComplementoFornecedor;

        public FornecedorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDFornecedor = itemView.findViewById(R.id.tvIDFornecedor);
            tvNomeFornecedor = itemView.findViewById(R.id.tvNomeFornecedor);
            tvTelefoneFornecedor = itemView.findViewById(R.id.tvTelefoneFornecedor);
            tvCNPJFornecedor = itemView.findViewById(R.id.tvCPFFornecedor);
            tvLogradouroFornecedor = itemView.findViewById(R.id.tvLogradouroFornecedor);
            tvNumeroFornecedor = itemView.findViewById(R.id.tvNumeroFornecedor);
            tvCEPFornecedor = itemView.findViewById(R.id.tvCEPFornecedor);
            tvComplementoFornecedor = itemView.findViewById(R.id.tvComplementoFornecedor);
        }

        public void bind(FornecedorDTO fornecedorDTO, int position, FornecedorAdapter.OnFornecedorClickListener listener) {
            tvIDFornecedor.setText("ID: " + fornecedorDTO.getId());
            tvNomeFornecedor.setText("Nome: " + fornecedorDTO.getNome());
            tvTelefoneFornecedor.setText("Telefone: " + fornecedorDTO.getTel());
            tvCNPJFornecedor.setText("CNPJ: " + fornecedorDTO.getCnpj());
            tvLogradouroFornecedor.setText("Logradouro: " + fornecedorDTO.getLogradouro());
            tvNumeroFornecedor.setText("Numero: " + fornecedorDTO.getNumero());
            tvCEPFornecedor.setText("CEP: " + fornecedorDTO.getCep());
            tvComplementoFornecedor.setText("Complemento: " + fornecedorDTO.getComplemento());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onFornecedorClick(fornecedorDTO, position);
                }
            });
        }
    }
}
