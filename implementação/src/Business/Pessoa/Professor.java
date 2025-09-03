package Business.Pessoa;

import java.util.ArrayList;

import Business.Disciplina;
import Interface.IGerenciavel;
import Utils.Identificador.Matricula;

public class Professor extends Usuario implements IGerenciavel<Disciplina, Integer> {
    private int matricula;
    private ArrayList<Disciplina> disciplinas;

    public Professor(String nome, String email, String senha) {
        super(nome, email, senha);
        this.matricula = Matricula.gerar();
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
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nenhuma disciplina corresponde ao código fornecido."));

        return disciplinaRecuperada.getAlunos();
    }

    @Override
    public boolean addDisciplina(Disciplina disciplina) {
        return this.disciplinas.add(disciplina);
    }

    @Override
    public Disciplina removerDisciplina(Integer idDisciplina) {
        Disciplina disciplina = this.disciplinas.stream()
                .filter(d -> d.getId() == idDisciplina)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Este professor não está ministrando a disciplina informada."));

        this.disciplinas.remove(disciplina);
        return disciplina;
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

    public String toPersist() {
        return String.format(
                "%d;%d;%s;%s;%s;%s;%s",
                this.getCodPessoa(),
                this.matricula,
                this.getNome(),
                this.getEmail(),
                this.getSenha(),
                this.isAtivo() ? "true" : "false",
                this.getDataCadastro().toString());
    }
}
