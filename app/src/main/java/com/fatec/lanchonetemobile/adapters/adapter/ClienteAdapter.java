package com.fatec.lanchonetemobile.adapters.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.ClienteDTO;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {
    public interface OnClienteClickListener {
        void onClienteClick(ClienteDTO cliente, int position);
    }

    private final List<ClienteDTO> clientes;
    private final ClienteAdapter.OnClienteClickListener listener;

    public ClienteAdapter(List<ClienteDTO> clientes, ClienteAdapter.OnClienteClickListener listener) {
        this.clientes = clientes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClienteAdapter.ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_list_cliente, parent, false);
        return new ClienteAdapter.ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteAdapter.ClienteViewHolder holder, int position) {
        ClienteDTO cliente = clientes.get(position);
        holder.bind(cliente, position, listener);
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public static class ClienteViewHolder extends RecyclerView.ViewHolder {

        TextView tvIDCliente;
        TextView tvNomeCliente;
        TextView tvTelefoneCliente;
        TextView tvCPFCliente;
        TextView tvLogradouroCliente;
        TextView tvNumeroCliente;
        TextView tvCEPCliente;
        TextView tvComplementoCliente;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDCliente = itemView.findViewById(R.id.tvIDCliente);
            tvNomeCliente = itemView.findViewById(R.id.tvNomeCliente);
            tvTelefoneCliente = itemView.findViewById(R.id.tvTelefoneCliente);
            tvCPFCliente = itemView.findViewById(R.id.tvCPFCliente);
            tvLogradouroCliente = itemView.findViewById(R.id.tvLogradouroCliente);
            tvNumeroCliente = itemView.findViewById(R.id.tvNumeroCliente);
            tvCEPCliente = itemView.findViewById(R.id.tvCEPCliente);
            tvComplementoCliente = itemView.findViewById(R.id.tvComplementoCliente);
        }

        public void bind(ClienteDTO clienteDTO, int position, ClienteAdapter.OnClienteClickListener listener) {
            tvIDCliente.setText("ID: " + clienteDTO.getId());
            tvNomeCliente.setText("Nome: " + clienteDTO.getNome());
            tvTelefoneCliente.setText("Telefone: " + clienteDTO.getTel());
            tvCPFCliente.setText("CPF: " + clienteDTO.getCpf());
            tvLogradouroCliente.setText("Logradouro: " + clienteDTO.getLogradouro());
            tvNumeroCliente.setText("Numero: " + clienteDTO.getNumero());
            tvCEPCliente.setText("CEP: " + clienteDTO.getCep());
            tvComplementoCliente.setText("Complemento: " + clienteDTO.getComplemento());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onClienteClick(clienteDTO, position);
                }
            });
        }
    }
}
