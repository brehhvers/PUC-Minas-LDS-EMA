package Business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import Business.Pessoa.Aluno;
import Business.Pessoa.Professor;
import Enum.StatusDisciplina;
import Enum.TipoDisciplina;
import Interface.IEfetivavel;
import Utils.Identificador.Id;
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
            throw new IllegalStateException(
                    "Não é possível adicionar o aluno: disciplina " + this.id + " " + this.nome
                            + " já atingiu o número máximo de vagas.");
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

            NotificadorEmail notificador = NotificadorEmail.getNotificador();
            this.alunos.forEach(a -> notificador.notificar(
                    "Prezado(a) " + a.getNome() + " a disciplina " + this.nome
                            + " infelizmente foi cancelada cancelada devido à quantidade insuficiente de alunos.",
                    a.getEmail()));
        } else {
            this.status = StatusDisciplina.ATIVA;
        }
    }

    @Override
    public String toString() {
        String alunosInfo = "";

        if (!alunos.isEmpty()) {
            alunosInfo = alunos.stream()
                    .map(a -> String.valueOf(a.getMatricula()) + " - " + a.getNome())
                    .collect(Collectors.joining("\n"));
        } else {
            alunosInfo = "Nenhum aluno";
        }

        return String.format(
                "ID da Disciplina: %d%nNome: %s%nValor: %.2f%nProfessor (Matrícula - Nome): %s%nTipo: %s%nData de Criação: %s%nStatus: %s%nAlunos (Matrícula - Nome): %s",
                this.id,
                this.nome,
                this.valor,
                this.professor.getMatricula() + this.professor.getNome(),
                this.tipo.name(),
                this.dataCriacao.toString(),
                this.status.name(),
                alunosInfo);
    }

    public String toPersist() {
        String alunosIds = "";
        if (!alunos.isEmpty()) {
            alunosIds = this.alunos.stream()
                    .map(a -> String.valueOf(a.getMatricula()))
                    .collect(Collectors.joining(","));
        }

        return String.format(
                "%d;%s;%.2f;%s;%s;%s;%s;%s",
                this.id,
                this.nome,
                this.valor,
                this.professor.getMatricula(),
                this.tipo.name(),
                this.dataCriacao.toString(),
                this.status.name(),
                alunosIds);
    }
}
