package Business;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Interface.IPersistivel;
import Utils.Identificador.Id;

public class Curso implements IPersistivel {
    private int id;
    private String nome;
    private int numCreditos;
    private String departamento;
    private LocalDate dataCriacao;

    public Curso() {
    }

    public Curso(String nome, int numCreditos) {
        this.id = Id.gerar();
        this.nome = nome;
        this.numCreditos = numCreditos;
        this.dataCriacao = LocalDate.now();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumCreditos() {
        return this.numCreditos;
    }

    public void setNumCreditos(int numCreditos) {
        this.numCreditos = numCreditos;
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
    
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return String.format(
                "ID do Curso: %d%nNome: %s%nNúmero de Créditos: %d%nDepartamento: %s%nData de Criação: %s",
                this.id,
                this.nome,
                this.numCreditos,
                this.departamento != null ? this.departamento : "Sem departamento",
                this.dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    @Override
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