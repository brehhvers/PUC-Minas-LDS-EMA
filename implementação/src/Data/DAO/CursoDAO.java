package Data.DAO;

import Business.Curso;
import Data.Conversor.Parse;

public class CursoDAO extends DAO<Curso> {
    private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/curso.txt";
    private static CursoDAO INSTANCIA;

    private CursoDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new CursoDAO();
    }

    public static CursoDAO getDAO() {
        return INSTANCIA;
    }

    @Override
    protected Curso parse(String linha) {
        return Parse.curso(linha);
    }
}
