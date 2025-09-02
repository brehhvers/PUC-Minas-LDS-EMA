package Data.DAO;

import java.io.IOException;
import java.util.ArrayList;

import Business.Pessoa.Professor;
import Data.Conversor.Parse;

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

    public Professor carregarPorMatricula(int matricula) throws IOException {
        ArrayList<Professor> professors = super.carregar();

        Professor recuperado = professors.stream()
                .filter(p -> p.getMatricula() == matricula)
                .findFirst()
                .orElse(null);

        return recuperado;
    }
}
