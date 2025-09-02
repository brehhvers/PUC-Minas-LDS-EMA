package Data;

import Business.Pessoa.Professor;
import Utils.Parse;

public class ProfessorDAO extends DAO<Professor> {
    private static final String CAMINHO_ARQUIVO = "src/Data/File/professor.txt";
    private static ProfessorDAO INSTANCIA;

    private ProfessorDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new ProfessorDAO();
    }

    public static ProfessorDAO getDAO() {
        return INSTANCIA;
    }

    @Override
    protected Professor parse(String linha) {
        return Parse.professor(linha);
    }
}
