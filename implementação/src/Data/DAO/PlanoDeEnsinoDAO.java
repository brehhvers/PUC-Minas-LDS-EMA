package Data.DAO;

import java.time.LocalDate;

import Business.Disciplina;
import Business.PlanoDeEnsino;
import Business.Pessoa.Aluno;
import Enum.StatusPlano;

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
        String[] dados = linha.split(";");
        PlanoDeEnsino planoDeEnsino = new PlanoDeEnsino();

        int id = Integer.parseInt(dados[0]);
        int ano = Integer.parseInt(dados[1]);
        int semestre = Integer.parseInt(dados[2]);
        StatusPlano status = StatusPlano.valueOf(dados[4]);
        LocalDate dataCriacao = LocalDate.parse(dados[5]);

        String[] disciplinasIds = dados[7].split(",");
        Aluno aluno = null;

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

        planoDeEnsino.setId(id);
        planoDeEnsino.setAno(ano);
        planoDeEnsino.setSemestre(semestre);
        planoDeEnsino.setDataCriacao(dataCriacao);
        planoDeEnsino.setStatus(status);

        return planoDeEnsino;
    }
}
