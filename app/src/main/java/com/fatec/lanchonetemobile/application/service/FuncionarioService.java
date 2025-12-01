package com.fatec.lanchonetemobile.application.service;

import com.fatec.lanchonetemobile.application.exception.FuncionarioInvalidoException;
import com.fatec.lanchonetemobile.application.exception.FuncionarioNaoEncontradoException;
import com.fatec.lanchonetemobile.application.repository.RepositoryNoReturn;
import com.fatec.lanchonetemobile.domain.entity.Funcionario;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioService {
    private final RepositoryNoReturn<Funcionario> repository;

    public FuncionarioService(RepositoryNoReturn<Funcionario> repository) {
        this.repository = repository;
    }

    public void criarFuncionario(Funcionario funcionario) throws SQLException, FuncionarioInvalidoException {
        if(!validarFuncionario(funcionario))
            throw new FuncionarioInvalidoException("Funcionário já cadastrado");

        repository.salvar(funcionario);
    }

    public void atualizarFuncionario(Funcionario funcionario) throws SQLException {
        repository.atualizar(funcionario);
    }

    public void excluirFuncionario(Funcionario funcionario) throws SQLException {
        repository.excluir(funcionario);
    }

    public Funcionario buscarFuncionario(int idFuncionario) throws SQLException, FuncionarioNaoEncontradoException {
        Funcionario funcionario = repository.buscarPorID(new Funcionario(idFuncionario));

        if(funcionario == null)
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado");

        return funcionario;
    }

    public List<Funcionario> listarFuncionarios() throws SQLException {
        return repository.listar();
    }

    public boolean validarFuncionario(Funcionario funcionario) throws SQLException {
        try{
            buscarDuplicata(funcionario);
            return true;
        } catch(FuncionarioInvalidoException e){
            return false;
        }
    }

    private void buscarDuplicata(Funcionario funcionario) throws SQLException, FuncionarioInvalidoException {
        if(repository.buscarPorChaveSecundaria(funcionario) != null)
            throw new FuncionarioInvalidoException("Funcionário inválido");
    }
}
