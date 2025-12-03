package com.fatec.lanchonetemobile.domain.entity;

import java.sql.Date;

public class Funcionario {
    private int id;
    private String nome;
    private String tel;
    private String email;
    private Date dataContrato;
    private Cargo cargo;

    public Funcionario(){}
    public Funcionario(int id) {
        this.id = id;
    }

    public Funcionario(int id, String nome, String tel, String email, Date dataContrato, Cargo cargo) {
        this.id = id;
        this.nome = nome;
        this.tel = tel;
        this.email = email;
        this.dataContrato = dataContrato;
        this.cargo = cargo;
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
    public String getEmail() {
        return email;
    }
    public Date getDataContrato() {
        return dataContrato;
    }
    public Cargo getCargo() {
        return cargo;
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
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDataContrato(Date dataContrat) {
        this.dataContrato = dataContrat;
    }
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}

