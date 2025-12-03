package com.fatec.lanchonetemobile.application.dto;

import com.fatec.lanchonetemobile.application.mapper.CargoMapper;
import com.fatec.lanchonetemobile.domain.entity.Cargo;

import java.sql.Date;

public record FuncionarioDTO(
        int id,
        String nome,
        String tel,
        String email,
        Date dataContrato,
        Cargo cargo
) {
    static CargoMapper cargoMapper = new CargoMapper();

    public int getId(){
        return id;
    }
    public String getNome() { return nome; }
    public String getTel() { return tel; }
    public String getEmail() { return email; }
    public Date getDataContrato() { return dataContrato; }
    public String getCargo() { return cargo.getNome(); }
    public double getSalario() { return cargo.getSalario(); }
    public CargoDTO getCargoDTO(){
        return cargoMapper.toDTO(cargo);
    }
}
