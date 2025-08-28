package Business;

import java.time.LocalDate;
import java.util.ArrayList;

import Business.Pessoa.Aluno;

public class PlanoDeEnsino implements Efetivavel {
    private int id;
    private int ano;
    private int semestre;
    private LocalDate dataCriacao;
    private StatusPlano statusPlano;
    private Aluno aluno;
    private ArrayList<Disciplina> disciplinas;

    public PlanoDeEnsino(Aluno aluno) {
        this.aluno = aluno;
        this.disciplinas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }


    public StatusPlano getStatusPlano() {
        return statusPlano;
    }

    public void setStatusPlano(StatusPlano statusPlano) {
        this.statusPlano = statusPlano;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public ArrayList<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(ArrayList<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public boolean addDisciplina(Disciplina disciplina) {
        // implementar regra de negocio
        return this.disciplinas.add(disciplina);
    }

    public Disciplina removerAluno(int idDisciplina) {
        Disciplina disciplina = this.disciplinas.stream().filter(d -> d.getId() == idDisciplina).findFirst().orElse(null);

        if (disciplina != null) {
            this.disciplinas.remove(idDisciplina);
        } else {
            throw new IllegalArgumentException("Disciplina não localizada no plano de ensino");
        }

        return disciplina;
    }

    public double getValorTotal() {
        return this.disciplinas.stream().mapToDouble(d -> d.getValor()).sum();
    }

    @Override
    public void efetivar() {
        // TODO Auto-generated method stub
    }
}
