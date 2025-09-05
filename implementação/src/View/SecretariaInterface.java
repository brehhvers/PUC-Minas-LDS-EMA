package View;

import java.util.ArrayList;
import java.util.Scanner;

import Business.Curriculo;
import Business.Curso;
import Business.Disciplina;
import Business.Pessoa.Aluno;
import Business.Pessoa.Professor;
import Business.Pessoa.Secretaria;
import Enum.TipoDisciplina;
import Interface.IEfetivavel;

public class SecretariaInterface {
    private Scanner in;
    private Secretaria secretaria;
    private ArrayList<Aluno> alunos;
    private ArrayList<Professor> professores;
    private ArrayList<Secretaria> secretarias;
    private ArrayList<Curso> cursos;
    private ArrayList<Disciplina> disciplinas;
    private ArrayList<Curriculo> curriculos;

    public SecretariaInterface(Scanner in, Secretaria secretaria, 
                             ArrayList<Aluno> alunos, 
                             ArrayList<Professor> professores,
                             ArrayList<Secretaria> secretarias,
                             ArrayList<Curso> cursos,
                             ArrayList<Disciplina> disciplinas,
                             ArrayList<Curriculo> curriculos) {
        this.in = in;
        this.secretaria = secretaria;
        this.alunos = alunos;
        this.professores = professores;
        this.secretarias = secretarias;
        this.cursos = cursos;
        this.disciplinas = disciplinas;
        this.curriculos = curriculos;
    }

    public void menu() {
        int opcao;
        String header = "Menu da Secretaria";
        String[] opcoes = {
                "1 - Cadastrar usuário",
                "2 - Listar usuários",
                "3 - Criar disciplina",
                "4 - Criar curso",
                "5 - Criar currículo",
                "6 - Listar disciplinas",
                "7 - Listar cursos",
                "8 - Listar currículos",
                "9 - Consolidar matrículas",
                "0 - Voltar ao menu principal"
        };

        do {
            Menu.imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1 -> menuCadastrarUsuario();
                case 2 -> menuListarUsuarios();
                case 3 -> criarDisciplina();
                case 4 -> criarCurso();
                case 5 -> criarCurriculo();
                case 6 -> listarDisciplinas();
                case 7 -> listarCursos();
                case 8 -> listarCurriculos();
                case 9 -> consolidarMatriculas();
                case 0 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuCadastrarUsuario() {
        int opcao;
        String header = "Cadastrar Usuário";
        String[] opcoes = {
                "1 - Cadastrar Aluno",
                "2 - Cadastrar Professor",
                "3 - Cadastrar Secretária",
                "0 - Voltar"
        };

        do {
            Menu.imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1 -> cadastrarAluno();
                case 2 -> cadastrarProfessor();
                case 3 -> cadastrarSecretaria();
                case 0 -> System.out.println("Retornando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuListarUsuarios() {
        int opcao;
        String header = "Listar Usuários";
        String[] opcoes = {
                "1 - Listar Alunos",
                "2 - Listar Professores",
                "3 - Listar Secretárias",
                "0 - Voltar"
        };

        do {
            Menu.imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1 -> listarAlunos();
                case 2 -> listarProfessores();
                case 3 -> listarSecretarias();
                case 0 -> System.out.println("Retornando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarAluno() {
        System.out.println("\n=== CADASTRAR ALUNO ===");
        
        System.out.print("Nome: ");
        String nome = in.nextLine();
        
        System.out.print("Email: ");
        String email = in.nextLine();
        
        System.out.print("Senha: ");
        String senha = in.nextLine();
        
        try {
            Aluno novoAluno = secretaria.cadastrarAluno(nome, email, senha);
            alunos.add(novoAluno);
            System.out.println("Aluno cadastrado com sucesso!");
            System.out.println("Código da pessoa: " + novoAluno.getCodPessoa());
            System.out.println("Matrícula: " + novoAluno.getMatricula());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    private void cadastrarProfessor() {
        System.out.println("\n=== CADASTRAR PROFESSOR ===");
        
        System.out.print("Nome: ");
        String nome = in.nextLine();
        
        System.out.print("Email: ");
        String email = in.nextLine();
        
        System.out.print("Senha: ");
        String senha = in.nextLine();
        
        try {
            Professor novoProfessor = secretaria.cadastrarProfessor(nome, email, senha);
            professores.add(novoProfessor);
            System.out.println("Professor cadastrado com sucesso!");
            System.out.println("Código da pessoa: " + novoProfessor.getCodPessoa());
            System.out.println("Matrícula: " + novoProfessor.getMatricula());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
        }
    }

    private void cadastrarSecretaria() {
        System.out.println("\n=== CADASTRAR SECRETÁRIA ===");
        
        System.out.print("Nome: ");
        String nome = in.nextLine();
        
        System.out.print("Email: ");
        String email = in.nextLine();
        
        System.out.print("Senha: ");
        String senha = in.nextLine();
        
        try {
            Secretaria novaSecretaria = secretaria.cadastrarSecretaria(nome, email, senha);
            secretarias.add(novaSecretaria);
            System.out.println("Secretária cadastrada com sucesso!");
            System.out.println("Código da pessoa: " + novaSecretaria.getCodPessoa());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar secretária: " + e.getMessage());
        }
    }

    private void listarAlunos() {
        System.out.println("\n=== LISTA DE ALUNOS ===");
        
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            for (Aluno aluno : alunos) {
                System.out.println(aluno);
                System.out.println("---");
            }
        }
    }

    private void listarProfessores() {
        System.out.println("\n=== LISTA DE PROFESSORES ===");
        
        if (professores.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
        } else {
            for (Professor professor : professores) {
                System.out.println(professor);
                System.out.println("---");
            }
        }
    }

    private void listarSecretarias() {
        System.out.println("\n=== LISTA DE SECRETÁRIAS ===");
        
        if (secretarias.isEmpty()) {
            System.out.println("Nenhuma secretária cadastrada.");
        } else {
            for (Secretaria secretaria : secretarias) {
                System.out.println(secretaria);
                System.out.println("---");
            }
        }
    }

    private void criarDisciplina() {
        System.out.println("\n=== CRIAR DISCIPLINA ===");
        
        System.out.println("Tipo de disciplina:");
        System.out.println("1 - Obrigatória");
        System.out.println("2 - Optativa");
        System.out.print("Escolha: ");
        
        int tipoOpcao = in.nextInt();
        in.nextLine();
        
        TipoDisciplina tipo;
        switch (tipoOpcao) {
            case 1 -> tipo = TipoDisciplina.OBRIGATORIA;
            case 2 -> tipo = TipoDisciplina.OPTATIVA;
            default -> {
                System.out.println("Opção inválida!");
                return;
            }
        }
        
        try {
            Disciplina novaDisciplina = secretaria.criarDisciplina(tipo);
            disciplinas.add(novaDisciplina);
            System.out.println("Disciplina criada com sucesso!");
            System.out.println("ID da disciplina: " + novaDisciplina.getId());
            System.out.println("Tipo: " + novaDisciplina.getTipo());
        } catch (Exception e) {
            System.out.println("Erro ao criar disciplina: " + e.getMessage());
        }
    }

    private void criarCurso() {
        System.out.println("\n=== CRIAR CURSO ===");
        
        System.out.print("Nome do curso: ");
        String nome = in.nextLine();
        
        System.out.print("Número de créditos: ");
        int numCreditos = in.nextInt();
        in.nextLine();
        
        try {
            Curso novoCurso = secretaria.criarCurso(nome, numCreditos);
            cursos.add(novoCurso);
            System.out.println("Curso criado com sucesso!");
            System.out.println("ID do curso: " + novoCurso.getId());
            System.out.println("Nome: " + novoCurso.getNome());
            System.out.println("Créditos: " + novoCurso.getNumCreditos());
        } catch (Exception e) {
            System.out.println("Erro ao criar curso: " + e.getMessage());
        }
    }

    private void criarCurriculo() {
        System.out.println("\n=== CRIAR CURRÍCULO ===");
        
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso disponível. Crie um curso primeiro.");
            return;
        }
        
        System.out.println("Cursos disponíveis:");
        for (Curso curso : cursos) {
            System.out.println("ID: " + curso.getId() + " - " + curso.getNome());
        }
        
        System.out.print("ID do curso: ");
        int cursoId = in.nextInt();
        in.nextLine();
        
        Curso cursoSelecionado = cursos.stream()
                .filter(c -> c.getId() == cursoId)
                .findFirst()
                .orElse(null);
        
        if (cursoSelecionado == null) {
            System.out.println("Curso não encontrado!");
            return;
        }
        
        try {
            Curriculo novoCurriculo = secretaria.criarCurriculo(cursoSelecionado);
            curriculos.add(novoCurriculo);
            System.out.println("Currículo criado com sucesso!");
            System.out.println("ID do currículo: " + novoCurriculo.getId());
            System.out.println("Curso: " + novoCurriculo.getCurso().getNome());
        } catch (Exception e) {
            System.out.println("Erro ao criar currículo: " + e.getMessage());
        }
    }

    private void listarDisciplinas() {
        System.out.println("\n=== LISTA DE DISCIPLINAS ===");
        
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
        } else {
            for (Disciplina disciplina : disciplinas) {
                System.out.println(disciplina);
                System.out.println("---");
            }
        }
    }

    private void listarCursos() {
        System.out.println("\n=== LISTA DE CURSOS ===");
        
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
        } else {
            for (Curso curso : cursos) {
                System.out.println(curso);
                System.out.println("---");
            }
        }
    }

    private void listarCurriculos() {
        System.out.println("\n=== LISTA DE CURRÍCULOS ===");
        
        if (curriculos.isEmpty()) {
            System.out.println("Nenhum currículo cadastrado.");
        } else {
            for (Curriculo curriculo : curriculos) {
                System.out.println(curriculo);
                System.out.println("---");
            }
        }
    }

    private void consolidarMatriculas() {
        System.out.println("\n=== CONSOLIDAR MATRÍCULAS ===");
        
        try {
            // Converter ArrayList<Disciplina> para ArrayList<IEfetivavel>
            ArrayList<IEfetivavel> disciplinasEfetivaveis = new ArrayList<>(disciplinas);
            secretaria.consolidarMatriculas(disciplinasEfetivaveis, alunos);
            System.out.println("Matrículas consolidadas com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao consolidar matrículas: " + e.getMessage());
        }
    }
}
