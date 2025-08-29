package Business;

import java.time.LocalDate;
import java.util.ArrayList;

import Interface.IGerenciavel;

public class Curriculo implements IGerenciavel<String> {

    private int id; // TODO
    private Curso curso;
    private LocalDate dataCriacao;
    private ArrayList<String> disciplinas;

    public Curriculo() {
        disciplinas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate dataCriacao() {
        return dataCriacao;
    }

    public ArrayList<String> getDisciplinas() {
        return disciplinas;
    }

    @Override
    public boolean addDisciplina(String nomeDisciplina) {
        return this.disciplinas.add(nomeDisciplina);
    }

    @Override
    public String removerDisciplina(String nomeDisciplina) {
        if (this.disciplinas.remove(nomeDisciplina)) {
            return nomeDisciplina;
        } else {
            throw new IllegalArgumentException("Disciplina inexistente no currículo.");
        }
    }
}