package Data;

import Business.Pessoa.Secretaria;
import Utils.Parse;

public class SecretariaDAO extends DAO<Secretaria> {
    private static final String CAMINHO_ARQUIVO = "src/Data/File/secretaria.txt";
    private static SecretariaDAO INSTANCIA;

    private SecretariaDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new SecretariaDAO();
    }

    public static SecretariaDAO getDAO() {
        return INSTANCIA;
    }

    @Override
    protected Secretaria parse(String linha) {
        return Parse.secretaria(linha);
    }
}
