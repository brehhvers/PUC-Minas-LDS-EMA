import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import Business.Curriculo;
import Business.Curso;
import Business.Disciplina;
import Business.PlanoDeEnsino;
import Business.Pessoa.Aluno;
import Business.Pessoa.Professor;
import Business.Pessoa.Secretaria;
import Business.Pessoa.Usuario;
import Data.DAO.AlunoDAO;
import Data.DAO.CurriculoDAO;
import Data.DAO.CursoDAO;
import Data.DAO.DisciplinaDAO;
import Data.DAO.PlanoDeEnsinoDAO;
import Data.DAO.ProfessorDAO;
import Data.DAO.SecretariaDAO;
import Enum.TipoAcesso;
import View.AlunoInterface;
import View.Menu;
import View.ProfessorInterface;
import View.SecretariaInterface;

public class App {
    static PrintStream out = System.out;
    static Scanner in = new Scanner(System.in);

    static AlunoDAO alunoDAO = AlunoDAO.getDAO();
    static CursoDAO cursoDAO = CursoDAO.getDAO();
    static CurriculoDAO curriculoDAO = CurriculoDAO.getDAO();
    static ProfessorDAO professorDAO = ProfessorDAO.getDAO();
    static DisciplinaDAO disciplinaDAO = DisciplinaDAO.getDAO();
    static SecretariaDAO secretariaDAO = SecretariaDAO.getDAO();
    static PlanoDeEnsinoDAO planoDeEnsinoDAO = PlanoDeEnsinoDAO.getDAO();

    static ArrayList<Aluno> alunos = new ArrayList<>();
    static ArrayList<Curso> cursos = new ArrayList<>();
    static ArrayList<Curriculo> curriculos = new ArrayList<>();
    static ArrayList<Professor> professores = new ArrayList<>();
    static ArrayList<Secretaria> secretarias = new ArrayList<>();
    static ArrayList<Disciplina> disciplinas = new ArrayList<>();
    static ArrayList<PlanoDeEnsino> planosDeEnsino = new ArrayList<>();

    static void inicializador() {
        try {
            disciplinas.addAll(disciplinaDAO.carregar());
            professores.addAll(professorDAO.carregar());

            planosDeEnsino.addAll(planoDeEnsinoDAO.carregar());
            alunos.addAll(alunoDAO.carregar());

            cursos.addAll(cursoDAO.carregar());
            curriculos.addAll(curriculoDAO.carregar());
            secretarias.addAll(secretariaDAO.carregar());

            if (secretarias.isEmpty()) {
                Secretaria novaSecretaria = new Secretaria(
                        "Alice",
                        "alice@jabberwock",
                        "alice");
                secretarias.add(novaSecretaria);
                
                out.println("=== SISTEMA INICIALIZADO ===");
                out.println("Secretária padrão criada:");
                out.println("Nome: " + novaSecretaria.getNome());
                out.println("Código de Pessoa: " + novaSecretaria.getCodPessoa());
                out.println("Senha: alice");
                out.println("===============================");
            }
        } catch (Exception e) {
            out.println(e.getMessage() + " Ao inicializar usuários.");
        }
    }

    static void persistirDados() {
        try {
            disciplinaDAO.salvarTodos(disciplinas);
            professorDAO.salvarTodos(professores);
            planoDeEnsinoDAO.salvarTodos(planosDeEnsino);
            alunoDAO.salvarTodos(alunos);
            cursoDAO.salvarTodos(cursos);
            curriculoDAO.salvarTodos(curriculos);
            secretariaDAO.salvarTodos(secretarias);

            out.println("Dados foram salvos com sucesso!");
        } catch (Exception e) {
            out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    static Usuario direcionaAutenticacao(TipoAcesso acesso) {
        Usuario usuario = switch (acesso) {
            case ALUNO -> checaAutorizacao(alunos);
            case PROFESSOR -> checaAutorizacao(professores);
            case SECRETARIA -> checaAutorizacao(secretarias);
        };

        if (usuario != null) {
            return usuario;
        } else {
            throw new RuntimeException("Credenciais inválidas.");
        }
    }

    static Usuario checaAutorizacao(ArrayList<? extends Usuario> usuarios) {
        out.print("Informe o seu código de pessoa: ");
        int codPessoa = in.nextInt();
        in.nextLine();

        out.print("Informe a sua senha: ");
        String senha = in.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario.getCodPessoa() == codPessoa && usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }

        return null;
    }

    static void rotinaSecretaria() {
        try {
            Secretaria secretaria = (Secretaria) direcionaAutenticacao(TipoAcesso.SECRETARIA);
            out.println("Bem-vinda, " + secretaria.getNome() + "!");

            SecretariaInterface secretariaInterface = new SecretariaInterface(
                in, secretaria, alunos, professores, secretarias, 
                disciplinas, cursos, curriculos);
            secretariaInterface.menu();

        } catch (Exception e) {
            out.println(e.getMessage());
            return;
        }
    }

    static void rotinaProfessor() {
        try {
            Professor professor = (Professor) direcionaAutenticacao(TipoAcesso.PROFESSOR);
            out.println("Bem-vindo(a), " + professor.getNome() + "!");

            ProfessorInterface professorInterface = new ProfessorInterface(in, professor);
            professorInterface.menu();

        } catch (Exception e) {
            out.println(e.getMessage());
            return;
        }
    }

    static void rotinaAluno() {
        try {
            Aluno aluno = (Aluno) direcionaAutenticacao(TipoAcesso.ALUNO);
            out.println("Bem-vindo(a), " + aluno.getNome() + "!");

            AlunoInterface alunoInterface = new AlunoInterface(in, aluno, disciplinas);
            alunoInterface.menu();

        } catch (Exception e) {
            out.println(e.getMessage());
            return;
        }
    }

    public static void main(String[] args) {
        inicializador();

        int opcao;
        String header = "Sistema EMA de matrículas";
        String[] opcoes = {
                "1 - Acesso para secretária.",
                "2 - Acesso para professor.",
                "3 - Acesso para aluno.",
                "0 - Encerrar sistema.",
        };

        do {
            Menu.imprimirMenu(header, opcoes);
            opcao = in.nextInt();

            switch (opcao) {
                case 1 -> rotinaSecretaria();
                case 2 -> rotinaProfessor();
                case 3 -> rotinaAluno();
                default -> out.println("Opção inválida!");
            }
        } while (opcao != 0);

        persistirDados();
        in.close();
    }
}
