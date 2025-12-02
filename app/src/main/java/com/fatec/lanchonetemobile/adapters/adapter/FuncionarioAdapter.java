package com.fatec.lanchonetemobile.adapters.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.FuncionarioDTO;

import java.util.List;

public class FuncionarioAdapter extends RecyclerView.Adapter<FuncionarioAdapter.FuncionarioViewHolder> {
    public interface OnFuncionarioClickListener {
        void onFuncionarioClick(FuncionarioDTO funcionario, int position);
    }

    private final List<FuncionarioDTO> funcionarios;
    private final FuncionarioAdapter.OnFuncionarioClickListener listener;

    public FuncionarioAdapter(List<FuncionarioDTO> funcionarios, FuncionarioAdapter.OnFuncionarioClickListener listener) {
        this.funcionarios = funcionarios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FuncionarioAdapter.FuncionarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_list_funcionario, parent, false);
        return new FuncionarioAdapter.FuncionarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FuncionarioAdapter.FuncionarioViewHolder holder, int position) {
        FuncionarioDTO funcionario = funcionarios.get(position);
        holder.bind(funcionario, position, listener);
    }

    @Override
    public int getItemCount() {
        return funcionarios.size();
    }

    public static class FuncionarioViewHolder extends RecyclerView.ViewHolder {

        TextView tvIDFuncionario;
        TextView tvNomeFuncionario;
        TextView tvTelefoneFuncionario;
        TextView tvEmailFuncionario;
        TextView tvDataContratoFuncionario;
        TextView tvCargoFuncionario;
        TextView tvSalarioFuncionario;

        public FuncionarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDFuncionario = itemView.findViewById(R.id.tvIDFuncionario);
            tvNomeFuncionario = itemView.findViewById(R.id.tvNomeFuncionario);
            tvTelefoneFuncionario = itemView.findViewById(R.id.tvTelefoneFuncionario);
            tvEmailFuncionario = itemView.findViewById(R.id.tvEmailFuncionario);
            tvDataContratoFuncionario = itemView.findViewById(R.id.tvDataContratoFunciorio);
            tvCargoFuncionario = itemView.findViewById(R.id.tvCargoFuncionario);
            tvSalarioFuncionario = itemView.findViewById(R.id.tvSalarioFuncionario);
        }

        public void bind(FuncionarioDTO funcionarioDTO, int position, FuncionarioAdapter.OnFuncionarioClickListener listener) {
            tvIDFuncionario.setText("ID: " + funcionarioDTO.getId());
            tvNomeFuncionario.setText("Nome: " + funcionarioDTO.getNome());
            tvTelefoneFuncionario.setText("Telefone: " + funcionarioDTO.getTel());
            tvEmailFuncionario.setText("Email: " + funcionarioDTO.getEmail());
            tvDataContratoFuncionario.setText("Data do Contrato: " + funcionarioDTO.getDataContrato());
            tvCargoFuncionario.setText("Cargo: " + funcionarioDTO.getCargo());
            tvSalarioFuncionario.setText("Salário: " + funcionarioDTO.getSalario());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onFuncionarioClick(funcionarioDTO, position);
                }
            });
        }
    }
}
