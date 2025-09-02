package Data.DAO;

import java.io.IOException;
import java.util.ArrayList;

import Business.Disciplina;
import Data.Conversor.Parse;

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

    public Disciplina carregarPorId(int id) throws IOException {
        ArrayList<Disciplina> disciplinas = super.carregar();
        
        Disciplina recuperada = disciplinas
                .stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);

        return recuperada;
    }
}
