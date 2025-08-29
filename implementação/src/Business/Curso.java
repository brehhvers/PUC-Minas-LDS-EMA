package Business;

import java.time.LocalDate;

import Utils.Id;

public class Curso {
    private int id;
    private String nome;
    private int numCreditos;
    private String departamento;
    private LocalDate dataCriacao;
    
    public Curso(String nome, int numCreditos) {
        this.id = Id.gerar();
        this.nome = nome;
        this.numCreditos = numCreditos;
        this.dataCriacao = LocalDate.now();
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public int getNumCreditos() {
        return this.numCreditos;
    }

    public String getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public LocalDate getDataCriacao() {
        return this.dataCriacao;
    }
}