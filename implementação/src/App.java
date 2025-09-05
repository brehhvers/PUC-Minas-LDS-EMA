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
import Enum.TipoDisciplina;
import View.AlunoView;
import View.Menu;
import View.ProfessorView;
import View.SecretariaView;

public class App {
    static PrintStream out = System.out;
    static Scanner in = new Scanner(System.in, "UTF-8");

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
        } catch (Exception e) {
            out.println("Aviso: Não foi possível carregar disciplinas: " + e.getMessage());
        }

        try {
            professores.addAll(professorDAO.carregar());
        } catch (Exception e) {
            out.println("Aviso: Não foi possível carregar professores: " + e.getMessage());
        }

        try {
            planosDeEnsino.addAll(planoDeEnsinoDAO.carregar());
        } catch (Exception e) {
            out.println("Aviso: Não foi possível carregar planos de ensino: " + e.getMessage());
        }

        try {
            alunos.addAll(alunoDAO.carregar());
        } catch (Exception e) {
            out.println("Aviso: Não foi possível carregar alunos: " + e.getMessage());
        }

        try {
            cursos.addAll(cursoDAO.carregar());
        } catch (Exception e) {
            out.println("Aviso: Não foi possível carregar cursos: " + e.getMessage());
        }

        try {
            curriculos.addAll(curriculoDAO.carregar());
        } catch (Exception e) {
            out.println("Aviso: Não foi possível carregar currículos: " + e.getMessage());
        }

        try {
            secretarias.addAll(secretariaDAO.carregar());
        } catch (Exception e) {
            out.println("Aviso: Não foi possível carregar secretarias: " + e.getMessage());
        }

        popularSistemaInicial();
    }

    static void popularSistemaInicial() {
        boolean sistemaVazio = secretarias.isEmpty() && professores.isEmpty() &&
                alunos.isEmpty() && cursos.isEmpty() && disciplinas.isEmpty();

        if (sistemaVazio) {
            out.println("=== POPULANDO SISTEMA COM DADOS INICIAIS ===");

            Secretaria secretariaPadrao = new Secretaria(
                    "Alice",
                    "alice@universidade.edu",
                    "alice");
            secretarias.add(secretariaPadrao);

            out.println("Secretária padrão criada:");
            out.println("Nome: " + secretariaPadrao.getNome());
            out.println("Código de Pessoa: " + secretariaPadrao.getCodPessoa());
            out.println("===============================");

            Professor professorPadrao = new Professor(
                    "Dr. João Silva",
                    "joao.silva@universidade.edu",
                    "prof123");
            professores.add(professorPadrao);

            out.println("Professor padrão criado:");
            out.println("Nome: " + professorPadrao.getNome());
            out.println("Código de Pessoa: " + professorPadrao.getCodPessoa());
            out.println("===============================");

            Curso cursoPadrao = new Curso("Ciência da Computação", 240);
            cursos.add(cursoPadrao);

            out.println("Curso padrão criado: Ciência da Computação");
            out.println("===============================");

            Curriculo curriculoPadrao = new Curriculo(cursoPadrao);
            curriculos.add(curriculoPadrao);

            out.println("Currículo padrão criado para Ciência da Computação");
            out.println("===============================");

            Aluno alunoPadrao = new Aluno(
                    "Branca Letícia de Barros Motta",
                    "branca.motta@universidade.edu",
                    "brancamotta");
            alunos.add(alunoPadrao);

            out.println("Aluna padrão criada:");
            out.println("Nome: " + alunoPadrao.getNome());
            out.println("Código de Pessoa: " + alunoPadrao.getCodPessoa());
            out.println("===============================");

            Disciplina disciplina1 = new Disciplina(TipoDisciplina.OBRIGATORIA);
            disciplina1.setNome("Programação I");
            disciplina1.setValor(150.0);
            disciplina1.setProfessor(professorPadrao);
            disciplinas.add(disciplina1);

            Disciplina disciplina2 = new Disciplina(TipoDisciplina.OBRIGATORIA);
            disciplina2.setNome("Estruturas de Dados");
            disciplina2.setValor(200.0);
            disciplina2.setProfessor(professorPadrao);
            disciplinas.add(disciplina2);

            Disciplina disciplina3 = new Disciplina(TipoDisciplina.OPTATIVA);
            disciplina3.setNome("Inteligência Artificial");
            disciplina3.setValor(180.0);
            disciplina3.setProfessor(professorPadrao);
            disciplinas.add(disciplina3);

            Disciplina disciplina4 = new Disciplina(TipoDisciplina.OBRIGATORIA);
            disciplina4.setNome("Banco de Dados");
            disciplina4.setValor(170.0);
            disciplina4.setProfessor(professorPadrao);
            disciplinas.add(disciplina4);

            Disciplina disciplina5 = new Disciplina(TipoDisciplina.OBRIGATORIA);
            disciplina5.setNome("Sistemas Operacionais");
            disciplina5.setValor(160.0);
            disciplina5.setProfessor(professorPadrao);
            disciplinas.add(disciplina5);

            Disciplina disciplina6 = new Disciplina(TipoDisciplina.OBRIGATORIA);
            disciplina6.setNome("Redes de Computadores");
            disciplina6.setValor(180.0);
            disciplina6.setProfessor(professorPadrao);
            disciplinas.add(disciplina6);

            Disciplina disciplina7 = new Disciplina(TipoDisciplina.OPTATIVA);
            disciplina7.setNome("Computação Gráfica");
            disciplina7.setValor(190.0);
            disciplina7.setProfessor(professorPadrao);
            disciplinas.add(disciplina7);

            Disciplina disciplina8 = new Disciplina(TipoDisciplina.OPTATIVA);
            disciplina8.setNome("Segurança da Informação");
            disciplina8.setValor(200.0);
            disciplina8.setProfessor(professorPadrao);
            disciplinas.add(disciplina8);

            curriculoPadrao.addDisciplina(disciplina1.getNome());
            curriculoPadrao.addDisciplina(disciplina2.getNome());
            curriculoPadrao.addDisciplina(disciplina3.getNome());
            curriculoPadrao.addDisciplina(disciplina4.getNome());
            curriculoPadrao.addDisciplina(disciplina5.getNome());
            curriculoPadrao.addDisciplina(disciplina6.getNome());
            curriculoPadrao.addDisciplina(disciplina7.getNome());
            curriculoPadrao.addDisciplina(disciplina8.getNome());

            out.println(
                    "Disciplinas padrão criadas: Programação I, Estruturas de Dados, IA, Banco de Dados, SO, Redes, Computação Gráfica, Segurança da Informação");

            out.println("===============================");
            out.println("=== SISTEMA POPULADO COM SUCESSO ===\n");

        } else if (secretarias.isEmpty()) {
            Secretaria secretariaPadrao = new Secretaria(
                    "Alice",
                    "alice@universidade.edu",
                    "alice");
            secretarias.add(secretariaPadrao);

            out.println("=== SISTEMA INICIALIZADO ===");
            out.println("Secretária padrão criada:");
            out.println("Nome: " + secretariaPadrao.getNome());
            out.println("Código de Pessoa: " + secretariaPadrao.getCodPessoa());
            out.println("===============================");
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

    static void rotinaAluno() {
        try {
            Aluno aluno = (Aluno) direcionaAutenticacao(TipoAcesso.ALUNO);
            out.println("Bem-vindo(a), " + aluno.getNome() + "!");

            AlunoView alunoView = new AlunoView(in, aluno, disciplinas);
            alunoView.menu();

        } catch (Exception e) {
            out.println(e.getMessage());
            return;
        }
    }

    static void rotinaProfessor() {
        try {
            Professor professor = (Professor) direcionaAutenticacao(TipoAcesso.PROFESSOR);
            out.println("Bem-vindo(a), " + professor.getNome() + "!");

            ProfessorView professorView = new ProfessorView(in, professor);
            professorView.menu();

        } catch (Exception e) {
            out.println(e.getMessage());
            return;
        }
    }

    static void rotinaSecretaria() {
        try {
            Secretaria secretaria = (Secretaria) direcionaAutenticacao(TipoAcesso.SECRETARIA);
            out.println("Bem-vindo(a), " + secretaria.getNome() + "!");

            SecretariaView secretariaView = new SecretariaView(
                    in, secretaria, alunos, professores, secretarias,
                    disciplinas, cursos, curriculos);
            secretariaView.menu();

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
                case 0 -> out.println("Encerrando o sistema...Fica com DEUS!");
                default -> out.println("Opção inválida!");
            }
        } while (opcao != 0);

        persistirDados();
        in.close();
    }
}
