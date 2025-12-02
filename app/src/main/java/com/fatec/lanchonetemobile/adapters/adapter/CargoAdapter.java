package com.fatec.lanchonetemobile.adapters.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatec.lanchonetemobile.R;
import com.fatec.lanchonetemobile.application.dto.CargoDTO;
import com.fatec.lanchonetemobile.application.dto.PedidoDTO;

import java.util.List;

public class CargoAdapter extends RecyclerView.Adapter<CargoAdapter.CargoViewHolder> {
    public interface OnCargoClickListener {
        void onCargoClick(CargoDTO cargo, int position);
    }

    private final List<CargoDTO> cargos;
    private final CargoAdapter.OnCargoClickListener listener;

    public CargoAdapter(List<CargoDTO> cargos, CargoAdapter.OnCargoClickListener listener) {
        this.cargos = cargos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CargoAdapter.CargoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_list_cargo, parent, false);
        return new CargoAdapter.CargoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CargoAdapter.CargoViewHolder holder, int position) {
        CargoDTO cargo = cargos.get(position);
        holder.bind(cargo, position, listener);
    }

    @Override
    public int getItemCount() {
        return cargos.size();
    }

    public static class CargoViewHolder extends RecyclerView.ViewHolder {

        TextView tvIDCargo;
        TextView tvNomeCargo;
        TextView tvDescricaoCargo;

        public CargoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDCargo = itemView.findViewById(R.id.tvIDCargo);
            tvNomeCargo = itemView.findViewById(R.id.tvNomeCargo);
            tvDescricaoCargo = itemView.findViewById(R.id.tvDescricaoCargo);
        }

        public void bind(CargoDTO cargoDTO, int position, CargoAdapter.OnCargoClickListener listener) {
            tvIDCargo.setText("ID: " + cargoDTO.getId());
            tvNomeCargo.setText("Nome: " + cargoDTO.getNome());
            tvDescricaoCargo.setText("Descrição: " + cargoDTO.getDescricao());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCargoClick(cargoDTO, position);
                }
            });
        }
    }
}