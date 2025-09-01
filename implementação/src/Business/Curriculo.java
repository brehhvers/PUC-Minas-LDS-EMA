package Business;

import java.time.LocalDate;
import java.util.ArrayList;

import Interface.IGerenciavel;
import Utils.Identificador.Id;

public class Curriculo implements IGerenciavel<String, String> {
    private int id;
    private Curso curso;
    private LocalDate dataCriacao;
    private ArrayList<String> disciplinas;

    public Curriculo(Curso curso) {
        this.id = Id.gerar();
        this.curso = curso;
        this.dataCriacao = LocalDate.now();
        this.disciplinas = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public LocalDate dataCriacao() {
        return this.dataCriacao;
    }

    public ArrayList<String> getDisciplinas() {
        return this.disciplinas;
    }

    @Override
    public synchronized boolean addDisciplina(String nomeDisciplina) {
        return this.disciplinas.add(nomeDisciplina);
    }

    @Override
    public synchronized  String removerDisciplina(String nomeDisciplina) {
        if (this.disciplinas.remove(nomeDisciplina)) {
            return nomeDisciplina;
        } else {
            throw new IllegalArgumentException("Disciplina inexistente no currículo.");
        }
    }
}