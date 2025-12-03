package com.fatec.lanchonetemobile.application.dto;

import com.fatec.lanchonetemobile.application.mapper.ClienteMapper;
import com.fatec.lanchonetemobile.domain.entity.Cliente;
import com.fatec.lanchonetemobile.domain.entity.ItemPedido;

import java.util.Date;
import java.util.List;

public record PedidoDTO(
        int nPedido,
        double valorTotal,
        List<ItemPedido> itensPedido,
        Date dataPedido,
        String status,
        Cliente cliente
) {
        static ClienteMapper clienteMapper = new ClienteMapper();

        public int getNPedido() {
                return nPedido;
        }
        public double getValorTotal(){
                return valorTotal;
        }
        public Date getDataPedido(){
                return dataPedido;
        }
        public String getStatus(){
                return status;
        }
        public int getIdCliente(){
                return cliente.getId();
        }
        public String getNomeCliente(){
                return cliente.getNome();
        }
        public ClienteDTO getClienteDTO(){
                return clienteMapper.toDTO(cliente);
        }
        
}
