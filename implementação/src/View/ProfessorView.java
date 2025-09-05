package View;

import java.util.ArrayList;
import java.util.Scanner;

import Business.Disciplina;
import Business.Pessoa.Aluno;
import Business.Pessoa.Professor;

public class ProfessorView {
    private Scanner in;
    private Professor professor;

    public ProfessorView(Scanner in, Professor professor) {
        this.in = in;
        this.professor = professor;
    }

    public void menu() {
        int opcao;
        String header = "Menu do Professor";
        String[] opcoes = {
                "1 - Consultar disciplinas",
                "2 - Consultar alunos por disciplina",
                "3 - Consultar lista completa de alunos",
                "4 - Exibir detalhes do professor",
                "0 - Voltar ao menu principal"
        };

        do {
            Menu.imprimirMenu(header, opcoes);
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1 -> listarDisciplinas();
                case 2 -> listarAlunosPorDisciplina();
                case 3 -> listarAlunos();
                case 4 -> listarDetalhesProfessor();
                case 0 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void listarDetalhesProfessor() {
        System.out.println(this.professor);
    }

    private void listarDisciplinas() {
        ArrayList<Disciplina> disciplinas = this.professor.getDisciplinas();

        if (disciplinas.isEmpty()) {
            System.out.println("Ainda não há registros para exibir.");
        } else {

            System.out.println("Registros encontrados:");
            for (Disciplina disciplina : disciplinas) {
                System.out.println(disciplina);
            }
        }
    }

    private void listarAlunosPorDisciplina() {
        ArrayList<Aluno> alunos;

        System.out.print("Informe o código identificador da disciplina para consulta: ");
        int id = in.nextInt();
        in.nextLine();

        try {
            alunos = this.professor.getAlunosPorDisciplina(id);

            if (alunos.isEmpty()) {
                System.out.println("Ainda não há registros para exibir.");
            } else {

                System.out.println("Registros encontrados: ");
                for (Aluno aluno : alunos) {
                    System.out.println(aluno);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarAlunos() {
        ArrayList<Aluno> alunos = this.professor.getAllAlunos();

        if (alunos.isEmpty()) {
            System.out.println("Ainda não há registros para exibir.");
        } else {

            System.out.println("Registros encontrados: ");
            for (Aluno aluno : alunos) {
                System.out.println(aluno);
            }
        }
    }
}
