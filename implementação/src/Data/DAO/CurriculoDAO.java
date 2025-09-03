package Data.DAO;

import java.time.LocalDate;

import Business.Curriculo;
import Business.Curso;

public class CurriculoDAO extends DAO<Curriculo> {
    private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/curriculo.txt";
    private static CurriculoDAO INSTANCIA;

    private CurriculoDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new CurriculoDAO();
    }

    public static CurriculoDAO getDAO() {
        return INSTANCIA;
    }

    @Override
    protected Curriculo parse(String linha) {
        String[] dados = linha.split(";");
        Curriculo curriculo = new Curriculo();

        int id = Integer.parseInt(dados[0]);
        LocalDate dataCriacao = LocalDate.parse(dados[2]);
        
        String[] disciplinasInfo = dados[3].split(",");
        Curso curso = null;
        
        try {
            curso = CursoDAO.getDAO().carregarPorId(dados[1]);

            for (String disciplinaInfo : disciplinasInfo) {
                curriculo.addDisciplina(disciplinaInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        curriculo.setId(id);
        curriculo.setDataCriacao(dataCriacao);
        curriculo.setCurso(curso);

        return curriculo;
    }
}
