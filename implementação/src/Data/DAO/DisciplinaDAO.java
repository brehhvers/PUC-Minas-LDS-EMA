package Data.DAO;

import java.io.IOException;
import java.time.LocalDate;

import Business.Disciplina;
import Business.Pessoa.Aluno;
import Business.Pessoa.Professor;
import Enum.StatusDisciplina;
import Enum.TipoDisciplina;

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
        String[] dados = linha.split(";");

        int id = Integer.parseInt(dados[0]);
        if (this.cache.containsKey(id)) {
            return this.cache.get(id);
        }

        String nome = dados[1];
        double valor = Double.parseDouble(dados[2].replace(",", "."));
        LocalDate dataCriacao = LocalDate.parse(dados[5]);
        TipoDisciplina tipo = TipoDisciplina.valueOf(dados[4]);
        StatusDisciplina status = StatusDisciplina.valueOf((dados[6]));

        Professor professor = null;

        Disciplina disciplina = new Disciplina();
        disciplina.setId(id);
        disciplina.setNome(nome);
        disciplina.setValor(valor);
        disciplina.setDataCriacao(dataCriacao);
        disciplina.setTipo(tipo);
        disciplina.setStatus(status);
        
        try {
            professor = ProfessorDAO.getDAO().carregarPorId(dados[3]);
            disciplina.setProfessor(professor);

            if (dados.length > 7 && !dados[7].isEmpty()) {
                String[] alunosIds = dados[7].split(",");

                for (String alunoId : alunosIds) {
                    Aluno aluno = AlunoDAO.getDAO().carregarPorId(alunoId);
                    disciplina.addAluno(aluno);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.cache.put(id, disciplina);
        return disciplina;
    }
}
