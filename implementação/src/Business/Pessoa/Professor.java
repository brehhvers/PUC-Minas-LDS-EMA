package Business.Pessoa;

import java.time.LocalDate;

public class Professor extends Usuario {

    private int matricula; // TODO:

    public Professor(
            String nome,
            LocalDate dataCadastro,
            boolean ativo,
            String email,
            String senha
    ) {
        super(nome, dataCadastro, ativo, email, senha);
    }

    public int getMatricula() {
        return this.matricula;
    }
//    public ArrayList<Aluno> consultarAlunos() {}
}
