package Data.Conversor;

import Business.Curso;
import Business.Curriculo;
import Business.Disciplina;
import Business.Pessoa.Aluno;
import Business.PlanoDeEnsino;
import Business.Pessoa.Professor;
import Business.Pessoa.Secretaria;

public class Parse {
    public static Aluno aluno(String linha) {
        String[] dados = linha.split(";");
        return new Aluno(dados[1], dados[2], dados[3]);
    }

    public static Professor professor(String linha) {
        String[] dados = linha.split(";");
        return new Professor(dados[1], dados[2], dados[3]);
    }

    public static Secretaria secretaria(String linha) {
        String[] dados = linha.split(";");
        return new Secretaria(dados[1], dados[2], dados[3]);
    }

    public static Disciplina disciplina(String linha) {
        String[] dados = linha.split(";");
        return new Disciplina(null);
    }

    public static Curso curso(String linha) {
        String[] dados = linha.split(";");
        return new Curso(dados[1], Integer.parseInt(dados[2]));
    }

    public static Curriculo curriculo(String linha) {
        return new Curriculo(null);
    }

    public static PlanoDeEnsino planoDeEnsino(String linha) {
        return new PlanoDeEnsino(null);
    }
}
