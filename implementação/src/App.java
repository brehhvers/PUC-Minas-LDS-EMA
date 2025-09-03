import java.io.PrintStream;
import java.util.ArrayList;

import Business.Pessoa.Aluno;
import Business.Pessoa.Professor;
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
    static ProfessorDAO professorDAO = ProfessorDAO.getDAO();
    static AlunoDAO alunoDAO = AlunoDAO.getDAO();
    static ArrayList<Secretaria> secretarias = new ArrayList<>();
    static ArrayList<Professor> professores = new ArrayList<>();
    static ArrayList<Aluno> alunos = new ArrayList<>();

    static void inicializador() {
        try {
            secretarias.addAll(secretariaDAO.carregar());
            if (secretarias.isEmpty()) {
                Secretaria novaSecretaria = new Secretaria(
                        "Alice",
                        "alice@jabberwock",
                        "alice"
                );
                secretarias.add(novaSecretaria);
            }
            professores.addAll(professorDAO.carregar());
            alunos.addAll(alunoDAO.carregar());
        } catch (Exception e) {
            out.println(e.getMessage() + " Ao inicializar usuários.");
        }
    }

    static boolean direcionaAutenticacao(TipoAcesso cargo, String infoAcesso) {
        return switch (cargo) {
            case SECRETARIA -> checaAutorizacao(infoAcesso, secretarias);
            case PROFESSOR -> checaAutorizacao(infoAcesso, professores);
            case ALUNO -> checaAutorizacao(infoAcesso, alunos);
        };
    }

    static <T> boolean checaAutorizacao(String infoAcesso, ArrayList<T> usuarios) {
        infoAcesso = infoAcesso.trim();
        String[] infos = infoAcesso.split(";");
        if (infos.length != 2) {
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
        return false;
    }


    public static void main(String[] args) {
        inicializador();
        out.println(direcionaAutenticacao(TipoAcesso.SECRETARIA, "954617;alice"));
    }
}
