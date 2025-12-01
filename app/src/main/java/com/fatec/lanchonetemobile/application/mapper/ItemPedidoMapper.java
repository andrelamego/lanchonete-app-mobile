package com.fatec.lanchonetemobile.application.mapper;

import com.fatec.lanchonetemobile.application.dto.ItemPedidoDTO;
import com.fatec.lanchonetemobile.domain.entity.ItemPedido;

public class ItemPedidoMapper {
    public ItemPedidoDTO toDTO(ItemPedido itemPedido) {
        if(itemPedido == null)
            return null;

        return new ItemPedidoDTO(
                itemPedido.getNumPedido(),
                itemPedido.getProduto(),
                itemPedido.getQtd(),
                itemPedido.getValorUnit(),
                itemPedido.getValorTotal()
        );
    }

    public ItemPedido toEntity(ItemPedidoDTO dto) {
        if(dto == null)
            return null;

        return new ItemPedido(
                dto.nPedido(),
                dto.produto(),
                dto.qtd(),
                dto.valorUnit(),
                dto.valorTotal()
        );
    }
}
