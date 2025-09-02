package Data.DAO;

import Business.Disciplina;
import Utils.Conversor.Parse;

public class DisciplinaDAO extends DAO<Disciplina> {
    private static final String CAMINHO_ARQUIVO = "src/Data/File/disciplina.txt";
    private static DisciplinaDAO INSTANCIA;

    private DisciplinaDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new DisciplinaDAO();
    }

    public static DisciplinaDAO getDAO() {
        return INSTANCIA;
    }

    @Override
    protected Disciplina parse(String linha) {
        return Parse.disciplina(linha);
    }
}
