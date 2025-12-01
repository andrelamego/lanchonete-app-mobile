package com.fatec.lanchonetemobile.application.mapper;

import com.fatec.lanchonetemobile.application.dto.PedidoDTO;
import com.fatec.lanchonetemobile.domain.entity.Pedido;

public class PedidoMapper {
    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        return new PedidoDTO(
                pedido.getnPedido(),
                pedido.getValorTotal(),
                pedido.getItensPedido(),
                pedido.getData(),
                pedido.getStatus(),
                pedido.getCliente()
        );
    }

    public Pedido toEntity(PedidoDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Pedido(
                dto.nPedido(),
                dto.valorTotal(),
                dto.itensPedido(),
                dto.dataPedido(),
                dto.status(),
                dto.cliente()
        );
    }
}
