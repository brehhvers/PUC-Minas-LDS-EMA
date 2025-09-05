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
import View.Menu;
import View.ProfessorInterface;

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
        } catch (Exception e) {
            out.println("Erro ao carregar dados: " + e.getMessage());
        }
        
        // Verificar se secretária está vazia e popular
        if (secretarias.isEmpty()) {
            out.println("Secretária não encontrada. Criando secretária padrão...");
            popularSecretaria();
        }
    }
    
    static void popularSecretaria() {
        try {
            Secretaria secretaria = new Secretaria("seubarriga", "seubarriga@email.com", "seubarriga");
            secretarias.add(secretaria);
            secretariaDAO.salvar(secretaria);
            out.println("Secretária criada com sucesso!");
            out.println("Credenciais: seubarriga (código " + secretaria.getCodPessoa() + ", senha: seubarriga)");
        } catch (Exception e) {
            out.println("Erro ao criar secretária: " + e.getMessage());
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
                case 1 -> {
                    if (!secretarias.isEmpty()) {
                        View.SecretariaInterface secretariaInterface = new View.SecretariaInterface(in, secretarias.get(0));
                        secretariaInterface.menuSecretaria();
                    } else {
                        out.println("Nenhuma secretária cadastrada no sistema.");
                    }
                }
                case 2 -> rotinaProfessor();
                case 3 -> out.println("Aluno");
                default -> out.println("Opção inválida!");
            }
        } while (opcao != 0);

        persistirDados();
        in.close();
    }
}
