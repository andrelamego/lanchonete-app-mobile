package com.fatec.lanchonetemobile.domain.entity;

public class Cliente {
    private int id;
    private String nome;
    private String tel;
    private String cpf;
    private String logradouro;
    private int numero;
    private String cep;
    private String complemento;

    public Cliente(){}

    public Cliente(int id) {
        this.id = id;
    }

    public Cliente(int id, String nome, String tel, String cpf, String logradouro, int numero, String cep, String complemento) {
        this.id = id;
        this.nome = nome;
        this.tel = tel;
        this.cpf = cpf;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.complemento = complemento;
    }

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
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}

