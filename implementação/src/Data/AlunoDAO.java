package Data;

import Business.Pessoa.Aluno;
import Utils.Parse;

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
}
