package Data.DAO;

import Business.PlanoDeEnsino;
import Data.Conversor.Parse;

public class PlanoDeEnsinoDAO extends DAO<PlanoDeEnsino> {
    private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/planoDeEnsino.txt";
    private static PlanoDeEnsinoDAO INSTANCIA;

    private PlanoDeEnsinoDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new PlanoDeEnsinoDAO();
    }

    public static PlanoDeEnsinoDAO getDAO() {
        return INSTANCIA;
    }

    @Override
    protected PlanoDeEnsino parse(String linha) {
        return Parse.planoDeEnsino(linha);
    }
}
