package com.fatec.lanchonetemobile.application.mapper;

import com.fatec.lanchonetemobile.application.dto.CategoriaDTO;
import com.fatec.lanchonetemobile.domain.entity.Categoria;

public class CategoriaMapper {
    public CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }

        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }

    public Categoria toEntity(CategoriaDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Categoria(
                dto.id(),
                dto.nome(),
                dto.descricao()
        );
    }
}
