package Data.DAO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import Business.Curriculo;
import Business.Pessoa.Aluno;

    public class AlunoDAO extends DAO<Aluno> {
        private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/aluno.txt";
        private static AlunoDAO INSTANCIA;

        private Map<Integer, Aluno> cache = new HashMap<>();

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
            Curriculo curriculo = null;

            Aluno aluno = new Aluno();
            aluno.setCodPessoa(codPessoa);
            aluno.setMatricula(matricula);
            aluno.setNome(nome);
            aluno.setEmail(email);
            aluno.setSenha(senha);
            aluno.setAtivo(isAtivo);
            aluno.setDataCadastro(dataCadastro);

            try {
                curriculo = CurriculoDAO.getDAO().carregarPorId(dados[7]);
                aluno.setCurriculo(curriculo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            cache.put(codPessoa, aluno);
            return aluno;
        }

        @Override
        public Aluno carregarPorId(String id) throws IOException {
            int idConvertido = Integer.parseInt(id);

            if (cache.containsKey(idConvertido)) {
                return cache.get(idConvertido);
            }

            return super.carregarPorId(id);
        }
    }
