package Data.DAO;

import java.time.LocalDate;

import Business.Curriculo;
import Business.Pessoa.Aluno;

public class AlunoDAO extends DAO<Aluno> {
    private static final String CAMINHO_ARQUIVO = "/implementaçãosrc/Data/File/aluno.txt";
    private static AlunoDAO INSTANCIA;

    private AlunoDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new AlunoDAO();
    }

    public static AlunoDAO getDAO() {
        return INSTANCIA;
    }

    @Override
    protected Aluno parse(String linha) {
        String[] dados = linha.split(";");
        Aluno aluno = new Aluno();

        int codPessoa = Integer.parseInt(dados[0]);
        int matricula = Integer.parseInt(dados[1]);
        String nome = dados[2];
        String email = dados[3];
        String senha = dados[4];
        boolean isAtivo = Boolean.parseBoolean(dados[5]);
        LocalDate dataCadastro = LocalDate.parse(dados[6]);

        Curriculo curriculo = null;
        String[] planosDeEnsinoIds = dados[8].split(",");

        try {
            curriculo = CurriculoDAO.getDAO().carregarPorId(dados[7]);
            aluno.setCurriculo(curriculo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        aluno.setCodPessoa(codPessoa);
        aluno.setMatricula(matricula);
        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setSenha(senha);
        aluno.setAtivo(isAtivo);
        aluno.setDataCadastro(dataCadastro);;

        return aluno;
    }
}
