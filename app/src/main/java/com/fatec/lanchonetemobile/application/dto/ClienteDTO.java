package com.fatec.lanchonetemobile.application.dto;

public record ClienteDTO(
        int id,
        String nome,
        String tel,
        String cpf,
        String logradouro,
        int numero,
        String cep,
        String complemento
) {
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTel() {
        return tel;
    }

    public String getCpf() {
        return cpf;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public String getCep() {
        return cep;
    }

    public String getComplemento() {
        return complemento;
    }
}
