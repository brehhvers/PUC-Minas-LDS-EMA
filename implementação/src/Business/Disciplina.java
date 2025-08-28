package Business;

import java.util.ArrayList;

import Business.Pessoa.Aluno;

public class Disciplina implements Efetivavel {
    private static final int QTDE_MAX_ALUNOS = 60;

    private int id;
    private String nome; 
    private double valor;
    private ArrayList<Aluno> alunos; 
    private TipoDisciplina tipoDisciplina;
    private StatusDisciplina statusDisciplina;

    public Disciplina() {
        this.alunos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoDisciplina getTipoDisciplina() {
        return tipoDisciplina;
    }
    
    public void setTipoDisciplina(TipoDisciplina tipoDisciplina) {
        this.tipoDisciplina = tipoDisciplina;
    }

    public StatusDisciplina getStatusDisciplina() {
        return statusDisciplina;
    }

    public void setStatusDisciplina(StatusDisciplina statusDisciplina) {
        this.statusDisciplina = statusDisciplina;
    }

    public boolean possuiVagas() {
        return true;
    }

    public boolean addAluno(Aluno aluno) {
        // implementar regra de negocio 
       return this.alunos.add(aluno);
    }

    public Aluno removerAluno(int matricula) {
        Aluno aluno = this.alunos.stream().filter(a -> a.getMatricula() == matricula).findFirst().orElse(null);

        if (aluno != null) {
            this.alunos.remove(aluno);
        } else {
            throw new IllegalArgumentException("Aluno não identificado a partir da matricula informada");
        }

        return aluno;
    }

    @Override
    public void efetivar() {
        // TODO Auto-generated method stub
    }
}
