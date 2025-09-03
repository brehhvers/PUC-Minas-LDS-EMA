package Data.DAO;

public class EmailDAO extends NotificadorDAO {
    private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/email.txt";
    private static EmailDAO INSTANCIA;

    private EmailDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new EmailDAO();
    }

    public static EmailDAO getDAO() {
        return INSTANCIA;
    }
}
