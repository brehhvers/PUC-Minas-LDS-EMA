package Data.DAO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import Business.Curriculo;
import Business.Curso;

public class CurriculoDAO extends DAO<Curriculo> {
    private static final String CAMINHO_ARQUIVO = "src/Data/File/curriculo.txt";
    private static CurriculoDAO INSTANCIA;

    private Map<Integer, Curriculo> cache = new HashMap<>();

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
        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        LocalDate dataCriacao = LocalDate.parse(dados[2]);
        String[] disciplinasInfo = dados[3].split(",");
        Curso curso = null;

        Curriculo curriculo = new Curriculo();
        curriculo.setId(id);
        curriculo.setDataCriacao(dataCriacao);

        for (String disciplinaInfo : disciplinasInfo) {
            curriculo.addDisciplina(disciplinaInfo);
        }

        try {
            curso = CursoDAO.getDAO().carregarPorId(dados[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        curriculo.setCurso(curso);

        cache.put(id, curriculo);
        return curriculo;
    }

    @Override
    public Curriculo carregarPorId(String id) throws IOException {
        int idConvertido = Integer.parseInt(id);

        if (cache.containsKey(idConvertido)) {
            return cache.get(idConvertido);
        }

        return super.carregarPorId(id);
    }
}
