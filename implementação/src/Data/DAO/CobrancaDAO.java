package Data.DAO;

public class CobrancaDAO extends NotificadorDAO {
    private static final String CAMINHO_ARQUIVO = "src/Data/File/cobranca.txt";
    private static CobrancaDAO INSTANCIA;

    private CobrancaDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new CobrancaDAO();
    }

    public static CobrancaDAO getDAO() {
        return INSTANCIA;
    }
}
