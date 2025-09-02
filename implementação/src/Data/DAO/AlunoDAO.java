package Data.DAO;

import java.io.IOException;
import java.util.ArrayList;

import Business.Pessoa.Aluno;
import Data.Conversor.Parse;

public class AlunoDAO extends DAO<Aluno> {
    private static final String CAMINHO_ARQUIVO = "src/Data/File/aluno.txt";
    private static AlunoDAO INSTANCIA;

    private AlunoDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new AlunoDAO();
    }

    public static AlunoDAO getDAO() {
        return INSTANCIA;
    }

    @Override
    protected Aluno parse(String linha) {
        return Parse.aluno(linha);
    }

    public Aluno carregarPorMatricula(int matricula) throws IOException {
        ArrayList<Aluno> alunos = super.carregar();

        Aluno recuperado = alunos.stream()
                .filter(a -> a.getMatricula() == matricula)
                .findFirst()
                .orElse(null);

        return recuperado;
    }

    // @Override
    // public ArrayList<Aluno> carregar() throws IOException {
    //     ArrayList<Aluno> alunos = super.carregar();

    //     for (Aluno aluno : alunos) {
    //         ArrayList<PlanoDeEnsino> planos = PlanoDeEnsinoDAO.getDAO().carregarPorAluno(aluno.getMatricula());
    //         aluno.setPlanosDeEnsino(planos);
    //     }

    //     return alunos;
    // }
}
