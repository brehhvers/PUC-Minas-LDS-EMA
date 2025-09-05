package Business.Pessoa;

import java.util.ArrayList;

import Business.Curriculo;
import Business.Curso;
import Business.Disciplina;
import Enum.TipoDisciplina;
import Interface.IEfetivavel;
import Interface.ISecretaria;

public class Secretaria extends Usuario implements ISecretaria {
    public Secretaria() {
        super();
    }
    
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

        alunos.stream().forEach(a -> {
            IEfetivavel planoDeEnsino = a.getPlanoAtivo();
            planoDeEnsino.efetivar();
        });
    }

    // Métodos de listagem - implementação básica
    public ArrayList<Aluno> listarAlunos() {
        // Este método será implementado na interface, não aqui
        return new ArrayList<>();
    }

    public ArrayList<Professor> listarProfessores() {
        // Este método será implementado na interface, não aqui
        return new ArrayList<>();
    }

    public ArrayList<Secretaria> listarSecretarias() {
        // Este método será implementado na interface, não aqui
        return new ArrayList<>();
    }

    public ArrayList<Curso> listarCursos() {
        // Este método será implementado na interface, não aqui
        return new ArrayList<>();
    }

    public ArrayList<Disciplina> listarDisciplinas() {
        // Este método será implementado na interface, não aqui
        return new ArrayList<>();
    }

    public ArrayList<Curriculo> listarCurriculos() {
        // Este método será implementado na interface, não aqui
        return new ArrayList<>();
    }

    // Métodos de busca - implementação básica
    public Aluno buscarAlunoPorCodigo(int codigo) {
        // Este método será implementado na interface, não aqui
        return null;
    }

    public Professor buscarProfessorPorCodigo(int codigo) {
        // Este método será implementado na interface, não aqui
        return null;
    }

    public Secretaria buscarSecretariaPorCodigo(int codigo) {
        // Este método será implementado na interface, não aqui
        return null;
    }

    public Curso buscarCursoPorId(int id) {
        // Este método será implementado na interface, não aqui
        return null;
    }

    public Disciplina buscarDisciplinaPorId(int id) {
        // Este método será implementado na interface, não aqui
        return null;
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

    @Override
    public String toPersist() {
        return String.format(
                "%d;%s;%s;%s;%s;%s",
                this.getCodPessoa(),
                this.getNome(),
                this.getEmail(),
                this.getSenha(),
                this.isAtivo() ? "true" : "false",
                this.getDataCadastro().toString());
    }
}