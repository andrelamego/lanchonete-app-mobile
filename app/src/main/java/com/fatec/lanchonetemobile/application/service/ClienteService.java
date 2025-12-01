package com.fatec.lanchonetemobile.application.service;

import com.fatec.lanchonetemobile.application.exception.ClienteInvalidoException;
import com.fatec.lanchonetemobile.application.exception.ClienteNaoEncontradoException;
import com.fatec.lanchonetemobile.application.repository.RepositoryNoReturn;
import com.fatec.lanchonetemobile.domain.entity.Cliente;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {

    private final RepositoryNoReturn<Cliente> repository;

    public ClienteService(RepositoryNoReturn<Cliente> repository) {
        this.repository = repository;
    }

    public void criarCliente(Cliente cliente) throws SQLException, ClienteInvalidoException {
        if(!validarCliente(cliente))
            throw new ClienteInvalidoException("Cliente já cadastrado");

        repository.salvar(cliente);
    }

    public void atualizarCliente(Cliente cliente) throws SQLException {
        repository.atualizar(cliente);
    }

    public void excluirCliente(Cliente cliente) throws SQLException {
        repository.excluir(cliente);
    }

    public Cliente buscarCliente(int clienteId) throws SQLException,ClienteNaoEncontradoException {
        Cliente cliente = repository.buscarPorID(new Cliente(clienteId));

        if(cliente == null)
            throw new ClienteNaoEncontradoException("Cliente não encontrado");

        return cliente;
    }

    public List<Cliente> listarClientes() throws SQLException {
        return repository.listar();
    }

    public boolean validarCliente(Cliente cliente) throws SQLException {
        try{
            buscarDuplicata(cliente);
            return true;
        } catch(ClienteInvalidoException e){
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    private void buscarDuplicata(Cliente cliente) throws SQLException, ClienteInvalidoException {
        if(repository.buscarPorChaveSecundaria(cliente) != null)
            throw new ClienteInvalidoException("Cliente inválido");
    }
}
