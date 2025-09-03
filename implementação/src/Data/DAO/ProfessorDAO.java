package Data.DAO;

import java.time.LocalDate;

import Business.Pessoa.Professor;

public class ProfessorDAO extends DAO<Professor> {
    private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/professor.txt";
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
        String[] dados = linha.split(";");
        Professor professor = new Professor();

        int codPessoa = Integer.parseInt(dados[0]);
        int matricula = Integer.parseInt(dados[1]);
        String nome = dados[2];
        String email = dados[3];
        String senha = dados[4];
        boolean isAtivo = Boolean.parseBoolean(dados[5]);
        LocalDate dataCadastro = LocalDate.parse(dados[6]);

        String[] disciplinasIds = dados[7].split(",");

        return professor;
    }
}
