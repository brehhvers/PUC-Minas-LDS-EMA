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

public class SecretariaView {
    private Scanner in;
    private Secretaria secretaria;

    private ArrayList<Curso> cursos;
    private ArrayList<Aluno> alunos;
    private ArrayList<Curriculo> curriculos;
    private ArrayList<Professor> professores;
    private ArrayList<Secretaria> secretarias;
    private ArrayList<Disciplina> disciplinas;

    public SecretariaView(Scanner in, Secretaria secretaria,
            ArrayList<Aluno> alunos, ArrayList<Professor> professores,
            ArrayList<Secretaria> secretarias, ArrayList<Disciplina> disciplinas,
            ArrayList<Curso> cursos, ArrayList<Curriculo> curriculos) {
        this.in = in;
        this.alunos = alunos;
        this.cursos = cursos;
        this.curriculos = curriculos;
        this.secretaria = secretaria;
        this.professores = professores;
        this.secretarias = secretarias;
        this.disciplinas = disciplinas;
    }

    public void menu() {
        int opcao;
        String header = "Menu da Secretaria";
        String[] opcoes = {
                "1 - Gerenciar Disciplinas",
                "2 - Gerenciar Cursos",
                "3 - Gerenciar Currículos",
                "4 - Gerenciar Usuários",
                "5 - Consolidar Matrículas",
                "6 - Relatórios",
                "7 - Exibir detalhes do secretario(a)",
                "0 - Voltar ao menu principal"
        };

        do {
            Menu.imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1 -> menuDisciplinas();
                case 2 -> menuCursos();
                case 3 -> menuCurriculos();
                case 4 -> menuUsuarios();
                case 5 -> consolidarMatriculas();
                case 6 -> menuRelatorios();
                case 7 -> listarDetalhesSecretaria();
                case 0 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void listarDetalhesSecretaria() {
        System.out.println(this.secretaria);
    }

    private void menuDisciplinas() {
        int opcao;
        String header = "Gerenciar Disciplinas";
        String[] opcoes = {
                "1 - Criar nova disciplina",
                "2 - Listar disciplinas",
                "3 - Editar disciplina",
                "4 - Ativar/Desativar disciplina",
                "5 - Atribuir professor a disciplina",
                "0 - Voltar"
        };

        do {
            Menu.imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1 -> criarDisciplina();
                case 2 -> listarDisciplinas();
                case 3 -> editarDisciplina();
                case 4 -> toggleStatusDisciplina();
                case 5 -> atribuirProfessorDisciplina();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuCursos() {
        int opcao;
        String header = "Gerenciar Cursos";
        String[] opcoes = {
                "1 - Criar novo curso",
                "2 - Listar cursos",
                "3 - Editar curso",
                "0 - Voltar"
        };

        do {
            Menu.imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1 -> criarCurso();
                case 2 -> listarCursos();
                case 3 -> editarCurso();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuCurriculos() {
        int opcao;
        String header = "Gerenciar Currículos";
        String[] opcoes = {
                "1 - Criar novo currículo",
                "2 - Listar currículos",
                "3 - Adicionar disciplina ao currículo",
                "4 - Remover disciplina do currículo",
                "0 - Voltar"
        };

        do {
            Menu.imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1 -> criarCurriculo();
                case 2 -> listarCurriculos();
                case 3 -> adicionarDisciplinaCurriculo();
                case 4 -> removerDisciplinaCurriculo();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuUsuarios() {
        int opcao;
        String header = "Gerenciar Usuários";
        String[] opcoes = {
                "1 - Cadastrar aluno",
                "2 - Cadastrar professor",
                "3 - Cadastrar secretária",
                "4 - Listar alunos",
                "5 - Listar professores",
                "6 - Listar secretárias",
                "7 - Editar secretária",
                "8 - Editar professor",
                "9 - Editar aluno",
                "10 - Atribuir currículo a aluno",
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
                case 4 -> listarAlunos();
                case 5 -> listarProfessores();
                case 6 -> listarSecretarias();
                case 7 -> editarSecretaria();
                case 8 -> editarProfessor();
                case 9 -> editarAluno();
                case 10 -> atribuirCurriculoAluno();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuRelatorios() {
        int opcao;
        String header = "Relatórios";
        String[] opcoes = {
                "1 - Relatório de matrículas por disciplina",
                "2 - Relatório de disciplinas por status",
                "3 - Relatório de alunos por curso",
                "4 - Relatório financeiro",
                "0 - Voltar"
        };

        do {
            Menu.imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1 -> relatorioMatriculasPorDisciplina();
                case 2 -> relatorioDisciplinasPorStatus();
                case 3 -> relatorioAlunosPorCurso();
                case 4 -> relatorioFinanceiro();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void criarDisciplina() {
        System.out.println("Tipo da disciplina:");
        System.out.println("1 - Obrigatória");
        System.out.println("2 - Optativa");
        System.out.print("Escolha: ");
        int tipo = in.nextInt();
        in.nextLine();

        TipoDisciplina tipoDisciplina = (tipo == 1) ? TipoDisciplina.OBRIGATORIA : TipoDisciplina.OPTATIVA;

        try {
            Disciplina novaDisciplina = secretaria.criarDisciplina(tipoDisciplina);

            System.out.print("Nome da disciplina: ");
            String nome = in.nextLine();
            novaDisciplina.setNome(nome);

            System.out.print("Valor da disciplina: ");
            double valor = in.nextDouble();
            in.nextLine();
            novaDisciplina.setValor(valor);

            disciplinas.add(novaDisciplina);
            System.out.println("Disciplina criada com sucesso! ID: " + novaDisciplina.getId());

        } catch (Exception e) {
            System.out.println("Erro ao criar disciplina: " + e.getMessage());
        }
    }

    private void listarDisciplinas() {
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
        } else {
            System.out.println("\n=== DISCIPLINAS CADASTRADAS ===");
            for (Disciplina d : disciplinas) {
                System.out.print("ID: " + d.getId() +
                        " | Nome: " + d.getNome() +
                        " | Tipo: " + d.getTipo() +
                        " | Status: " + d.getStatus() +
                        " | Matrículas: " + d.getAlunos().size() +
                        " | Valor: R$ " + String.format("%.2f", d.getValor()));

                if (d.getProfessor() != null) {
                    System.out.print(" | Professor: " + d.getProfessor().getNome());
                }

                System.out.println();
            }
        }
    }

    private void editarDisciplina() {
        listarDisciplinas();
        if (disciplinas.isEmpty())
            return;

        System.out.print("Digite o ID da disciplina a editar: ");
        int id = in.nextInt();
        in.nextLine();

        Disciplina disciplina = encontrarDisciplinaPorId(id);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada!");
            return;
        }

        System.out.print("Novo nome (atual: " + disciplina.getNome() + "): ");
        String novoNome = in.nextLine();
        if (!novoNome.trim().isEmpty()) {
            disciplina.setNome(novoNome);
        }

        System.out.print("Novo valor (atual: " + String.format("%.2f", disciplina.getValor()) + "): ");
        Double novoValor = in.nextDouble();

        if (novoValor > 0) {
            try {
                disciplina.setValor(novoValor);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, mantendo o anterior.");
            }
        }

        System.out.println("Disciplina atualizada com sucesso!");
    }

    private void toggleStatusDisciplina() {
        listarDisciplinas();
        if (disciplinas.isEmpty())
            return;

        System.out.print("Digite o ID da disciplina para alterar status: ");
        int id = in.nextInt();
        in.nextLine();

        Disciplina disciplina = encontrarDisciplinaPorId(id);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada!");
            return;
        }

        disciplina.toggleStatus();
        System.out.println("Status da disciplina alterado para: " + disciplina.getStatus());
    }

    private void atribuirProfessorDisciplina() {
        listarDisciplinas();
        if (disciplinas.isEmpty())
            return;

        System.out.print("Digite o ID da disciplina: ");
        int idDisciplina = in.nextInt();
        in.nextLine();

        Disciplina disciplina = encontrarDisciplinaPorId(idDisciplina);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada!");
            return;
        }

        listarProfessores();
        if (professores.isEmpty())
            return;

        System.out.print("Digite o código do professor: ");
        int codProfessor = in.nextInt();
        in.nextLine();

        Professor professor = encontrarProfessorPorCodigo(codProfessor);
        if (professor == null) {
            System.out.println("Professor não encontrado!");
            return;
        }

        disciplina.setProfessor(professor);
        System.out.println("Professor atribuído à disciplina com sucesso!");
    }

    private void criarCurso() {
        System.out.print("Nome do curso: ");
        String nome = in.nextLine();

        System.out.print("Número de créditos: ");
        int creditos = in.nextInt();
        in.nextLine();

        try {
            Curso novoCurso = secretaria.criarCurso(nome, creditos);
            cursos.add(novoCurso);
            System.out.println("Curso criado com sucesso! ID: " + novoCurso.getId());
        } catch (Exception e) {
            System.out.println("Erro ao criar curso: " + e.getMessage());
        }
    }

    private void listarCursos() {
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
        } else {
            System.out.println("\n=== CURSOS CADASTRADOS ===");
            for (Curso c : cursos) {
                System.out.println("ID: " + c.getId() +
                        " | Nome: " + c.getNome() +
                        " | Créditos: " + c.getNumCreditos());
            }
        }
    }

    private void editarCurso() {
        listarCursos();
        if (cursos.isEmpty())
            return;

        System.out.print("Digite o ID do curso a editar: ");
        int id = in.nextInt();
        in.nextLine();

        Curso curso = encontrarCursoPorId(id);
        if (curso == null) {
            System.out.println("Curso não encontrado!");
            return;
        }

        System.out.print("Novo nome (atual: " + curso.getNome() + "): ");
        String novoNome = in.nextLine();
        if (!novoNome.trim().isEmpty()) {
            curso.setNome(novoNome);
        }

        System.out.print("Novo número de créditos (atual: " + curso.getNumCreditos() + "): ");
        String novoCreditosStr = in.nextLine();
        if (!novoCreditosStr.trim().isEmpty()) {
            try {
                int novoCreditos = Integer.parseInt(novoCreditosStr);
                curso.setNumCreditos(novoCreditos);
            } catch (NumberFormatException e) {
                System.out.println("Número inválido, mantendo o anterior.");
            }
        }

        System.out.println("Curso atualizado com sucesso!");
    }

    private void criarCurriculo() {
        listarCursos();
        if (cursos.isEmpty()) {
            System.out.println("É necessário ter cursos cadastrados para criar um currículo.");
            return;
        }

        System.out.print("Digite o ID do curso para o currículo: ");
        int idCurso = in.nextInt();
        in.nextLine();

        Curso curso = encontrarCursoPorId(idCurso);
        if (curso == null) {
            System.out.println("Curso não encontrado!");
            return;
        }

        try {
            Curriculo novoCurriculo = secretaria.criarCurriculo(curso);
            curriculos.add(novoCurriculo);
            System.out.println("Currículo criado com sucesso! ID: " + novoCurriculo.getId());
        } catch (Exception e) {
            System.out.println("Erro ao criar currículo: " + e.getMessage());
        }
    }

    private void listarCurriculos() {
        if (curriculos.isEmpty()) {
            System.out.println("Nenhum currículo cadastrado.");
        } else {
            System.out.println("\n=== CURRÍCULOS CADASTRADOS ===");
            for (Curriculo c : curriculos) {
                System.out.println("ID: " + c.getId() +
                        " | Curso: " + c.getCurso().getNome() +
                        " | Disciplinas: " + c.getDisciplinas().size());
            }
        }
    }

    private void adicionarDisciplinaCurriculo() {
        listarCurriculos();
        if (curriculos.isEmpty())
            return;

        System.out.print("Digite o ID do currículo: ");
        int idCurriculo = in.nextInt();
        in.nextLine();

        Curriculo curriculo = encontrarCurriculoPorId(idCurriculo);
        if (curriculo == null) {
            System.out.println("Currículo não encontrado!");
            return;
        }

        listarDisciplinas();
        if (disciplinas.isEmpty())
            return;

        System.out.print("Digite o ID da disciplina a adicionar: ");
        int idDisciplina = in.nextInt();
        in.nextLine();

        Disciplina disciplina = encontrarDisciplinaPorId(idDisciplina);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada!");
            return;
        }

        if (curriculo.addDisciplina(disciplina.getNome())) {
            System.out.println("Disciplina adicionada ao currículo com sucesso!");
        } else {
            System.out.println("Não foi possível adicionar a disciplina ao currículo.");
        }
    }

    private void removerDisciplinaCurriculo() {
        listarCurriculos();
        if (curriculos.isEmpty())
            return;

        System.out.print("Digite o ID do currículo: ");
        int idCurriculo = in.nextInt();
        in.nextLine();

        Curriculo curriculo = encontrarCurriculoPorId(idCurriculo);
        if (curriculo == null) {
            System.out.println("Currículo não encontrado!");
            return;
        }

        ArrayList<String> disciplinasCurriculo = curriculo.getDisciplinas();
        if (disciplinasCurriculo.isEmpty()) {
            System.out.println("Não há disciplinas neste currículo.");
            return;
        }

        System.out.println("Disciplinas no currículo:");
        for (int i = 0; i < disciplinasCurriculo.size(); i++) {
            System.out.println((i + 1) + " - " + disciplinasCurriculo.get(i));
        }

        System.out.print("Digite o número da disciplina a remover: ");
        int numeroDisciplina = in.nextInt();
        in.nextLine();

        if (numeroDisciplina > 0 && numeroDisciplina <= disciplinasCurriculo.size()) {
            String nomeDisciplina = disciplinasCurriculo.get(numeroDisciplina - 1);
            if (curriculo.removerDisciplina(nomeDisciplina) != null) {
                System.out.println("Disciplina removida do currículo com sucesso!");
            } else {
                System.out.println("Erro ao remover disciplina!");
            }
        } else {
            System.out.println("Número inválido!");
        }
    }

    private void cadastrarAluno() {
        System.out.print("Nome do aluno: ");
        String nome = in.nextLine();

        System.out.print("Email do aluno: ");
        String email = in.nextLine();

        System.out.print("Senha do aluno: ");
        String senha = in.nextLine();

        try {
            Aluno novoAluno = secretaria.cadastrarAluno(nome, email, senha);
            alunos.add(novoAluno);
            System.out.println("Aluno cadastrado com sucesso!");
            System.out.println("Código: " + novoAluno.getCodPessoa() + " | Matrícula: " + novoAluno.getMatricula());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    private void cadastrarProfessor() {
        System.out.print("Nome do professor: ");
        String nome = in.nextLine();

        System.out.print("Email do professor: ");
        String email = in.nextLine();

        System.out.print("Senha do professor: ");
        String senha = in.nextLine();

        try {
            Professor novoProfessor = secretaria.cadastrarProfessor(nome, email, senha);
            professores.add(novoProfessor);
            System.out.println("Professor cadastrado com sucesso!");
            System.out.println("Código: " + novoProfessor.getCodPessoa());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
        }
    }

    private void cadastrarSecretaria() {
        System.out.print("Nome da secretária: ");
        String nome = in.nextLine();

        System.out.print("Email da secretária: ");
        String email = in.nextLine();

        System.out.print("Senha da secretária: ");
        String senha = in.nextLine();

        try {
            Secretaria novaSecretaria = secretaria.cadastrarSecretaria(nome, email, senha);
            secretarias.add(novaSecretaria);
            System.out.println("Secretária cadastrada com sucesso!");
            System.out.println("Código: " + novaSecretaria.getCodPessoa());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar secretária: " + e.getMessage());
        }
    }

    private void listarAlunos() {
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            System.out.println("\n=== ALUNOS CADASTRADOS ===");
            for (Aluno a : alunos) {
                System.out.println("Código: " + a.getCodPessoa() +
                        " | Matrícula: " + a.getMatricula() +
                        " | Nome: " + a.getNome() +
                        " | Email: " + a.getEmail() +
                        " | Ativo: " + (a.isAtivo() ? "Sim" : "Não"));
            }
        }
    }

    private void listarProfessores() {
        if (professores.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
        } else {
            System.out.println("\n=== PROFESSORES CADASTRADOS ===");
            for (Professor p : professores) {
                System.out.println("Código: " + p.getCodPessoa() +
                        " | Nome: " + p.getNome() +
                        " | Email: " + p.getEmail() +
                        " | Ativo: " + (p.isAtivo() ? "Sim" : "Não"));
            }
        }
    }

    private void listarSecretarias() {
        if (secretarias.isEmpty()) {
            System.out.println("Nenhuma secretária cadastrada.");
        } else {
            System.out.println("\n=== SECRETÁRIAS CADASTRADAS ===");
            for (Secretaria s : secretarias) {
                System.out.println("Código: " + s.getCodPessoa() +
                        " | Nome: " + s.getNome() +
                        " | Email: " + s.getEmail() +
                        " | Ativo: " + (s.isAtivo() ? "Sim" : "Não"));
            }
        }
    }

    private void editarAluno() {
        listarAlunos();
        if (alunos.isEmpty())
            return;

        System.out.print("Digite o código do aluno a editar: ");
        int codigo = in.nextInt();
        in.nextLine();

        Aluno aluno = encontrarAlunoPorCodigo(codigo);
        if (aluno == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }

        System.out.print("Novo nome (atual: " + aluno.getNome() + "): ");
        String novoNome = in.nextLine();
        if (!novoNome.trim().isEmpty()) {
            aluno.setNome(novoNome);
        }

        System.out.print("Novo email (atual: " + aluno.getEmail() + "): ");
        String novoEmail = in.nextLine();
        if (!novoEmail.trim().isEmpty()) {
            aluno.setEmail(novoEmail);
        }

        System.out.print("Nova senha (atual: " + aluno.getSenha() + "): ");
        String novaSenha = in.nextLine();
        if (!novaSenha.trim().isEmpty()) {
            aluno.setSenha(novaSenha);
        }

        System.out.print("Ativo? (atual: " + aluno.isAtivo() + ") [true/false]: ");
        String ativoStr = in.nextLine();
        if (!ativoStr.trim().isEmpty()) {
            aluno.setAtivo(Boolean.parseBoolean(ativoStr.trim()));
        }

        System.out.println("Aluno atualizado com sucesso!");
    }

    private void editarProfessor() {
        listarProfessores();
        if (professores.isEmpty())
            return;

        System.out.print("Digite o código do professor a editar: ");
        int codigo = in.nextInt();
        in.nextLine();

        Professor professor = encontrarProfessorPorCodigo(codigo);
        if (professor == null) {
            System.out.println("Professor não encontrado!");
            return;
        }

        System.out.print("Novo nome (atual: " + professor.getNome() + "): ");
        String novoNome = in.nextLine();
        if (!novoNome.trim().isEmpty()) {
            professor.setNome(novoNome);
        }

        System.out.print("Novo email (atual: " + professor.getEmail() + "): ");
        String novoEmail = in.nextLine();
        if (!novoEmail.trim().isEmpty()) {
            professor.setEmail(novoEmail);
        }

        System.out.print("Nova senha (atual: " + professor.getSenha() + "): ");
        String novaSenha = in.nextLine();
        if (!novaSenha.trim().isEmpty()) {
            professor.setSenha(novaSenha);
        }

        System.out.print("Ativo? (atual: " + professor.isAtivo() + ") [true/false]: ");
        String ativoStr = in.nextLine();
        if (!ativoStr.trim().isEmpty()) {
            professor.setAtivo(Boolean.parseBoolean(ativoStr.trim()));
        }

        System.out.println("Professor atualizado com sucesso!");
    }

    private void editarSecretaria() {
        listarSecretarias();
        if (secretarias.isEmpty())
            return;

        System.out.print("Digite o código da secretaria a editar: ");
        int codigo = in.nextInt();
        in.nextLine();

        Secretaria secretaria = encontrarSecretariaPorCodigo(codigo);
        if (secretaria == null) {
            System.out.println("Secretaria não encontrada!");
            return;
        }

        System.out.print("Novo nome (atual: " + secretaria.getNome() + "): ");
        String novoNome = in.nextLine();
        if (!novoNome.trim().isEmpty()) {
            secretaria.setNome(novoNome);
        }

        System.out.print("Novo email (atual: " + secretaria.getEmail() + "): ");
        String novoEmail = in.nextLine();
        if (!novoEmail.trim().isEmpty()) {
            secretaria.setEmail(novoEmail);
        }

        System.out.print("Nova senha (atual: " + secretaria.getSenha() + "): ");
        String novaSenha = in.nextLine();
        if (!novaSenha.trim().isEmpty()) {
            secretaria.setSenha(novaSenha);
        }

        System.out.print("Ativo? (atual: " + secretaria.isAtivo() + ") [true/false]: ");
        String ativoStr = in.nextLine();
        if (!ativoStr.trim().isEmpty()) {
            secretaria.setAtivo(Boolean.parseBoolean(ativoStr.trim()));
        }

        System.out.println("Secretaria atualizada com sucesso!");
    }

    private void atribuirCurriculoAluno() {
        listarAlunos();
        if (alunos.isEmpty())
            return;

        System.out.print("Digite o código do aluno: ");
        int codAluno = in.nextInt();
        in.nextLine();

        Aluno aluno = encontrarAlunoPorCodigo(codAluno);
        if (aluno == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }

        listarCurriculos();
        if (curriculos.isEmpty())
            return;

        System.out.print("Digite o ID do currículo: ");
        int idCurriculo = in.nextInt();
        in.nextLine();

        Curriculo curriculo = encontrarCurriculoPorId(idCurriculo);
        if (curriculo == null) {
            System.out.println("Currículo não encontrado!");
            return;
        }

        aluno.setCurriculo(curriculo);
        System.out.println("Currículo atribuído ao aluno com sucesso!");
    }

    private void consolidarMatriculas() {
        System.out.println("Consolidando matrículas...");

        try {
            secretaria.consolidarMatriculas(disciplinas, alunos);
            System.out.println("Matrículas consolidadas com sucesso!");
            System.out.println("Disciplinas com menos de 3 alunos foram canceladas.");
        } catch (Exception e) {
            System.out.println("Erro ao consolidar matrículas: " + e.getMessage());
        }
    }

    private void relatorioMatriculasPorDisciplina() {
        System.out.println("\n=== RELATÓRIO DE MATRÍCULAS POR DISCIPLINA ===");
        for (Disciplina d : disciplinas) {
            System.out.println("Disciplina: " + d.getNome() + " (ID: " + d.getId() + ")");
            System.out.println("Status: " + d.getStatus());
            System.out.println("Alunos matriculados: " + d.getAlunos().size());
            if (!d.getAlunos().isEmpty()) {
                for (Aluno aluno : d.getAlunos()) {
                    System.out.println("  - " + aluno.getNome() + " (Matrícula: " + aluno.getMatricula() + ")");
                }
            }
            System.out.println("---");
        }
    }

    private void relatorioDisciplinasPorStatus() {
        System.out.println("\n=== RELATÓRIO DE DISCIPLINAS POR STATUS ===");
        long previstas = disciplinas.stream().filter(d -> d.getStatus().toString().equals("PREVISTA")).count();
        long ativas = disciplinas.stream().filter(d -> d.getStatus().toString().equals("ATIVA")).count();
        long canceladas = disciplinas.stream().filter(d -> d.getStatus().toString().equals("CANCELADA")).count();

        System.out.println("Disciplinas Ativas: " + ativas);
        System.out.println("Disciplinas Canceladas: " + canceladas);
        System.out.println("Disciplinas Previstas: " + previstas);
        System.out.println("Total de Disciplinas: " + disciplinas.size());
    }

    private void relatorioAlunosPorCurso() {
        System.out.println("\n=== RELATÓRIO DE ALUNOS POR CURSO ===");
        for (Curso curso : cursos) {
            long alunosNoCurso = alunos.stream()
                    .filter(a -> a.getCurriculo() != null && a.getCurriculo().getCurso().getId() == curso.getId())
                    .count();
            System.out.println("Curso: " + curso.getNome() + " - Alunos: " + alunosNoCurso);
        }
    }

    private void relatorioFinanceiro() {
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        double totalReceita = 0;

        for (Aluno aluno : alunos) {
            if (!aluno.getPlanosDeEnsino().isEmpty()) {
                for (var plano : aluno.getPlanosDeEnsino()) {
                    if (plano.getStatus().toString().equals("EFETIVADO")) {
                        totalReceita += plano.getValorTotal();
                    }
                }
            }
        }

        System.out.println("Receita Total de Matrículas: R$ " + String.format("%.2f", totalReceita));
        System.out.println("Número de Alunos: " + alunos.size());
        System.out.println(
                "Média por Aluno: R$ " + String.format("%.2f", alunos.size() > 0 ? totalReceita / alunos.size() : 0));
    }

    private Disciplina encontrarDisciplinaPorId(int id) {
        return disciplinas.stream().filter(d -> d.getId() == id).findFirst().orElse(null);
    }

    private Curso encontrarCursoPorId(int id) {
        return cursos.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    private Curriculo encontrarCurriculoPorId(int id) {
        return curriculos.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    private Aluno encontrarAlunoPorCodigo(int codigo) {
        return alunos.stream().filter(a -> a.getCodPessoa() == codigo).findFirst().orElse(null);
    }

    private Secretaria encontrarSecretariaPorCodigo(int codigo) {
        return secretarias.stream().filter(s -> s.getCodPessoa() == codigo).findFirst().orElse(null);
    }

    private Professor encontrarProfessorPorCodigo(int codigo) {
        return professores.stream().filter(p -> p.getCodPessoa() == codigo).findFirst().orElse(null);
    }
}