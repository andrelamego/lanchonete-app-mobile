package com.fatec.lanchonetemobile.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fatec.lanchonetemobile.adapters.repository.*;
import com.fatec.lanchonetemobile.adapters.repository.db.*;
import com.fatec.lanchonetemobile.application.facade.*;
import com.fatec.lanchonetemobile.application.repository.*;
import com.fatec.lanchonetemobile.application.service.*;
import com.fatec.lanchonetemobile.domain.entity.*;

import java.sql.SQLException;

public class AppBuilder {

    private final IDBConnection c;

    // Facades
    private final CadastroFacade cadastroFacade;
    private final PedidoFacade pedidoFacade;

    // Services
    private final ClienteService clienteService;
    private final PedidoService pedidoService;
    private final CargoService cargoService;
    private final CategoriaService categoriaService;
    private final FornecedorService fornecedorService;
    private final FuncionarioService funcionarioService;
    private final ProdutoService produtoService;
    private final ItemPedidoService itemPedidoService;

    // Repositories
    private final RepositoryNoReturn<Cliente> clienteRepository;
    private final RepositoryReturn<Pedido> pedidoRepository;
    private final RepositoryNoReturn<Cargo> cargoRepository;
    private final RepositoryNoReturn<Categoria> categoriaRepository;
    private final RepositoryNoReturn<Fornecedor> fornecedorRepository;
    private final RepositoryNoReturn<Funcionario> funcionarioRepository;
    private final RepositoryReturn<Produto> produtoRepository;
    private final RepositoryListById<ItemPedido> itemPedidoRepository;

    public AppBuilder(Context context) {
        c = new SQLiteConnection(context);
        SQLiteDatabase db = c.getConnection();

        // instancia repositórios
        clienteRepository = new ClienteRepository(db);
        pedidoRepository = new PedidoRepository(db);
        cargoRepository = new CargoRepository(db);
        categoriaRepository = new CategoriaRepository(db);
        fornecedorRepository = new FornecedorRepository(db);
        funcionarioRepository = new FuncionarioRepository(db);
        produtoRepository = new ProdutoRepository(db);
        itemPedidoRepository = new ItemPedidoRepository(db);

        // instancia services
        clienteService = new ClienteService(clienteRepository);
        pedidoService = new PedidoService(pedidoRepository);
        cargoService = new CargoService(cargoRepository);
        categoriaService = new CategoriaService(categoriaRepository);
        fornecedorService = new FornecedorService(fornecedorRepository);
        funcionarioService = new FuncionarioService(funcionarioRepository);
        produtoService = new ProdutoService(produtoRepository);
        itemPedidoService = new ItemPedidoService(itemPedidoRepository);

        // instancia facades
        cadastroFacade = new CadastroFacadeImpl(clienteService, funcionarioService, produtoService, cargoService, categoriaService, fornecedorService);
        pedidoFacade = new PedidoFacadeImpl(pedidoService, itemPedidoService, produtoService, clienteService);
    }

    // getters
    public CadastroFacade getCadastroFacade() {
        return cadastroFacade;
    }

    public PedidoFacade getPedidoFacade() {
        return pedidoFacade;
    }
}
