package Data.DAO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import Business.Pessoa.Professor;

public class ProfessorDAO extends DAO<Professor> {
    private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/professor.txt";
    private static ProfessorDAO INSTANCIA;

    private Map<Integer, Professor> cache = new HashMap<>();

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
        String[] dados = linha.split(";");

        int codPessoa = Integer.parseInt(dados[0]);
        if (cache.containsKey(codPessoa)) {
            return cache.get(codPessoa);
        }

        int matricula = Integer.parseInt(dados[1]);
        String nome = dados[2];
        String email = dados[3];
        String senha = dados[4];
        boolean isAtivo = Boolean.parseBoolean(dados[5]);
        LocalDate dataCadastro = LocalDate.parse(dados[6]);

        Professor professor = new Professor();
        professor.setCodPessoa(codPessoa);
        professor.setMatricula(matricula);
        professor.setNome(nome);
        professor.setEmail(email);
        professor.setSenha(senha);
        professor.setAtivo(isAtivo);
        professor.setDataCadastro(dataCadastro);

        cache.put(codPessoa, professor);
        return professor;
    }

    @Override
    public Professor carregarPorId(String id) throws IOException {
        int idConvertido = Integer.parseInt(id);

        if (cache.containsKey(idConvertido)) {
            return cache.get(idConvertido);
        }

        return super.carregarPorId(id);
    }
}
