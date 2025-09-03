package Business;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Override
    public String toString() {
        String disciplinasInfo = "Nenhuma disciplina";

        if (!this.disciplinas.isEmpty()) {
            disciplinasInfo = String.join("\n", this.disciplinas);
        }

        return String.format(
                "ID do Currículo: %d%nCurso: %s%nData de Criação: %s%nDisciplinas:%n%s",
                this.id,
                curso.getNome(),
                this.dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                disciplinasInfo);
    }
}