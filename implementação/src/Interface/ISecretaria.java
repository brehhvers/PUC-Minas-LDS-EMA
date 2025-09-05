package Interface;

import java.util.ArrayList;
import Business.Curriculo;
import Business.Curso;
import Business.Disciplina;
import Business.Pessoa.Aluno;
import Business.Pessoa.Professor;
import Business.Pessoa.Secretaria;
import Enum.TipoDisciplina;

public interface ISecretaria {
    
    // Métodos para criação de entidades
    Disciplina criarDisciplina(TipoDisciplina tipo);
    Curso criarCurso(String nome, int numCreditos);
    Curriculo criarCurriculo(Curso curso);
    
    // Métodos para cadastro de usuários
    Aluno cadastrarAluno(String nome, String email, String senha);
    Professor cadastrarProfessor(String nome, String email, String senha);
    Secretaria cadastrarSecretaria(String nome, String email, String senha);
    
    // Métodos para consolidação e gestão
    void consolidarMatriculas(ArrayList<IEfetivavel> disciplinas, ArrayList<Aluno> alunos);
    
    // Métodos para consultas e listagens
    ArrayList<Aluno> listarAlunos();
    ArrayList<Professor> listarProfessores();
    ArrayList<Secretaria> listarSecretarias();
    ArrayList<Curso> listarCursos();
    ArrayList<Disciplina> listarDisciplinas();
    ArrayList<Curriculo> listarCurriculos();
    
    // Métodos para busca específica
    Aluno buscarAlunoPorCodigo(int codigo);
    Professor buscarProfessorPorCodigo(int codigo);
    Secretaria buscarSecretariaPorCodigo(int codigo);
    Curso buscarCursoPorId(int id);
    Disciplina buscarDisciplinaPorId(int id);
}
