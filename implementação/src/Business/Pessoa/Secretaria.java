package Business.Pessoa;

import java.time.LocalDate;

public class Secretaria extends Usuario {

    public Secretaria(
            String nome,
            LocalDate dataCadastro,
            String email,
            String senha
    ) {
        super(nome, dataCadastro, email, senha);
    }

//    public Disciplina criarDisciplina(){}
//
//    public Curso criarCurso(){}
//
//    public Aluno cadastrarAluno(){}
//
//    public Professor cadastrarProfessor(){}
//
//    public Curriculo criarCurriculo(){}
}