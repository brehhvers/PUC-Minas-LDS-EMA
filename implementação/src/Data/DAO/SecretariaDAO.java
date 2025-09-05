package Data.DAO;

import java.time.LocalDate;
import Business.Pessoa.Secretaria;

public class SecretariaDAO extends DAO<Secretaria> {
    private static final String CAMINHO_ARQUIVO = "src/Data/File/secretaria.txt";
    private static SecretariaDAO INSTANCIA;

    private SecretariaDAO() {
        super(CAMINHO_ARQUIVO);
    }

    static {
        INSTANCIA = new SecretariaDAO();
    }

    public static SecretariaDAO getDAO() {
        return INSTANCIA;
    }

    @Override
    protected Secretaria parse(String linha) {
        String[] dados = linha.split(";");
        
        int codPessoa = Integer.parseInt(dados[0]);
        if (this.cache.containsKey(codPessoa)) {
            return this.cache.get(codPessoa);
        }
        
        String nome = dados[1];
        String email = dados[2];
        String senha = dados[3];
        boolean isAtivo = Boolean.parseBoolean(dados[4]);
        LocalDate dataCadastro = LocalDate.parse(dados[5]);
        
        Secretaria secretaria = new Secretaria();
        secretaria.setCodPessoa(codPessoa);
        secretaria.setNome(nome);
        secretaria.setEmail(email);
        secretaria.setSenha(senha);
        secretaria.setAtivo(isAtivo);
        secretaria.setDataCadastro(dataCadastro);

        this.cache.put(codPessoa, secretaria);
        return secretaria;
    }
}
