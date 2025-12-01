package com.fatec.lanchonetemobile.application.mapper;

import com.fatec.lanchonetemobile.application.dto.CargoDTO;
import com.fatec.lanchonetemobile.domain.entity.Cargo;

public class CargoMapper {
    public CargoDTO toDTO(Cargo cargo) {
        if (cargo == null) {
            return null;
        }

        return new CargoDTO(
                cargo.getId(),
                cargo.getNome(),
                cargo.getSalario(),
                cargo.getDescricao()
        );
    }

    public Cargo toEntity(CargoDTO dto) {
        if (dto == null){
            return null;
        }

        return new Cargo(
                dto.id(),
                dto.nome(),
                dto.salario(),
                dto.descricao()
        );
    }
}
