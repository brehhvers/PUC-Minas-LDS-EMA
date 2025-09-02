package Data.DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import Business.PlanoDeEnsino;
import Data.Conversor.Parse;

public class PlanoDeEnsinoDAO extends DAO<PlanoDeEnsino> {
    private static final String CAMINHO_ARQUIVO = "src/Data/File/planoDeEnsino.txt";
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

    public ArrayList<PlanoDeEnsino> carregarPorAluno(int matricula) throws IOException {
        ArrayList<PlanoDeEnsino> planos = super.carregar();

        ArrayList<PlanoDeEnsino> planosPorAluno = planos.stream()
                .filter(p -> p.getAluno().getMatricula() == matricula)
                .collect(Collectors.toCollection(ArrayList::new));

        return planosPorAluno;
    }
}
