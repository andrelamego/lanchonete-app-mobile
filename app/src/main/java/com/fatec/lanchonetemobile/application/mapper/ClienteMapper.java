package com.fatec.lanchonetemobile.application.mapper;

import com.fatec.lanchonetemobile.application.dto.ClienteDTO;
import com.fatec.lanchonetemobile.domain.entity.Cliente;

public class ClienteMapper {
    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Cliente(
                dto.id(),
                dto.nome(),
                dto.tel(),
                dto.cpf(),
                dto.logradouro(),
                dto.numero(),
                dto.cep(),
                dto.complemento()
        );
    }

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTel(),
                cliente.getCpf(),
                cliente.getLogradouro(),
                cliente.getNumero(),
                cliente.getCep(),
                cliente.getComplemento()
        );
    }
}
