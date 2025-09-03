import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

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
    static Scanner in = new Scanner(System.in);
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

        String header = "Sistema EMA de matrículas";
        String[] opcoes = {
                "1 - Acesso para secretária.",
                "2 - Acesso para professor.",
                "3 - Acesso para aluno.",
                "0 - Encerrar sistema.",
        };
        int flag;
        do {
            imprimirMenu(header, opcoes);
            flag = in.nextInt();

        } while (flag != 0);
    }

    static void imprimirMenu(String header, String[] opcoes) {
        out.println(gerarHeaderMenu(header));
        for (String opc : opcoes) {
            out.println(preencherLinha(opc));
        }
        out.print(solicitarInput());
    }
    static String gerarHeaderMenu(String header) {
        StringBuilder linha = new StringBuilder();
        final int MAX_CARACTERES = 45;
        int blanks = MAX_CARACTERES - (header.length() + 6);

        linha.append("#".repeat(MAX_CARACTERES))
                .append("\n");

        linha.append("###");
        linha.append(" ".repeat(Math.max(0, blanks / 2)));
        linha.append(header);
        linha.append(" ".repeat(Math.max(0, (blanks / 2 + blanks % 2))));
        linha.append("###")
                .append("\n");

        linha.append("#".repeat(MAX_CARACTERES));
        return linha.toString();
    }
    static String preencherLinha(String texto) {
        StringBuilder linha = new StringBuilder();

        final int MAX_CARACTERES = 45;
        int blanks = MAX_CARACTERES - (texto.length() + 2);

        linha.append("#");
        linha.append(" ".repeat(Math.max(0, blanks / 2)));
        linha.append(texto);
        linha.append(" ".repeat(Math.max(0, (blanks / 2 + blanks % 2))));
        linha.append("#");

        return linha.toString();
    }
    static String solicitarInput() {
        String texto = "Digite a opção desejada:";
        StringBuilder linha = new StringBuilder();

        final int MAX_CARACTERES = 45;
        int hashtags = MAX_CARACTERES - (texto.length() + 3);

        linha.append("#".repeat(MAX_CARACTERES))
                .append("\n");
        linha.append("#".repeat(hashtags))
                .append(" ");
        linha.append(texto)
                .append(" ");
        return linha.toString();
    }
}
