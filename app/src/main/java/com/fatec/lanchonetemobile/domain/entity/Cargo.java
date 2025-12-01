package com.fatec.lanchonetemobile.domain.entity;

public class Cargo {
    private int id;
    private String nome;
    private double salario;
    private String descricao;

    public Cargo() {}
    public Cargo(int id) {
        this.id = id;
    }
    public Cargo(String nome, double salario, String descricao) {
        this.nome = nome;
        this.salario = salario;
        this.descricao = descricao;
    }
    public Cargo(int id, String nome, double salario, String descricao) {
        this.id = id;
        this.nome = nome;
        this.salario = salario;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getNome() {
        return nome;
    }
    public double getSalario() {
        return salario;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }

}
