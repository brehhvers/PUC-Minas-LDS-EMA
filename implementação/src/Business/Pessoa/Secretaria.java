package Business.Pessoa;

import java.util.ArrayList;

import Business.Curriculo;
import Business.Curso;
import Business.Disciplina;
import Enum.TipoDisciplina;
import Interface.IEfetivavel;

public class Secretaria extends Usuario {
    public Secretaria(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public Disciplina criarDisciplina(TipoDisciplina tipo) {
        return new Disciplina(tipo);
    }

    public Curso criarCurso(String nome, int numCreditos) {
        return new Curso(nome, numCreditos);
    }

    public Curriculo criarCurriculo(Curso curso) {
        return new Curriculo(curso);
    }

    public Aluno cadastrarAluno(String nome, String email, String senha) {
        return new Aluno(nome, email, senha);
    }

    public Professor cadastrarProfessor(String nome, String email, String senha) {
        return new Professor(nome, email, senha);
    }

    public Secretaria cadastrarSecretaria(String nome, String email, String senha) {
        return new Secretaria(nome, email, senha);
    }

    public void consolidarMatriculas(ArrayList<IEfetivavel> disciplinas, ArrayList<Aluno> alunos) {
        disciplinas.stream().forEach(d -> d.efetivar());
        alunos.stream().forEach(a -> a.getPlanoAtivo().efetivar());
    }

    @Override
    public String toString() {
        String string = String.format(
                "Código da Pessoa: %d%nNome: %s%nEmail: %s%nData de Cadastro: %s",
                this.getCodPessoa(),
                this.getNome(),
                this.getEmail(),
                this.getDataCadastro().format(this.formatter));

        return string;
    }
}