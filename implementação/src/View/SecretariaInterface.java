package View;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import Business.Curriculo;
import Business.Curso;
import Business.Disciplina;
import Business.Pessoa.Aluno;
import Business.Pessoa.Professor;
import Business.Pessoa.Secretaria;
import Business.Pessoa.Usuario;
import Data.DAO.ProfessorDAO;
import Enum.StatusDisciplina;
import Enum.TipoAcesso;
import Enum.TipoDisciplina;

public class SecretariaInterface {
    private Scanner in;
    private Secretaria secretaria;

    public SecretariaInterface(Scanner scanner, Secretaria secretaria) {
        this.in = scanner;
        this.secretaria = secretaria;
    }

    // ==================== MENU PRINCIPAL SECRETÁRIA ====================

    public void menuSecretaria() {
        String header = "Menu da Secretária";
        String[] opcoes = {
                "1 - Gerenciar Usuários",
                "2 - Gerenciar Disciplinas", 
                "3 - Gerenciar Cursos",
                "4 - Gerenciar Currículos",
                "0 - Voltar ao menu principal"
        };
        
        int opcao;
        do {
            imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 -> menuUsuarios();
                case 2 -> menuDisciplinas();
                case 3 -> menuCursos();
                case 4 -> menuCurriculos();
                case 0 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // ==================== MENU USUÁRIOS ====================

    private void menuUsuarios() {
        String header = "Gerenciar Usuários";
        String[] opcoes = {
                "1 - Cadastrar Usuário",
                "2 - Listar Usuários",
                "3 - Buscar Usuário por Email",
                "4 - Editar Usuário",
                "5 - Deletar Usuário",
                "0 - Voltar"
        };
        
        int opcao;
        do {
            imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> listarUsuarios();
                case 3 -> buscarUsuarioPorEmail();
                case 4 -> editarUsuario();
                case 5 -> deletarUsuario();
                case 0 -> System.out.println("Retornando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarUsuario() {
        System.out.println("\n=== CADASTRAR USUÁRIO ===");
        
        System.out.print("Nome: ");
        String nome = in.nextLine();
        
        System.out.print("Email: ");
        String email = in.nextLine();
        
        System.out.print("Senha: ");
        String senha = in.nextLine();
        
        System.out.println("Tipo de usuário:");
        System.out.println("1 - Aluno");
        System.out.println("2 - Professor");
        System.out.println("3 - Secretária");
        System.out.print("Escolha: ");
        int tipo = in.nextInt();
        in.nextLine();
        
        TipoAcesso tipoAcesso = switch (tipo) {
            case 1 -> TipoAcesso.ALUNO;
            case 2 -> TipoAcesso.PROFESSOR;
            case 3 -> TipoAcesso.SECRETARIA;
            default -> null;
        };
        
        if (tipoAcesso != null) {
            try {
                Usuario usuario = secretaria.cadastrarUsuario(nome, email, senha, tipoAcesso);
                System.out.println("Usuário cadastrado com sucesso!");
                System.out.println(usuario.toString());
            } catch (Exception e) {
                System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            }
        } else {
            System.out.println("Tipo de usuário inválido!");
        }
    }

    private void listarUsuarios() {
        System.out.println("\n=== LISTAR USUÁRIOS ===");
        try {
            List<Usuario> usuarios = secretaria.listarTodosUsuarios();
            
            if (usuarios.isEmpty()) {
                System.out.println("Nenhum usuário cadastrado.");
            } else {
                System.out.println("Total de usuários: " + usuarios.size());
                System.out.println("========================");
                for (Usuario usuario : usuarios) {
                    System.out.println(usuario.toString());
                    System.out.println("========================");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
    }

    private void buscarUsuarioPorEmail() {
        System.out.println("\n=== BUSCAR USUÁRIO POR EMAIL ===");
        System.out.print("Email: ");
        String email = in.nextLine();
        
        try {
            Usuario usuario = secretaria.buscarUsuarioPorEmail(email);
            
            if (usuario != null) {
                System.out.println("Usuário encontrado:");
                System.out.println(usuario.toString());
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    private void editarUsuario() {
        System.out.println("\n=== EDITAR USUÁRIO ===");
        System.out.print("Email do usuário a ser editado: ");
        String email = in.nextLine();
        
        System.out.println("Deixe em branco para não alterar:");
        System.out.print("Novo nome: ");
        String novoNome = in.nextLine();
        if (novoNome.trim().isEmpty()) novoNome = null;
        
        System.out.print("Novo email: ");
        String novoEmail = in.nextLine();
        if (novoEmail.trim().isEmpty()) novoEmail = null;
        
        System.out.print("Nova senha: ");
        String novaSenha = in.nextLine();
        if (novaSenha.trim().isEmpty()) novaSenha = null;
        
        System.out.print("Status ativo (true/false): ");
        String statusStr = in.nextLine();
        Boolean ativo = null;
        if (!statusStr.trim().isEmpty()) {
            ativo = Boolean.parseBoolean(statusStr);
        }
        
        try {
            boolean sucesso = secretaria.editarUsuarioPorEmail(email, novoNome, novoEmail, novaSenha, ativo);
            
            if (sucesso) {
                System.out.println("Usuário editado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao editar usuário: " + e.getMessage());
        }
    }

    private void deletarUsuario() {
        System.out.println("\n=== DELETAR USUÁRIO ===");
        System.out.print("Email do usuário a ser deletado: ");
        String email = in.nextLine();
        
        System.out.print("Tem certeza? (s/n): ");
        String confirmacao = in.nextLine();
        
        if (confirmacao.toLowerCase().equals("s")) {
            try {
                boolean sucesso = secretaria.deletarUsuarioPorEmail(email);
                
                if (sucesso) {
                    System.out.println("Usuário deletado com sucesso!");
                } else {
                    System.out.println("Usuário não encontrado.");
                }
            } catch (IOException e) {
                System.out.println("Erro ao deletar usuário: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    // ==================== MENU DISCIPLINAS ====================

    private void menuDisciplinas() {
        String header = "Gerenciar Disciplinas";
        String[] opcoes = {
                "1 - Cadastrar Disciplina",
                "2 - Listar Disciplinas",
                "3 - Buscar Disciplina por ID",
                "4 - Buscar Disciplinas por Nome",
                "5 - Buscar Disciplinas por Tipo",
                "6 - Buscar Disciplinas por Status",
                "7 - Editar Disciplina",
                "8 - Deletar Disciplina",
                "0 - Voltar"
        };
        
        int opcao;
        do {
            imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 -> cadastrarDisciplina();
                case 2 -> listarDisciplinas();
                case 3 -> buscarDisciplinaPorId();
                case 4 -> buscarDisciplinasPorNome();
                case 5 -> buscarDisciplinasPorTipo();
                case 6 -> buscarDisciplinasPorStatus();
                case 7 -> editarDisciplina();
                case 8 -> deletarDisciplina();
                case 0 -> System.out.println("Retornando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarDisciplina() {
        System.out.println("\n=== CADASTRAR DISCIPLINA ===");
        
        System.out.print("Nome da disciplina: ");
        String nome = in.nextLine();
        
        System.out.print("Valor da disciplina: ");
        double valor = in.nextDouble();
        in.nextLine();
        
        System.out.println("Tipo da disciplina:");
        System.out.println("1 - Obrigatória");
        System.out.println("2 - Optativa");
        System.out.print("Escolha: ");
        int tipo = in.nextInt();
        in.nextLine();
        
        TipoDisciplina tipoDisciplina = switch (tipo) {
            case 1 -> TipoDisciplina.OBRIGATORIA;
            case 2 -> TipoDisciplina.OPTATIVA;
            default -> null;
        };
        
        System.out.print("Matrícula do professor: ");
        int matriculaProfessor = in.nextInt();
        in.nextLine();
        
        try {
            // Buscar professor por matrícula
            List<Professor> professores = ProfessorDAO.getDAO().carregar();
            Professor professor = professores.stream()
                    .filter(p -> p.getMatricula() == matriculaProfessor)
                    .findFirst()
                    .orElse(null);
            
            if (professor != null && tipoDisciplina != null) {
                Disciplina disciplina = secretaria.cadastrarDisciplina(nome, valor, tipoDisciplina, professor);
                System.out.println("Disciplina cadastrada com sucesso!");
                System.out.println("ID: " + disciplina.getId());
            } else {
                System.out.println("Professor não encontrado ou tipo inválido!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar disciplina: " + e.getMessage());
        }
    }

    private void listarDisciplinas() {
        System.out.println("\n=== LISTAR DISCIPLINAS ===");
        try {
            List<Disciplina> disciplinas = secretaria.listarTodasDisciplinas();
            
            if (disciplinas.isEmpty()) {
                System.out.println("Nenhuma disciplina cadastrada.");
            } else {
                System.out.println("Total de disciplinas: " + disciplinas.size());
                System.out.println("========================");
                for (Disciplina disciplina : disciplinas) {
                    System.out.println(disciplina.toString());
                    System.out.println("========================");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao listar disciplinas: " + e.getMessage());
        }
    }

    private void buscarDisciplinaPorId() {
        System.out.println("\n=== BUSCAR DISCIPLINA POR ID ===");
        System.out.print("ID da disciplina: ");
        int id = in.nextInt();
        in.nextLine();
        
        try {
            Disciplina disciplina = secretaria.buscarDisciplinaPorId(id);
            
            if (disciplina != null) {
                System.out.println("Disciplina encontrada:");
                System.out.println(disciplina.toString());
            } else {
                System.out.println("Disciplina não encontrada.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar disciplina: " + e.getMessage());
        }
    }

    private void buscarDisciplinasPorNome() {
        System.out.println("\n=== BUSCAR DISCIPLINAS POR NOME ===");
        System.out.print("Nome (busca parcial): ");
        String nome = in.nextLine();
        
        try {
            List<Disciplina> disciplinas = secretaria.buscarDisciplinasPorNome(nome);
            
            if (disciplinas.isEmpty()) {
                System.out.println("Nenhuma disciplina encontrada.");
            } else {
                System.out.println("Disciplinas encontradas: " + disciplinas.size());
                for (Disciplina disciplina : disciplinas) {
                    System.out.println(disciplina.toString());
                    System.out.println("========================");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar disciplinas: " + e.getMessage());
        }
    }

    private void buscarDisciplinasPorTipo() {
        System.out.println("\n=== BUSCAR DISCIPLINAS POR TIPO ===");
        System.out.println("1 - Obrigatória");
        System.out.println("2 - Optativa");
        System.out.print("Escolha: ");
        int tipo = in.nextInt();
        in.nextLine();
        
        TipoDisciplina tipoDisciplina = switch (tipo) {
            case 1 -> TipoDisciplina.OBRIGATORIA;
            case 2 -> TipoDisciplina.OPTATIVA;
            default -> null;
        };
        
        if (tipoDisciplina != null) {
            try {
                List<Disciplina> disciplinas = secretaria.buscarDisciplinasPorTipo(tipoDisciplina);
                
                if (disciplinas.isEmpty()) {
                    System.out.println("Nenhuma disciplina encontrada.");
                } else {
                    System.out.println("Disciplinas encontradas: " + disciplinas.size());
                    for (Disciplina disciplina : disciplinas) {
                        System.out.println(disciplina.toString());
                        System.out.println("========================");
                    }
                }
            } catch (IOException e) {
                System.out.println("Erro ao buscar disciplinas: " + e.getMessage());
            }
        } else {
            System.out.println("Tipo inválido!");
        }
    }

    private void buscarDisciplinasPorStatus() {
        System.out.println("\n=== BUSCAR DISCIPLINAS POR STATUS ===");
        System.out.println("1 - Prevista");
        System.out.println("2 - Ativa");
        System.out.println("3 - Cancelada");
        System.out.print("Escolha: ");
        int status = in.nextInt();
        in.nextLine();
        
        StatusDisciplina statusDisciplina = switch (status) {
            case 1 -> StatusDisciplina.PREVISTA;
            case 2 -> StatusDisciplina.ATIVA;
            case 3 -> StatusDisciplina.CANCELADA;
            default -> null;
        };
        
        if (statusDisciplina != null) {
            try {
                List<Disciplina> disciplinas = secretaria.buscarDisciplinasPorStatus(statusDisciplina);
                
                if (disciplinas.isEmpty()) {
                    System.out.println("Nenhuma disciplina encontrada.");
                } else {
                    System.out.println("Disciplinas encontradas: " + disciplinas.size());
                    for (Disciplina disciplina : disciplinas) {
                        System.out.println(disciplina.toString());
                        System.out.println("========================");
                    }
                }
            } catch (IOException e) {
                System.out.println("Erro ao buscar disciplinas: " + e.getMessage());
            }
        } else {
            System.out.println("Status inválido!");
        }
    }

    private void editarDisciplina() {
        System.out.println("\n=== EDITAR DISCIPLINA ===");
        System.out.print("ID da disciplina: ");
        int id = in.nextInt();
        in.nextLine();
        
        System.out.println("Deixe em branco para não alterar:");
        System.out.print("Novo nome: ");
        String novoNome = in.nextLine();
        if (novoNome.trim().isEmpty()) novoNome = null;
        
        System.out.print("Novo valor: ");
        String valorStr = in.nextLine();
        Double novoValor = null;
        if (!valorStr.trim().isEmpty()) {
            novoValor = Double.parseDouble(valorStr);
        }
        
        System.out.println("Novo tipo (1-Obrigatória, 2-Optativa): ");
        String tipoStr = in.nextLine();
        TipoDisciplina novoTipo = null;
        if (!tipoStr.trim().isEmpty()) {
            int tipo = Integer.parseInt(tipoStr);
            novoTipo = switch (tipo) {
                case 1 -> TipoDisciplina.OBRIGATORIA;
                case 2 -> TipoDisciplina.OPTATIVA;
                default -> null;
            };
        }
        
        System.out.println("Novo status (1-Prevista, 2-Ativa, 3-Cancelada): ");
        String statusStr = in.nextLine();
        StatusDisciplina novoStatus = null;
        if (!statusStr.trim().isEmpty()) {
            int status = Integer.parseInt(statusStr);
            novoStatus = switch (status) {
                case 1 -> StatusDisciplina.PREVISTA;
                case 2 -> StatusDisciplina.ATIVA;
                case 3 -> StatusDisciplina.CANCELADA;
                default -> null;
            };
        }
        
        try {
            boolean sucesso = secretaria.editarDisciplinaPorId(id, novoNome, novoValor, novoTipo, novoStatus, null);
            
            if (sucesso) {
                System.out.println("Disciplina editada com sucesso!");
            } else {
                System.out.println("Disciplina não encontrada.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao editar disciplina: " + e.getMessage());
        }
    }

    private void deletarDisciplina() {
        System.out.println("\n=== DELETAR DISCIPLINA ===");
        System.out.print("ID da disciplina: ");
        int id = in.nextInt();
        in.nextLine();
        
        System.out.print("Tem certeza? (s/n): ");
        String confirmacao = in.nextLine();
        
        if (confirmacao.toLowerCase().equals("s")) {
            try {
                boolean sucesso = secretaria.deletarDisciplinaPorId(id);
                
                if (sucesso) {
                    System.out.println("Disciplina deletada com sucesso!");
                } else {
                    System.out.println("Disciplina não encontrada.");
                }
            } catch (IOException e) {
                System.out.println("Erro ao deletar disciplina: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    // ==================== MENU CURSOS ====================

    private void menuCursos() {
        String header = "Gerenciar Cursos";
        String[] opcoes = {
                "1 - Cadastrar Curso",
                "2 - Listar Cursos",
                "3 - Buscar Curso por ID",
                "4 - Buscar Cursos por Nome",
                "5 - Buscar Cursos por Departamento",
                "6 - Buscar Cursos por Créditos",
                "7 - Editar Curso",
                "8 - Deletar Curso",
                "0 - Voltar"
        };
        
        int opcao;
        do {
            imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 -> cadastrarCurso();
                case 2 -> listarCursos();
                case 3 -> buscarCursoPorId();
                case 4 -> buscarCursosPorNome();
                case 5 -> buscarCursosPorDepartamento();
                case 6 -> buscarCursosPorCreditos();
                case 7 -> editarCurso();
                case 8 -> deletarCurso();
                case 0 -> System.out.println("Retornando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarCurso() {
        System.out.println("\n=== CADASTRAR CURSO ===");
        
        System.out.print("Nome do curso: ");
        String nome = in.nextLine();
        
        System.out.print("Número de créditos: ");
        int creditos = in.nextInt();
        in.nextLine();
        
        System.out.print("Departamento (opcional): ");
        String departamento = in.nextLine();
        if (departamento.trim().isEmpty()) departamento = null;
        
        try {
            Curso curso = secretaria.cadastrarCurso(nome, creditos, departamento);
            System.out.println("Curso cadastrado com sucesso!");
            System.out.println("ID: " + curso.getId());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar curso: " + e.getMessage());
        }
    }

    private void listarCursos() {
        System.out.println("\n=== LISTAR CURSOS ===");
        try {
            List<Curso> cursos = secretaria.listarTodosCursos();
            
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso cadastrado.");
            } else {
                System.out.println("Total de cursos: " + cursos.size());
                System.out.println("========================");
                for (Curso curso : cursos) {
                    System.out.println(curso.toString());
                    System.out.println("========================");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao listar cursos: " + e.getMessage());
        }
    }

    private void buscarCursoPorId() {
        System.out.println("\n=== BUSCAR CURSO POR ID ===");
        System.out.print("ID do curso: ");
        int id = in.nextInt();
        in.nextLine();
        
        try {
            Curso curso = secretaria.buscarCursoPorId(id);
            
            if (curso != null) {
                System.out.println("Curso encontrado:");
                System.out.println(curso.toString());
            } else {
                System.out.println("Curso não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar curso: " + e.getMessage());
        }
    }

    private void buscarCursosPorNome() {
        System.out.println("\n=== BUSCAR CURSOS POR NOME ===");
        System.out.print("Nome (busca parcial): ");
        String nome = in.nextLine();
        
        try {
            List<Curso> cursos = secretaria.buscarCursosPorNome(nome);
            
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso encontrado.");
            } else {
                System.out.println("Cursos encontrados: " + cursos.size());
                for (Curso curso : cursos) {
                    System.out.println(curso.toString());
                    System.out.println("========================");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar cursos: " + e.getMessage());
        }
    }

    private void buscarCursosPorDepartamento() {
        System.out.println("\n=== BUSCAR CURSOS POR DEPARTAMENTO ===");
        System.out.print("Departamento: ");
        String departamento = in.nextLine();
        
        try {
            List<Curso> cursos = secretaria.buscarCursosPorDepartamento(departamento);
            
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso encontrado.");
            } else {
                System.out.println("Cursos encontrados: " + cursos.size());
                for (Curso curso : cursos) {
                    System.out.println(curso.toString());
                    System.out.println("========================");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar cursos: " + e.getMessage());
        }
    }

    private void buscarCursosPorCreditos() {
        System.out.println("\n=== BUSCAR CURSOS POR CRÉDITOS ===");
        System.out.print("Créditos mínimos: ");
        int minCreditos = in.nextInt();
        System.out.print("Créditos máximos: ");
        int maxCreditos = in.nextInt();
        in.nextLine();
        
        try {
            List<Curso> cursos = secretaria.buscarCursosPorCreditos(minCreditos, maxCreditos);
            
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso encontrado.");
            } else {
                System.out.println("Cursos encontrados: " + cursos.size());
                for (Curso curso : cursos) {
                    System.out.println(curso.toString());
                    System.out.println("========================");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar cursos: " + e.getMessage());
        }
    }

    private void editarCurso() {
        System.out.println("\n=== EDITAR CURSO ===");
        System.out.print("ID do curso: ");
        int id = in.nextInt();
        in.nextLine();
        
        System.out.println("Deixe em branco para não alterar:");
        System.out.print("Novo nome: ");
        String novoNome = in.nextLine();
        if (novoNome.trim().isEmpty()) novoNome = null;
        
        System.out.print("Novo número de créditos: ");
        String creditosStr = in.nextLine();
        Integer novoCreditos = null;
        if (!creditosStr.trim().isEmpty()) {
            novoCreditos = Integer.parseInt(creditosStr);
        }
        
        System.out.print("Novo departamento: ");
        String novoDepartamento = in.nextLine();
        if (novoDepartamento.trim().isEmpty()) novoDepartamento = null;
        
        try {
            boolean sucesso = secretaria.editarCursoPorId(id, novoNome, novoCreditos, novoDepartamento);
            
            if (sucesso) {
                System.out.println("Curso editado com sucesso!");
            } else {
                System.out.println("Curso não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao editar curso: " + e.getMessage());
        }
    }

    private void deletarCurso() {
        System.out.println("\n=== DELETAR CURSO ===");
        System.out.print("ID do curso: ");
        int id = in.nextInt();
        in.nextLine();
        
        System.out.print("Tem certeza? (s/n): ");
        String confirmacao = in.nextLine();
        
        if (confirmacao.toLowerCase().equals("s")) {
            try {
                boolean sucesso = secretaria.deletarCursoPorId(id);
                
                if (sucesso) {
                    System.out.println("Curso deletado com sucesso!");
                } else {
                    System.out.println("Curso não encontrado.");
                }
            } catch (IOException e) {
                System.out.println("Erro ao deletar curso: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    // ==================== MENU CURRÍCULOS ====================

    private void menuCurriculos() {
        String header = "Gerenciar Currículos";
        String[] opcoes = {
                "1 - Cadastrar Currículo",
                "2 - Listar Currículos",
                "3 - Buscar Currículo por ID",
                "4 - Buscar Currículos por Curso",
                "5 - Buscar Currículos por Disciplina",
                "6 - Adicionar Disciplina ao Currículo",
                "7 - Remover Disciplina do Currículo",
                "8 - Editar Currículo",
                "9 - Deletar Currículo",
                "0 - Voltar"
        };
        
        int opcao;
        do {
            imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 -> cadastrarCurriculo();
                case 2 -> listarCurriculos();
                case 3 -> buscarCurriculoPorId();
                case 4 -> buscarCurriculosPorCurso();
                case 5 -> buscarCurriculosPorDisciplina();
                case 6 -> adicionarDisciplinaAoCurriculo();
                case 7 -> removerDisciplinaDoCurriculo();
                case 8 -> editarCurriculo();
                case 9 -> deletarCurriculo();
                case 0 -> System.out.println("Retornando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarCurriculo() {
        System.out.println("\n=== CADASTRAR CURRÍCULO ===");
        System.out.print("ID do curso: ");
        int idCurso = in.nextInt();
        in.nextLine();
        
        try {
            Curso curso = secretaria.buscarCursoPorId(idCurso);
            
            if (curso != null) {
                Curriculo curriculo = secretaria.cadastrarCurriculo(curso);
                System.out.println("Currículo cadastrado com sucesso!");
                System.out.println("ID: " + curriculo.getId());
            } else {
                System.out.println("Curso não encontrado!");
            }
        } catch (IOException e) {
            System.out.println("Erro ao cadastrar currículo: " + e.getMessage());
        }
    }

    private void listarCurriculos() {
        System.out.println("\n=== LISTAR CURRÍCULOS ===");
        try {
            List<Curriculo> curriculos = secretaria.listarTodosCurriculos();
            
            if (curriculos.isEmpty()) {
                System.out.println("Nenhum currículo cadastrado.");
            } else {
                System.out.println("Total de currículos: " + curriculos.size());
                System.out.println("========================");
                for (Curriculo curriculo : curriculos) {
                    System.out.println(curriculo.toString());
                    System.out.println("========================");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao listar currículos: " + e.getMessage());
        }
    }

    private void buscarCurriculoPorId() {
        System.out.println("\n=== BUSCAR CURRÍCULO POR ID ===");
        System.out.print("ID do currículo: ");
        int id = in.nextInt();
        in.nextLine();
        
        try {
            Curriculo curriculo = secretaria.buscarCurriculoPorId(id);
            
            if (curriculo != null) {
                System.out.println("Currículo encontrado:");
                System.out.println(curriculo.toString());
            } else {
                System.out.println("Currículo não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar currículo: " + e.getMessage());
        }
    }

    private void buscarCurriculosPorCurso() {
        System.out.println("\n=== BUSCAR CURRÍCULOS POR CURSO ===");
        System.out.print("ID do curso: ");
        int idCurso = in.nextInt();
        in.nextLine();
        
        try {
            List<Curriculo> curriculos = secretaria.buscarCurriculosPorIdCurso(idCurso);
            
            if (curriculos.isEmpty()) {
                System.out.println("Nenhum currículo encontrado.");
            } else {
                System.out.println("Currículos encontrados: " + curriculos.size());
                for (Curriculo curriculo : curriculos) {
                    System.out.println(curriculo.toString());
                    System.out.println("========================");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar currículos: " + e.getMessage());
        }
    }

    private void buscarCurriculosPorDisciplina() {
        System.out.println("\n=== BUSCAR CURRÍCULOS POR DISCIPLINA ===");
        System.out.print("Nome da disciplina: ");
        String nomeDisciplina = in.nextLine();
        
        try {
            List<Curriculo> curriculos = secretaria.buscarCurriculosPorDisciplina(nomeDisciplina);
            
            if (curriculos.isEmpty()) {
                System.out.println("Nenhum currículo encontrado.");
            } else {
                System.out.println("Currículos encontrados: " + curriculos.size());
                for (Curriculo curriculo : curriculos) {
                    System.out.println(curriculo.toString());
                    System.out.println("========================");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar currículos: " + e.getMessage());
        }
    }

    private void adicionarDisciplinaAoCurriculo() {
        System.out.println("\n=== ADICIONAR DISCIPLINA AO CURRÍCULO ===");
        System.out.print("ID do currículo: ");
        int idCurriculo = in.nextInt();
        in.nextLine();
        
        System.out.print("Nome da disciplina: ");
        String nomeDisciplina = in.nextLine();
        
        try {
            boolean sucesso = secretaria.adicionarDisciplinaAoCurriculo(idCurriculo, nomeDisciplina);
            
            if (sucesso) {
                System.out.println("Disciplina adicionada com sucesso!");
            } else {
                System.out.println("Currículo não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao adicionar disciplina: " + e.getMessage());
        }
    }

    private void removerDisciplinaDoCurriculo() {
        System.out.println("\n=== REMOVER DISCIPLINA DO CURRÍCULO ===");
        System.out.print("ID do currículo: ");
        int idCurriculo = in.nextInt();
        in.nextLine();
        
        System.out.print("Nome da disciplina: ");
        String nomeDisciplina = in.nextLine();
        
        try {
            boolean sucesso = secretaria.removerDisciplinaDoCurriculo(idCurriculo, nomeDisciplina);
            
            if (sucesso) {
                System.out.println("Disciplina removida com sucesso!");
            } else {
                System.out.println("Currículo não encontrado ou disciplina não existe no currículo.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao remover disciplina: " + e.getMessage());
        }
    }

    private void editarCurriculo() {
        System.out.println("\n=== EDITAR CURRÍCULO ===");
        System.out.print("ID do currículo: ");
        int id = in.nextInt();
        in.nextLine();
        
        System.out.print("ID do novo curso (deixe em branco para não alterar): ");
        String cursoStr = in.nextLine();
        Curso novoCurso = null;
        if (!cursoStr.trim().isEmpty()) {
            try {
                int idCurso = Integer.parseInt(cursoStr);
                try {
                    novoCurso = secretaria.buscarCursoPorId(idCurso);
                } catch (Exception e) {
                    System.out.println("Erro ao buscar curso: " + e.getMessage());
                    return;
                }
                if (novoCurso == null) {
                    System.out.println("Curso não encontrado!");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID do curso inválido!");
                return;
            }
        }
        
        try {
            boolean sucesso = secretaria.editarCurriculoPorId(id, novoCurso);
            
            if (sucesso) {
                System.out.println("Currículo editado com sucesso!");
            } else {
                System.out.println("Currículo não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao editar currículo: " + e.getMessage());
        }
    }

    private void deletarCurriculo() {
        System.out.println("\n=== DELETAR CURRÍCULO ===");
        System.out.print("ID do currículo: ");
        int id = in.nextInt();
        in.nextLine();
        
        System.out.print("Tem certeza? (s/n): ");
        String confirmacao = in.nextLine();
        
        if (confirmacao.toLowerCase().equals("s")) {
            try {
                boolean sucesso = secretaria.deletarCurriculoPorId(id);
                
                if (sucesso) {
                    System.out.println("Currículo deletado com sucesso!");
                } else {
                    System.out.println("Currículo não encontrado.");
                }
            } catch (IOException e) {
                System.out.println("Erro ao deletar currículo: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private void imprimirMenu(String header, String[] opcoes) {
        MenuUtils.imprimirMenu(header, opcoes);
    }
}
