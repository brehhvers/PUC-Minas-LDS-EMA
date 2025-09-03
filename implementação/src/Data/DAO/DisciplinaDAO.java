package Data.DAO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Business.Disciplina;
import Business.Pessoa.Aluno;
import Business.Pessoa.Professor;
import Enum.StatusDisciplina;
import Enum.TipoDisciplina;

public class DisciplinaDAO extends DAO<Disciplina> {
    private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/disciplina.txt";
    private static DisciplinaDAO INSTANCIA;

    private Map<Integer, Disciplina> cache = new HashMap<>();

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
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        
        String nome = dados[1];
        double valor = Double.parseDouble(dados[2]);
        LocalDate dataCriacao = LocalDate.parse(dados[5]);
        TipoDisciplina tipo = TipoDisciplina.valueOf(dados[4]);
        StatusDisciplina status = StatusDisciplina.valueOf((dados[6]));
        String[] alunosIds = dados[7].split(",");
        Professor professor = null;
        
        Disciplina disciplina = new Disciplina();
        disciplina.setId(id);
        disciplina.setNome(nome);
        disciplina.setValor(valor);
        disciplina.setDataCriacao(dataCriacao);
        disciplina.setTipo(tipo);
        disciplina.setStatus(status);
        disciplina.setProfessor(professor);

         try {
            professor = ProfessorDAO.getDAO().carregarPorId(dados[3]);
            
            for (String alunoId : alunosIds) {
                Aluno aluno = AlunoDAO.getDAO().carregarPorId(alunoId);
                disciplina.addAluno(aluno);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        cache.put(id, disciplina);
        return disciplina;
    }

    @Override
    public ArrayList<Disciplina> carregar() throws IOException {
        ArrayList<Disciplina> disciplinas = super.carregar();

        for (Disciplina disciplina : disciplinas) {
            Professor professor = disciplina.getProfessor();

            if (professor != null) {
                professor.addDisciplina(disciplina);
            }
        }

        return disciplinas;
    }

    @Override
    public Disciplina carregarPorId(String id) throws IOException {
        int idConvertido = Integer.parseInt(id);

        if (cache.containsKey(idConvertido)) {
            return cache.get(idConvertido);
        }

        return super.carregarPorId(id);
    }
}
