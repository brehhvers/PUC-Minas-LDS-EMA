package View;

import java.util.ArrayList;
import java.util.Scanner;

import Business.Disciplina;
import Business.PlanoDeEnsino;
import Business.Pessoa.Aluno;
import Enum.StatusDisciplina;
import Enum.StatusPlano;
import Enum.TipoDisciplina;

public class AlunoInterface {
    private Scanner in;
    private Aluno aluno;
    private ArrayList<Disciplina> disciplinasDisponiveis;

    public AlunoInterface(Scanner in, Aluno aluno, ArrayList<Disciplina> disciplinasDisponiveis) {
        this.in = in;
        this.aluno = aluno;
        this.disciplinasDisponiveis = disciplinasDisponiveis;
    }

    public void menu() {
        int opcao;
        String header = "Menu do Aluno - " + aluno.getNome();
        String[] opcoes = {
                "1 - Criar novo plano de ensino",
                "2 - Adicionar disciplina ao plano atual",
                "3 - Remover disciplina do plano atual",
                "4 - Consultar plano atual",
                "5 - Confirmar plano de ensino",
                "6 - Cancelar plano de ensino",
                "7 - Consultar disciplinas disponíveis",
                "8 - Consultar histórico de planos",
                "0 - Voltar ao menu principal"
        };

        do {
            Menu.imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1 -> criarNovoPlano();
                case 2 -> adicionarDisciplina();
                case 3 -> removerDisciplina();
                case 4 -> consultarPlanoAtual();
                case 5 -> confirmarPlano();
                case 6 -> cancelarPlano();
                case 7 -> consultarDisciplinasDisponiveis();
                case 8 -> consultarHistoricoPlanos();
                case 0 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void criarNovoPlano() {
        try {
            PlanoDeEnsino novoPlano = aluno.criarPlanoDeEnsino();
            System.out.println("Plano de ensino criado com sucesso!");
            System.out.println("ID do plano: " + novoPlano.getId());
        } catch (Exception e) {
            System.out.println("Erro ao criar plano: " + e.getMessage());
        }
    }

    private void adicionarDisciplina() {
        try {
            PlanoDeEnsino planoAtual = aluno.getPlanoAtivo();
            
            if (!StatusPlano.RASCUNHO.equals(planoAtual.getStatus())) {
                System.out.println("Não é possível adicionar disciplinas. Plano já foi confirmado ou cancelado.");
                return;
            }

            consultarDisciplinasDisponiveis();
            System.out.print("Digite o ID da disciplina que deseja adicionar: ");
            int idDisciplina = in.nextInt();
            in.nextLine();

            Disciplina disciplinaSelecionada = encontrarDisciplinaPorId(idDisciplina);
            if (disciplinaSelecionada == null) {
                System.out.println("Disciplina não encontrada!");
                return;
            }

            if (planoAtual.addDisciplina(disciplinaSelecionada)) {
                System.out.println("Disciplina adicionada com sucesso!");
                System.out.println("Valor total do plano: R$ " + String.format("%.2f", planoAtual.getValorTotal()));
            } else {
                System.out.println("Não foi possível adicionar a disciplina. Verifique as regras de matrícula.");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerDisciplina() {
        try {
            PlanoDeEnsino planoAtual = aluno.getPlanoAtivo();
            
            if (!StatusPlano.RASCUNHO.equals(planoAtual.getStatus())) {
                System.out.println("Não é possível remover disciplinas. Plano já foi confirmado ou cancelado.");
                return;
            }

            ArrayList<Disciplina> disciplinasPlano = planoAtual.getDisciplinas();
            if (disciplinasPlano.isEmpty()) {
                System.out.println("Não há disciplinas no plano atual.");
                return;
            }

            System.out.println("Disciplinas no plano atual:");
            for (Disciplina d : disciplinasPlano) {
                System.out.println("ID: " + d.getId() + " - " + d.getNome() + " - Tipo: " + d.getTipo());
            }

            System.out.print("Digite o ID da disciplina que deseja remover: ");
            int idDisciplina = in.nextInt();
            in.nextLine();

            if (planoAtual.removerDisciplina(idDisciplina) != null) {
                System.out.println("Disciplina removida com sucesso!");
                System.out.println("Valor total do plano: R$ " + String.format("%.2f", planoAtual.getValorTotal()));
            } else {
                System.out.println("Disciplina não encontrada no plano!");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void consultarPlanoAtual() {
        try {
            PlanoDeEnsino planoAtual = aluno.getPlanoAtivo();
            
            System.out.println("\n=== PLANO ATUAL ===");
            System.out.println("ID do Plano: " + planoAtual.getId());
            System.out.println("Status: " + planoAtual.getStatus());
            System.out.println("Data de Criação: " + planoAtual.getDataCriacao());
            System.out.println("Valor Total: R$ " + String.format("%.2f", planoAtual.getValorTotal()));
            
            ArrayList<Disciplina> disciplinas = planoAtual.getDisciplinas();
            if (disciplinas.isEmpty()) {
                System.out.println("Nenhuma disciplina adicionada ao plano.");
            } else {
                System.out.println("\nDisciplinas no plano:");
                for (Disciplina d : disciplinas) {
                    System.out.println("- " + d.getNome() + " (" + d.getTipo() + ") - R$ " + String.format("%.2f", d.getValor()));
                }
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void confirmarPlano() {
        try {
            boolean confirmado = aluno.confirmarPlanoDeEnsino();
            if (confirmado) {
                System.out.println("Plano de ensino confirmado com sucesso!");
                System.out.println("Suas matrículas foram efetivadas.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao confirmar plano: " + e.getMessage());
        }
    }

    private void cancelarPlano() {
        try {
            PlanoDeEnsino planoCancelado = aluno.cancelarPlanoAtivo();
            System.out.println("Plano de ensino cancelado com sucesso!");
            System.out.println("ID do plano cancelado: " + planoCancelado.getId());
        } catch (Exception e) {
            System.out.println("Erro ao cancelar plano: " + e.getMessage());
        }
    }

    private void consultarDisciplinasDisponiveis() {
        ArrayList<Disciplina> disciplinasAtivas = new ArrayList<>();
        
        for (Disciplina d : disciplinasDisponiveis) {
            if (StatusDisciplina.ATIVA.equals(d.getStatus()) && d.getAlunosMatriculados().size() < 60) {
                disciplinasAtivas.add(d);
            }
        }

        if (disciplinasAtivas.isEmpty()) {
            System.out.println("Não há disciplinas disponíveis para matrícula no momento.");
        } else {
            System.out.println("\n=== DISCIPLINAS DISPONÍVEIS ===");
            for (Disciplina d : disciplinasAtivas) {
                System.out.println("ID: " + d.getId() + 
                                 " | Nome: " + d.getNome() + 
                                 " | Tipo: " + d.getTipo() + 
                                 " | Vagas: " + (60 - d.getAlunosMatriculados().size()) + "/60" +
                                 " | Valor: R$ " + String.format("%.2f", d.getValor()));
            }
        }
    }

    private void consultarHistoricoPlanos() {
        ArrayList<PlanoDeEnsino> historico = aluno.getPlanosDeEnsino();
        
        if (historico.isEmpty()) {
            System.out.println("Não há planos de ensino no histórico.");
        } else {
            System.out.println("\n=== HISTÓRICO DE PLANOS ===");
            for (PlanoDeEnsino plano : historico) {
                System.out.println("ID: " + plano.getId() + 
                                 " | Status: " + plano.getStatus() + 
                                 " | Data: " + plano.getDataCriacao() + 
                                 " | Valor: R$ " + String.format("%.2f", plano.getValorTotal()) +
                                 " | Disciplinas: " + plano.getDisciplinas().size());
            }
        }
    }

    private Disciplina encontrarDisciplinaPorId(int id) {
        for (Disciplina d : disciplinasDisponiveis) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }
}
