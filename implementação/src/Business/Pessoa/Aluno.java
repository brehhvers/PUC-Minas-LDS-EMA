package Business.Pessoa;

import java.time.LocalDate;

public class Aluno extends Usuario {

    private int matricula; // TODO:

    public Aluno(
            String nome,
            LocalDate dataCadastro,
            String email,
            String senha) {
        super(nome, dataCadastro, email, senha);
    }

    public int getMatricula() {
        return matricula;
    }
}