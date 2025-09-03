package Business;

import java.time.LocalDate;

import Utils.Identificador.Id;

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

    @Override
    public String toString() {
        return String.format(
                "ID do Curso: %d%nNome: %s%nNúmero de Créditos: %d%nDepartamento: %s%nData de Criação: %s",
                this.id,
                this.nome,
                this.numCreditos,
                this.departamento != null ? this.departamento : "Sem departamento",
                this.dataCriacao.toString());
    }

    public String toPersist() {
        return String.format(
                "%d;%s;%d;%s;%s",
                this.id,
                this.nome,
                this.numCreditos,
                this.departamento != null ? this.departamento : "SEM_DEPARTAMENTO",
                this.dataCriacao.toString());
    }
}