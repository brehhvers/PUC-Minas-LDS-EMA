package Data.DAO;

import Business.Curriculo;
import Data.Conversor.Parse;

public class CurriculoDAO extends DAO<Curriculo> {
    private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/curriculo.txt";
    private static CurriculoDAO INSTANCIA;

    private CurriculoDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new CurriculoDAO();
    }

    public static CurriculoDAO getDAO() {
        return INSTANCIA;
    }

    @Override
    protected Curriculo parse(String linha) {
        return Parse.curriculo(linha);
    }
}
