package Data.DAO;

import java.time.LocalDate;

import Business.Curso;

public class CursoDAO extends DAO<Curso> {
    private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/curso.txt";
    private static CursoDAO INSTANCIA;

    private CursoDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new CursoDAO();
    }

    public static CursoDAO getDAO() {
        return INSTANCIA;
    }

    @Override
    protected Curso parse(String linha) {
        String[] dados = linha.split(";");
        Curso curso = new Curso();

        int id = Integer.parseInt(dados[0]);
        String nome = dados[1];
        int numCreditos = Integer.parseInt(dados[2]);
        String departamento = dados[3];
        LocalDate dataCriacao = LocalDate.parse(dados[4]);

        curso.setId(id);
        curso.setNome(nome);
        curso.setNumCreditos(numCreditos);
        curso.setDataCriacao(dataCriacao);
        curso.setDepartamento(departamento);

        return curso;
    }
}
