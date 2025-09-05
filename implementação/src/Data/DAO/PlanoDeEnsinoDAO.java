package Data.DAO;

import java.time.LocalDate;

import Business.Disciplina;
import Business.PlanoDeEnsino;
import Business.Pessoa.Aluno;
import Enum.StatusPlano;

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
        String[] dados = linha.split(";");

        int id = Integer.parseInt(dados[0]);
        if (this.cache.containsKey(id)) {
            return this.cache.get(id);
        }

        int ano = Integer.parseInt(dados[1]);
        int semestre = Integer.parseInt(dados[2]);
        StatusPlano status = StatusPlano.valueOf(dados[4]);
        LocalDate dataCriacao = LocalDate.parse(dados[5]);
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

            if (dados.length > 6 && !dados[6].isEmpty()) {
                String[] disciplinasIds = dados[6].split(",");
                
                for (String disciplinaId : disciplinasIds) {
                    Disciplina disciplina = DisciplinaDAO.getDAO().carregarPorId(disciplinaId);
                    planoDeEnsino.addDisciplina(disciplina);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.cache.put(id, planoDeEnsino);
        return planoDeEnsino;
    }
}
