package com.fatec.lanchonetemobile.application.usecase.pedido;

import com.fatec.lanchonetemobile.domain.entity.Cliente;
import com.fatec.lanchonetemobile.domain.entity.ItemPedido;
import com.fatec.lanchonetemobile.domain.entity.Pedido;
import com.fatec.lanchonetemobile.domain.entity.Produto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ManterPedidoUseCase {
    public void criarPedido(Pedido pedido, Cliente cliente, List<ItemPedido> itensPedido) {
        pedido.setItensPedido(itensPedido);
        pedido.setData(Date.valueOf(String.valueOf(LocalDate.now())));
        pedido.setStatus("Criado");
        pedido.setCliente(cliente);

        pedido.calcularValorTotal();
    }

    public void adicionarItem(Pedido pedido, Produto produto, ItemPedido item) throws IllegalArgumentException{
        if(item.getQtd() > produto.getQntdEstoq()){
            throw new IllegalArgumentException("Quantidade insuficiente no estoque");
        }

        item.setValorUnit(produto.getValorUn());
        item.calcularValorTotal();

        pedido.adicionarItem(item);
        produto.atualizarQtdEstoque(item.getQtd());
    }

    public void removerItem(Pedido pedido, ItemPedido item) {
        pedido.removerItem(item);
    }

    public void atualizarQuantidadeItem(Pedido pedido, ItemPedido item, int novaQtd) {
        pedido.atualizarQuantidadeItem(item, novaQtd);
    }

    public void atualizarStatus(Pedido pedido, String novoStatus) {
        pedido.atualizarStatus(novoStatus);
    }
}
