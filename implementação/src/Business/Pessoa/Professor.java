package Business.Pessoa;

import java.util.ArrayList;

import Business.Disciplina;

public class Professor extends Usuario {
    private int matricula; // TODO:
    private ArrayList<Disciplina> disciplinas;

    public Professor(String nome, String email, String senha) {
        super(nome, email, senha);
        this.disciplinas = new ArrayList<>();
    }

    public int getMatricula() {
        return this.matricula;
    }

    public ArrayList<Disciplina> getDisciplinas() {
        return this.disciplinas;
    }

    public ArrayList<Aluno> getAllAlunos() {
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();

        for (Disciplina disciplina : this.disciplinas) {
            alunos.addAll(disciplina.getAlunos());
        }

        return alunos;
    }

    public ArrayList<Aluno> getAlunosPorDisciplina(int idDisciplina) {
        Disciplina disciplinaRecuperada = this.disciplinas.stream()
                .filter(d -> d.getId() == idDisciplina)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma disciplina corresponde ao código fornecido."));

        return disciplinaRecuperada.getAlunos();
    }

    @Override
    public String toString() {
        String string = String.format(
                "Código da Pessoa: %d%nMatrícula: %d%nNome: %s%nEmail: %s%nData de Cadastro: %s",
                this.getCodPessoa(),
                this.matricula,
                this.getNome(),
                this.getEmail(),
                this.getDataCadastro().format(this.formatter));

        return string;
    }
}
