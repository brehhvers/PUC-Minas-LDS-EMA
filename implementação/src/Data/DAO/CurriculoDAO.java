package Data.DAO;

import java.time.LocalDate;
import Business.Curriculo;
import Business.Curso;

public class CurriculoDAO extends DAO<Curriculo> {
    private static final String CAMINHO_ARQUIVO = "src/Data/File/curriculo.txt";
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

        int id = Integer.parseInt(dados[0]);
        if (this.cache.containsKey(id)) {
            return this.cache.get(id);
        }

        LocalDate dataCriacao = LocalDate.parse(dados[1]);
        Curso curso = null;

        Curriculo curriculo = new Curriculo();
        curriculo.setId(id);
        curriculo.setDataCriacao(dataCriacao);

        if (dados.length > 2 && !dados[2].isEmpty()) {
            String[] disciplinasInfo = dados[2].split(",");
            for (String disciplinaInfo : disciplinasInfo) {
                curriculo.addDisciplina(disciplinaInfo);
            }
        }

        try {
            if (dados.length > 3 && !dados[3].isEmpty()) {
                curso = CursoDAO.getDAO().carregarPorId(dados[3]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        curriculo.setCurso(curso);

        this.cache.put(id, curriculo);
        return curriculo;
    }
}
