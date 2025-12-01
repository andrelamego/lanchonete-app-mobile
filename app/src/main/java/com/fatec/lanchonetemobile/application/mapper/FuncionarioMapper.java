package com.fatec.lanchonetemobile.application.mapper;

import com.fatec.lanchonetemobile.application.dto.FuncionarioDTO;
import com.fatec.lanchonetemobile.domain.entity.Funcionario;

public class FuncionarioMapper {
    public Funcionario toEntity(FuncionarioDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Funcionario(
                dto.id(),
                dto.nome(),
                dto.tel(),
                dto.email(),
                dto.dataContrato(),
                dto.cargo()
        );
    }

    public FuncionarioDTO toDTO(Funcionario funcionario) {
        if (funcionario == null) {
            return null;
        }

        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getTel(),
                funcionario.getEmail(),
                funcionario.getDataContrato(),
                funcionario.getCargo()
        );
    }
}
