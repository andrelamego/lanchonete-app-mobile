package com.fatec.lanchonetemobile.application.facade;

import com.fatec.lanchonetemobile.application.dto.ItemPedidoDTO;
import com.fatec.lanchonetemobile.application.dto.PedidoDTO;
import com.fatec.lanchonetemobile.application.mapper.ItemPedidoMapper;
import com.fatec.lanchonetemobile.application.mapper.PedidoMapper;
import com.fatec.lanchonetemobile.application.service.ClienteService;
import com.fatec.lanchonetemobile.application.service.ItemPedidoService;
import com.fatec.lanchonetemobile.application.service.PedidoService;
import com.fatec.lanchonetemobile.application.service.ProdutoService;
import com.fatec.lanchonetemobile.application.usecase.pedido.ManterPedidoUseCase;
import com.fatec.lanchonetemobile.domain.entity.Cliente;
import com.fatec.lanchonetemobile.domain.entity.ItemPedido;
import com.fatec.lanchonetemobile.domain.entity.Pedido;
import com.fatec.lanchonetemobile.domain.entity.Produto;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoFacadeImpl implements PedidoFacade{

    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;
    private final ProdutoService produtoService;
    private final ClienteService clienteService;

    private final ManterPedidoUseCase manterPedidoUC = new ManterPedidoUseCase();
    private final PedidoMapper pedidoMapper = new PedidoMapper();
    private final ItemPedidoMapper itemPedidoMapper = new ItemPedidoMapper();

    public PedidoFacadeImpl(PedidoService pedidoService,
                            ItemPedidoService itemPedidoService,
                            ProdutoService produtoService,
                            ClienteService clienteService) {
        this.pedidoService = pedidoService;
        this.itemPedidoService = itemPedidoService;
        this.produtoService = produtoService;
        this.clienteService = clienteService;
    }

    @Override
    public PedidoDTO criarPedido(PedidoDTO pedidoDTO) throws SQLException {
        Pedido pedido = new Pedido();
        Cliente cliente = clienteService.buscarCliente(pedidoDTO.cliente().getId());

        manterPedidoUC.criarPedido(pedido, cliente, pedidoDTO.itensPedido());

        pedido.setnPedido(pedidoService.criarPedido(pedido));
        return pedidoMapper.toDTO(pedido);
    }

    @Override
    public void atualizarPedido(PedidoDTO pedidoDTO) throws SQLException {
        pedidoService.atualizarPedido(pedidoMapper.toEntity(pedidoDTO));
    }

    @Override
    public PedidoDTO buscarPedido(int nPedido) throws SQLException {
        Pedido pedido = pedidoService.buscarPedido(nPedido);
        return pedidoMapper.toDTO(pedido);
    }

    @Override
    public List<PedidoDTO> listarPedidos() throws SQLException {
        return pedidoService.listarPedidos().stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoDTO cancelarPedido(int nPedido) throws SQLException {
        Pedido pedido = pedidoService.buscarPedido(nPedido);

        manterPedidoUC.atualizarStatus(pedido, "Cancelado");

        pedidoService.atualizarPedido(pedido);
        return pedidoMapper.toDTO(pedido);
    }

    @Override
    public PedidoDTO atualizarStatus(PedidoDTO pedidoDTO) throws SQLException {
        Pedido pedido = pedidoService.buscarPedido(pedidoDTO.nPedido());

        manterPedidoUC.atualizarStatus(pedido, pedidoDTO.status());

        pedidoService.atualizarPedido(pedido);
        return pedidoMapper.toDTO(pedido);
    }

    @Override
    public PedidoDTO adicionarItem(ItemPedidoDTO itemPedidoDTO) throws SQLException, IllegalArgumentException {
        Pedido pedido = pedidoService.buscarPedido(itemPedidoDTO.nPedido());
        Produto produto = produtoService.buscarProduto(itemPedidoDTO.produto().getId());
        ItemPedido item = new ItemPedido(itemPedidoDTO.nPedido(), produto, itemPedidoDTO.qtd());

        manterPedidoUC.adicionarItem(pedido, produto, item);

        produtoService.atualizarProduto(produto);
        itemPedidoService.adicionarItem(item);
        pedidoService.atualizarPedido(pedido);
        return pedidoMapper.toDTO(pedido);
    }

    @Override
    public PedidoDTO removerItem(ItemPedidoDTO itemPedidoDTO) throws SQLException {
        Pedido pedido = pedidoService.buscarPedido(itemPedidoDTO.nPedido());
        ItemPedido item = itemPedidoService.buscarItem(itemPedidoDTO.nPedido(), itemPedidoDTO.produto());

        manterPedidoUC.removerItem(pedido, item);

        itemPedidoService.removerItem(item);
        pedidoService.atualizarPedido(pedido);
        return pedidoMapper.toDTO(pedido);
    }

    @Override
    public PedidoDTO atualizarQuantidadeItem(ItemPedidoDTO itemPedidoDTO) throws SQLException {
        ItemPedido item =  itemPedidoMapper.toEntity(itemPedidoDTO);
        Pedido pedido = pedidoService.buscarPedido(itemPedidoDTO.nPedido());
        
        manterPedidoUC.adicionarItem(pedido, item.getProduto(), item);
        manterPedidoUC.atualizarQuantidadeItem(pedido, item, itemPedidoDTO.qtd());

        itemPedidoService.atualizarQuantidade(item);
        pedidoService.atualizarPedido(pedido);
        return pedidoMapper.toDTO(pedido);
    }

    @Override
    public List<ItemPedidoDTO> listarItens() throws SQLException {
        return itemPedidoService.listarItens().stream()
                .map(itemPedidoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ItemPedidoDTO> listarItensPorNumPedido(int nPedido) throws SQLException {
        return itemPedidoService.listarItensPorNumPedido(nPedido).stream()
                .map(itemPedidoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
