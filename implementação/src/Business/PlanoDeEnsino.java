package Business;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import Business.Pessoa.Aluno;
import Enum.StatusDisciplina;
import Enum.StatusPlano;
import Enum.TipoDisciplina;
import Interface.IEfetivavel;
import Interface.IGerenciavel;
import Interface.IPersistivel;
import Utils.Identificador.Id;
import Utils.Notificador.NotificadorCobranca;

public class PlanoDeEnsino implements IEfetivavel, IGerenciavel<Disciplina, Integer>, IPersistivel {
    private int id;
    private int ano;
    private Aluno aluno;
    private int semestre;
    private StatusPlano status;
    private LocalDate dataCriacao;
    private ArrayList<Disciplina> disciplinas;

    public PlanoDeEnsino(Aluno aluno) {
        this.id = Id.gerar();
        this.aluno = aluno;
        this.status = StatusPlano.RASCUNHO;
        this.dataCriacao = LocalDate.now();
        this.disciplinas = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public int getAno() {
        return this.ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSemestre() {
        return this.semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public LocalDate getDataCriacao() {
        return this.dataCriacao;
    }

    public StatusPlano getStatus() {
        return this.status;
    }

    public void setStatus(StatusPlano status) {
        this.status = status;
    }

    public Aluno getAluno() {
        return this.aluno;
    }

    public double getValorTotal() {
        return this.disciplinas.stream().mapToDouble(d -> d.getValor()).sum();
    }

    public ArrayList<Disciplina> getDisciplinas() {
        return this.disciplinas;
    }

    @Override
    public synchronized boolean addDisciplina(Disciplina disciplina) {
        final long MAX_DISCIPLINAS = 6L;
        final long MAX_OBRIGATORIAS = 4L;
        final long MAX_OPTATIVAS = 2L;

        Curriculo curriculo = this.aluno.getCurriculo();

        if (!curriculo.getDisciplinas().contains(disciplina.getNome())) {
            throw new IllegalArgumentException(
                    "Não é possível adicionar esta disciplina: ela não faz parte do currículo do aluno.");
        }

        if (this.disciplinas.size() == MAX_DISCIPLINAS) {
            throw new IllegalStateException("Limite máximo de disciplinas atingido neste plano.");
        }

        long qtdeObrigatorias = this.disciplinas.stream()
                .filter(d -> TipoDisciplina.OBRIGATORIA.equals(d.getTipo()))
                .count();

        if (qtdeObrigatorias == MAX_OBRIGATORIAS
                && TipoDisciplina.OBRIGATORIA.equals(disciplina.getTipo()))
            throw new IllegalStateException("Limite máximo de disciplinas obrigatórias atingido neste plano.");

        long qtdeOptativas = this.disciplinas.stream()
                .filter(d -> TipoDisciplina.OPTATIVA.equals(d.getTipo()))
                .count();

        if (qtdeOptativas == MAX_OPTATIVAS
                && TipoDisciplina.OPTATIVA.equals(disciplina.getTipo()))
            throw new IllegalStateException("Limite máximo de disciplinas optativas atingido neste plano.");

        return this.disciplinas.add(disciplina);
    }

    @Override
    public synchronized Disciplina removerDisciplina(Integer idDisciplina) {
        Disciplina disciplina = this.disciplinas.stream()
                .filter(d -> d.getId() == idDisciplina)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nenhuma disciplina correspondente foi encontrada neste plano de ensino."));

        this.disciplinas.remove(disciplina);
        return disciplina;
    }

    @Override
    public void efetivar() {
        this.disciplinas = this.disciplinas.stream()
                .filter(d -> StatusDisciplina.ATIVA.equals(d.getStatus()))
                .collect(Collectors.toCollection(ArrayList::new));

        if (this.disciplinas.size() > 0) {
            if (StatusPlano.CONFIRMADO.equals(this.status)) {
                this.status = StatusPlano.EFETIVADO;

                NotificadorCobranca
                        .getNotificador()
                        .notificar("Plano de ensino " + this.getId() + " efetivado",
                                this.getValorTotal());
            }
        }
    }

    @Override
    public String toString() {
        String disciplinasInfo = "Nenhuma disciplina";

        if (!disciplinas.isEmpty()) {
            disciplinasInfo = disciplinas.stream()
                    .map(d -> String.valueOf(d.getId()) + " - " + d.getNome())
                    .collect(Collectors.joining("\n"));
        }

        String string = String.format(
                "ID do Plano: %d%nAno: %d%nAluno (Matrícula - Nome): %s%nSemestre: %d%nStatus: %s%nData de Criação: %s%nDisciplinas (Matrícula - Nome): %s",
                this.id,
                this.ano,
                this.aluno.getMatricula() + this.aluno.getNome(),
                this.semestre,
                this.status,
                this.dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                disciplinasInfo);

        return string;
    }

    @Override
    public String toPersist() {
        String disciplinasIds = "";

        if (!this.disciplinas.isEmpty()) {
            disciplinasIds = this.disciplinas.stream()
                    .map(d -> String.valueOf(d.getId()))
                    .collect(Collectors.joining(","));
        }

        return String.format(
                "%d;%d;%d;%d;%s;%s;%s",
                this.id,
                this.ano,
                this.semestre,
                this.aluno.getMatricula(),
                this.status.name(),
                this.dataCriacao.toString(),
                disciplinasIds);
    }
}
