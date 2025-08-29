package Business;

import java.time.LocalDate;
import java.util.ArrayList;

import Business.Pessoa.Aluno;
import Business.Pessoa.Professor;
import Enum.StatusDisciplina;
import Enum.TipoDisciplina;
import Interface.IEfetivavel;
import Utils.Codigo.Id;
import Utils.Notificador.NotificadorEmail;

public class Disciplina implements IEfetivavel {
    private static final int QTDE_MIN_ALUNOS = 3;
    private static final int QTDE_MAX_ALUNOS = 60;

    private int id;
    private String nome;
    private double valor;
    private Professor professor;
    private TipoDisciplina tipo;
    private LocalDate dataCriacao;
    private StatusDisciplina status;
    private ArrayList<Aluno> alunos;

    public Disciplina(TipoDisciplina tipo) {
        this.id = Id.gerar();
        this.tipo = tipo;
        this.alunos = new ArrayList<>();
        this.dataCriacao = LocalDate.now();
        this.status = StatusDisciplina.PREVISTA;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoDisciplina getTipo() {
        return this.tipo;
    }

    public LocalDate getDataCriacao() {
        return this.dataCriacao;
    }

    public void setTipo(TipoDisciplina tipo) {
        this.tipo = tipo;
    }

    public StatusDisciplina getStatus() {
        return this.status;
    }

    public void setStatus(StatusDisciplina status) {
        this.status = status;
    }

    private boolean possuiVagas() {
        return this.alunos.size() < QTDE_MAX_ALUNOS;
    }

    public Professor getProfessor() {
        return this.professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public ArrayList<Aluno> getAlunos() {
        return this.alunos;
    }

    public boolean addAluno(Aluno aluno) {
        if (possuiVagas()) {
            return this.alunos.add(aluno);
        } else {
            throw new RuntimeException(
                    "Não é possível adicionar o aluno: a disciplina já atingiu o número máximo de vagas.");
        }
    }

    public Aluno removerAluno(int matricula) {
        Aluno aluno = this.alunos.stream().filter(a -> a.getMatricula() == matricula).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Não foi possível remover o aluno: matrícula inválida ou inexistente."));

        this.alunos.remove(aluno);
        return aluno;
    }

    @Override
    public void efetivar() {
        if (this.alunos.size() < QTDE_MIN_ALUNOS) {

            this.status = StatusDisciplina.CANCELADA;
            this.alunos.forEach(a -> NotificadorEmail.notificar(
                    "Prezado(a) " + a.getNome() + " a disciplina " + this.nome
                            + " infelizmente foi cancelada cancelada devido à quantidade insuficiente de alunos.",
                    a.getEmail()));
        }
    }
}
