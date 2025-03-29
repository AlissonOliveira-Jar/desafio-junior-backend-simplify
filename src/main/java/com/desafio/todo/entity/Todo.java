package com.desafio.todo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "tb_todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;

    @Column(name = "nome", nullable = false)
    @NotBlank(message = "O nome não pode estar vazio.")
    private String nome;

    @Column(name = "descricao", nullable = false)
    @NotBlank(message = "A descrição não pode estar vazia.")
    private String descricao;

    @Column(name = "realizado", nullable = false)
    @NotNull(message = "O campo realizado não pode ser nulo.")
    private boolean realizado;

    @Column(name = "prioridade", nullable = false)
    @Min(value = 1, message = "A prioridade deve ser no mínimo 1.")
    private int prioridade;

    @Version
    private Integer version;

    public Todo() {
    }

    public Todo(String nome, String descricao, boolean realizado, int prioridade) {
        this.nome = nome;
        this.descricao = descricao;
        this.realizado = realizado;
        this.prioridade = prioridade;
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}