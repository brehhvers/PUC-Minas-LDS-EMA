package Data.DAO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Business.Disciplina;
import Business.PlanoDeEnsino;
import Business.Pessoa.Aluno;
import Enum.StatusPlano;

public class PlanoDeEnsinoDAO extends DAO<PlanoDeEnsino> {
    private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/planoDeEnsino.txt";
    private static PlanoDeEnsinoDAO INSTANCIA;

    private Map<Integer, PlanoDeEnsino> cache = new HashMap<>();

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
        String[] dados = linha.split(";");

        int id = Integer.parseInt(dados[0]);
        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        int ano = Integer.parseInt(dados[1]);
        int semestre = Integer.parseInt(dados[2]);
        StatusPlano status = StatusPlano.valueOf(dados[4]);
        LocalDate dataCriacao = LocalDate.parse(dados[5]);
        String[] disciplinasIds = dados[7].split(",");
        Aluno aluno = null;

        PlanoDeEnsino planoDeEnsino = new PlanoDeEnsino();
        planoDeEnsino.setId(id);
        planoDeEnsino.setAno(ano);
        planoDeEnsino.setSemestre(semestre);
        planoDeEnsino.setDataCriacao(dataCriacao);
        planoDeEnsino.setStatus(status);

        try {
            aluno = AlunoDAO.getDAO().carregarPorId(dados[3]);
            planoDeEnsino.setAluno(aluno);

            for (String disciplinaId : disciplinasIds) {
                Disciplina disciplina = DisciplinaDAO.getDAO().carregarPorId(disciplinaId);
                planoDeEnsino.addDisciplina(disciplina);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cache.put(id, planoDeEnsino);
        return planoDeEnsino;
    }

    @Override
    public ArrayList<PlanoDeEnsino> carregar() throws IOException {
        ArrayList<PlanoDeEnsino> planos = super.carregar();

        for (PlanoDeEnsino plano : planos) {
            Aluno aluno = plano.getAluno();

            if (aluno != null) {
                aluno.addPlanoEnsino(plano);
            }
        }

        return planos;
    }

    @Override
    public PlanoDeEnsino carregarPorId(String id) throws IOException {
        int idConvertido = Integer.parseInt(id);

        if (cache.containsKey(idConvertido)) {
            return cache.get(idConvertido);
        }

        return super.carregarPorId(id);
    }
}
