package Business;

import java.util.ArrayList;

import Business.Pessoa.Aluno;
import Interface.IEfetivavel;

public class Disciplina implements IEfetivavel {
    private static final int QTDE_MAX_ALUNOS = 60;

    private int id;
    private String nome; 
    private double valor;
    private ArrayList<Aluno> alunos; 
    private TipoDisciplina tipo;
    private StatusDisciplina status;

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

    public TipoDisciplina getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoDisciplina tipo) {
        this.tipo = tipo;
    }

    public StatusDisciplina getStatus() {
        return status;
    }

    public void setStatus(StatusDisciplina status) {
        this.status= status;
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
