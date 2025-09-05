package Data.DAO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import Business.Curso;

public class CursoDAO extends DAO<Curso> {
    private static final String CAMINHO_ARQUIVO = "src/Data/File/curso.txt";
    private static CursoDAO INSTANCIA;

    private Map<Integer, Curso> cache = new HashMap<>();

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

        int id = Integer.parseInt(dados[0]);
        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        String nome = dados[1];
        int numCreditos = Integer.parseInt(dados[2]);
        String departamento = dados[3];
        LocalDate dataCriacao = LocalDate.parse(dados[4]);

        Curso curso = new Curso();
        curso.setId(id);
        curso.setNome(nome);
        curso.setNumCreditos(numCreditos);
        curso.setDataCriacao(dataCriacao);
        curso.setDepartamento(departamento);

        cache.put(id, curso);
        return curso;
    }

    @Override
    public Curso carregarPorId(String id) throws IOException {
        int idConvertido = Integer.parseInt(id);

        if (cache.containsKey(idConvertido)) {
            return cache.get(idConvertido);
        }

        return super.carregarPorId(id);
    }
}
