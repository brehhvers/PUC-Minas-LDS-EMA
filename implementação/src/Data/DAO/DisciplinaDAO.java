package Data.DAO;

import java.io.IOException;
import java.time.LocalDate;

import Business.Disciplina;
import Business.Pessoa.Aluno;
import Business.Pessoa.Professor;
import Enum.StatusDisciplina;
import Enum.TipoDisciplina;

public class DisciplinaDAO extends DAO<Disciplina> {
    private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/disciplina.txt";
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
        String[] dados = linha.split(";");
        Disciplina disciplina = new Disciplina();

        int id = Integer.parseInt(dados[0]);
        String nome = dados[1];
        double valor = Double.parseDouble(dados[2]);
        LocalDate dataCriacao = LocalDate.parse(dados[5]);
        TipoDisciplina tipo = TipoDisciplina.valueOf(dados[4]);
        StatusDisciplina status = StatusDisciplina.valueOf((dados[6]));

        String[] alunosIds = dados[7].split(",");
        Professor professor = null;

        try {
            professor = ProfessorDAO.getDAO().carregarPorId(dados[3]);

            for (String alunoId : alunosIds) {
                Aluno aluno = AlunoDAO.getDAO().carregarPorId(alunoId);
                disciplina.addAluno(aluno);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        disciplina.setId(id);
        disciplina.setNome(nome);
        disciplina.setValor(valor);
        disciplina.setDataCriacao(dataCriacao);
        disciplina.setTipo(tipo);
        disciplina.setStatus(status);
        disciplina.setProfessor(professor);

        return null;
    }
}
