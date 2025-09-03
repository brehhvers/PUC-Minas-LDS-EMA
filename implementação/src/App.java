import java.io.PrintStream;
import java.util.ArrayList;

import Business.Pessoa.Secretaria;
import Business.Pessoa.Usuario;
import Data.DAO.AlunoDAO;
import Data.DAO.DAO;
import Data.DAO.ProfessorDAO;
import Data.DAO.SecretariaDAO;
import Enum.TipoAcesso;

public class App {
    static PrintStream out = System.out;
    static SecretariaDAO secretariaDAO = SecretariaDAO.getDAO();
    static ArrayList<Secretaria> secretarias = new ArrayList<>();

    static void inicializaSecretarias() {
        try {
            secretarias.addAll(secretariaDAO.carregar());
            if (secretarias.isEmpty()) {
                Secretaria novaSecretaria = new Secretaria(
                        "Alice",
                        "alice@jabberwock",
                        "alice"
                );
                secretariaDAO.salvar(novaSecretaria);
                inicializaSecretarias();
            }
        } catch (Exception e) {
            out.println(e.getMessage() + " Ao inicializar secretárias.");
        }
    }

    static boolean direcionaAutenticacao(TipoAcesso cargo, String infoAcesso) {
        return switch (cargo) {
            case SECRETARIA -> checaAutorizacao(infoAcesso, SecretariaDAO.getDAO());
            case PROFESSOR -> checaAutorizacao(infoAcesso, ProfessorDAO.getDAO());
            case ALUNO -> checaAutorizacao(infoAcesso, AlunoDAO.getDAO());
        };
    }

    static <T> boolean checaAutorizacao(String infoAcesso, DAO<T> dao) {
        infoAcesso = infoAcesso.trim();
        String[] infos = infoAcesso.split(";");

        ArrayList<T> usuarios;
        if (infos.length == 2) {
            try {
                usuarios = new ArrayList<>(dao.carregar());
            } catch (Exception e) {
                out.println(e.getMessage());
                return false;
            }
            for (T usuario : usuarios) {
                if (usuario instanceof Usuario) {
                    if (((Usuario) usuario).getCodPessoa() == (Integer.parseInt(infos[0])) &&
                            ((Usuario) usuario).getSenha().equals(infos[1])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static void main(String[] args) {
        inicializaSecretarias();
        out.println(direcionaAutenticacao(TipoAcesso.PROFESSOR, "954619;glados"));
    }
}
