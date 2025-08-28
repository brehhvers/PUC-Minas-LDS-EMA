package Business.Pessoa;

import java.time.LocalDate;

public class Aluno extends Usuario {

    private int matricula; // TODO:

    public Aluno(
            String nome,
            LocalDate dataCadastro,
            boolean ativo,
            String email,
            String senha
    ) {
        super(nome, dataCadastro, ativo, email, senha);
    }

    public int getMatricula() {
        return matricula;
    }
}
