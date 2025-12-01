package com.fatec.lanchonetemobile.application.repository;

import java.sql.SQLException;

public interface RepositoryReturn<T> extends RepositoryStrategy<T> {
    /**
     * Salva a entidade especificada no banco de dados.
     *
     * @param entidade Entidade a ser salva.
     * @exception SQLException exception lançada em caso de erro na operação com o banco de dados
     */
    int salvar(T entidade) throws SQLException;
}
