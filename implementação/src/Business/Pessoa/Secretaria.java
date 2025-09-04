package Business.Pessoa;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import Business.Curriculo;
import Business.Curso;
import Business.Disciplina;
import Data.DAO.AlunoDAO;
import Data.DAO.CurriculoDAO;
import Data.DAO.CursoDAO;
import Data.DAO.DisciplinaDAO;
import Data.DAO.ProfessorDAO;
import Data.DAO.SecretariaDAO;
import Enum.StatusDisciplina;
import Enum.TipoAcesso;
import Enum.TipoDisciplina;
import Interface.IEfetivavel;

public class Secretaria extends Usuario {
    public Secretaria() {
        super();
    }
    
    public Secretaria(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public Disciplina criarDisciplina(TipoDisciplina tipo) {
        return new Disciplina(tipo);
    }

    public Curso criarCurso(String nome, int numCreditos) {
        return new Curso(nome, numCreditos);
    }

    public Curriculo criarCurriculo(Curso curso) {
        return new Curriculo(curso);
    }

    public Aluno cadastrarAluno(String nome, String email, String senha) {
        return new Aluno(nome, email, senha);
    }

    public Professor cadastrarProfessor(String nome, String email, String senha) {
        return new Professor(nome, email, senha);
    }

    public Secretaria cadastrarSecretaria(String nome, String email, String senha) {
        return new Secretaria(nome, email, senha);
    }

    /**
     * Método genérico para cadastrar usuários de qualquer tipo
     * @param nome Nome do usuário
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @param tipoAcesso Tipo de acesso (ALUNO, PROFESSOR, SECRETARIA)
     * @return Usuario cadastrado
     */
    public Usuario cadastrarUsuario(String nome, String email, String senha, TipoAcesso tipoAcesso) {
        switch (tipoAcesso) {
            case ALUNO:
                return cadastrarAluno(nome, email, senha);
            case PROFESSOR:
                return cadastrarProfessor(nome, email, senha);
            case SECRETARIA:
                return cadastrarSecretaria(nome, email, senha);
            default:
                throw new IllegalArgumentException("Tipo de acesso inválido: " + tipoAcesso);
        }
    }

    /**
     * Lista todos os usuários cadastrados no sistema
     * @return Lista com todos os usuários (Alunos, Professores e Secretárias)
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Usuario> listarTodosUsuarios() throws IOException {
        List<Usuario> todosUsuarios = new ArrayList<>();
        
        // Carregar todos os alunos
        try {
            List<Aluno> alunos = AlunoDAO.getDAO().carregar();
            todosUsuarios.addAll(alunos);
        } catch (IOException e) {
            // Se não conseguir carregar alunos, continua com os outros tipos
            System.err.println("Erro ao carregar alunos: " + e.getMessage());
        }
        
        // Carregar todos os professores
        try {
            List<Professor> professores = ProfessorDAO.getDAO().carregar();
            todosUsuarios.addAll(professores);
        } catch (IOException e) {
            // Se não conseguir carregar professores, continua com os outros tipos
            System.err.println("Erro ao carregar professores: " + e.getMessage());
        }
        
        // Carregar todas as secretárias
        try {
            List<Secretaria> secretarias = SecretariaDAO.getDAO().carregar();
            todosUsuarios.addAll(secretarias);
        } catch (IOException e) {
            // Se não conseguir carregar secretárias, continua
            System.err.println("Erro ao carregar secretárias: " + e.getMessage());
        }
        
        return todosUsuarios;
    }

    /**
     * Busca um usuário pelo email
     * @param email Email do usuário a ser buscado
     * @return Usuario encontrado ou null se não encontrado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public Usuario buscarUsuarioPorEmail(String email) throws IOException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }
        
        // Buscar entre os alunos
        try {
            List<Aluno> alunos = AlunoDAO.getDAO().carregar();
            for (Aluno aluno : alunos) {
                if (email.equals(aluno.getEmail())) {
                    return aluno;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao buscar alunos: " + e.getMessage());
        }
        
        // Buscar entre os professores
        try {
            List<Professor> professores = ProfessorDAO.getDAO().carregar();
            for (Professor professor : professores) {
                if (email.equals(professor.getEmail())) {
                    return professor;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao buscar professores: " + e.getMessage());
        }
        
        // Buscar entre as secretárias
        try {
            List<Secretaria> secretarias = SecretariaDAO.getDAO().carregar();
            for (Secretaria secretaria : secretarias) {
                if (email.equals(secretaria.getEmail())) {
                    return secretaria;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao buscar secretárias: " + e.getMessage());
        }
        
        return null; // Usuário não encontrado
    }

    /**
     * Edita um usuário pelo email
     * @param email Email do usuário a ser editado
     * @param novoNome Novo nome (pode ser null para não alterar)
     * @param novoEmail Novo email (pode ser null para não alterar)
     * @param novaSenha Nova senha (pode ser null para não alterar)
     * @param ativo Status ativo/inativo (pode ser null para não alterar)
     * @return true se o usuário foi editado com sucesso, false se não foi encontrado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public boolean editarUsuarioPorEmail(String email, String novoNome, String novoEmail, String novaSenha, Boolean ativo) throws IOException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }

        // Buscar o usuário primeiro
        Usuario usuario = buscarUsuarioPorEmail(email);
        if (usuario == null) {
            return false; // Usuário não encontrado
        }

        // Aplicar as alterações
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            usuario.setNome(novoNome.trim());
        }
        
        if (novoEmail != null && !novoEmail.trim().isEmpty()) {
            usuario.setEmail(novoEmail.trim());
        }
        
        if (novaSenha != null && !novaSenha.trim().isEmpty()) {
            usuario.setSenha(novaSenha.trim());
        }
        
        if (ativo != null) {
            usuario.setAtivo(ativo);
        }

        // Salvar as alterações baseado no tipo do usuário
        if (usuario instanceof Aluno) {
            return salvarAlunoAtualizado((Aluno) usuario);
        } else if (usuario instanceof Professor) {
            return salvarProfessorAtualizado((Professor) usuario);
        } else if (usuario instanceof Secretaria) {
            return salvarSecretariaAtualizada((Secretaria) usuario);
        }

        return false;
    }

    /**
     * Salva um aluno atualizado no arquivo
     * @param aluno Aluno com as alterações
     * @return true se salvo com sucesso
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private boolean salvarAlunoAtualizado(Aluno aluno) throws IOException {
        try {
            List<Aluno> alunos = AlunoDAO.getDAO().carregar();
            
            // Encontrar e substituir o aluno na lista
            for (int i = 0; i < alunos.size(); i++) {
                if (alunos.get(i).getCodPessoa() == aluno.getCodPessoa()) {
                    alunos.set(i, aluno);
                    break;
                }
            }
            
            // Salvar a lista atualizada
            salvarListaAlunos(alunos);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar aluno atualizado: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Salva um professor atualizado no arquivo
     * @param professor Professor com as alterações
     * @return true se salvo com sucesso
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private boolean salvarProfessorAtualizado(Professor professor) throws IOException {
        try {
            List<Professor> professores = ProfessorDAO.getDAO().carregar();
            
            // Encontrar e substituir o professor na lista
            for (int i = 0; i < professores.size(); i++) {
                if (professores.get(i).getCodPessoa() == professor.getCodPessoa()) {
                    professores.set(i, professor);
                    break;
                }
            }
            
            // Salvar a lista atualizada
            salvarListaProfessores(professores);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar professor atualizado: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Salva uma secretária atualizada no arquivo
     * @param secretaria Secretaria com as alterações
     * @return true se salva com sucesso
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private boolean salvarSecretariaAtualizada(Secretaria secretaria) throws IOException {
        try {
            List<Secretaria> secretarias = SecretariaDAO.getDAO().carregar();
            
            // Encontrar e substituir a secretária na lista
            for (int i = 0; i < secretarias.size(); i++) {
                if (secretarias.get(i).getCodPessoa() == secretaria.getCodPessoa()) {
                    secretarias.set(i, secretaria);
                    break;
                }
            }
            
            // Salvar a lista atualizada
            salvarListaSecretarias(secretarias);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar secretária atualizada: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Salva uma lista de alunos no arquivo
     * @param alunos Lista de alunos para salvar
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private void salvarListaAlunos(List<Aluno> alunos) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("implementação/src/Data/File/aluno.txt", false), "UTF-8"))) {
            
            for (Aluno aluno : alunos) {
                escritor.write(aluno.toPersist());
                escritor.newLine();
            }
            escritor.flush();
        }
    }

    /**
     * Salva uma lista de professores no arquivo
     * @param professores Lista de professores para salvar
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private void salvarListaProfessores(List<Professor> professores) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("implementação/src/Data/File/professor.txt", false), "UTF-8"))) {
            
            for (Professor professor : professores) {
                escritor.write(professor.toPersist());
                escritor.newLine();
            }
            escritor.flush();
        }
    }

    /**
     * Salva uma lista de secretárias no arquivo
     * @param secretarias Lista de secretárias para salvar
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private void salvarListaSecretarias(List<Secretaria> secretarias) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("implementação/src/Data/File/secretaria.txt", false), "UTF-8"))) {
            
            for (Secretaria secretaria : secretarias) {
                escritor.write(secretaria.toPersist());
                escritor.newLine();
            }
            escritor.flush();
        }
    }

    /**
     * Deleta um usuário pelo email
     * @param email Email do usuário a ser deletado
     * @return true se o usuário foi deletado com sucesso, false se não foi encontrado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public boolean deletarUsuarioPorEmail(String email) throws IOException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }

        // Buscar o usuário primeiro
        Usuario usuario = buscarUsuarioPorEmail(email);
        if (usuario == null) {
            return false; // Usuário não encontrado
        }

        // Deletar baseado no tipo do usuário
        if (usuario instanceof Aluno) {
            return deletarAluno((Aluno) usuario);
        } else if (usuario instanceof Professor) {
            return deletarProfessor((Professor) usuario);
        } else if (usuario instanceof Secretaria) {
            return deletarSecretaria((Secretaria) usuario);
        }

        return false;
    }

    /**
     * Deleta um aluno do arquivo
     * @param aluno Aluno a ser deletado
     * @return true se deletado com sucesso
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private boolean deletarAluno(Aluno aluno) throws IOException {
        try {
            List<Aluno> alunos = AlunoDAO.getDAO().carregar();
            
            // Remover o aluno da lista
            boolean removido = alunos.removeIf(a -> a.getCodPessoa() == aluno.getCodPessoa());
            
            if (removido) {
                // Salvar a lista atualizada
                salvarListaAlunos(alunos);
                return true;
            }
            return false;
        } catch (IOException e) {
            System.err.println("Erro ao deletar aluno: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Deleta um professor do arquivo
     * @param professor Professor a ser deletado
     * @return true se deletado com sucesso
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private boolean deletarProfessor(Professor professor) throws IOException {
        try {
            List<Professor> professores = ProfessorDAO.getDAO().carregar();
            
            // Remover o professor da lista
            boolean removido = professores.removeIf(p -> p.getCodPessoa() == professor.getCodPessoa());
            
            if (removido) {
                // Salvar a lista atualizada
                salvarListaProfessores(professores);
                return true;
            }
            return false;
        } catch (IOException e) {
            System.err.println("Erro ao deletar professor: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Deleta uma secretária do arquivo
     * @param secretaria Secretaria a ser deletada
     * @return true se deletada com sucesso
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private boolean deletarSecretaria(Secretaria secretaria) throws IOException {
        try {
            List<Secretaria> secretarias = SecretariaDAO.getDAO().carregar();
            
            // Remover a secretária da lista
            boolean removido = secretarias.removeIf(s -> s.getCodPessoa() == secretaria.getCodPessoa());
            
            if (removido) {
                // Salvar a lista atualizada
                salvarListaSecretarias(secretarias);
                return true;
            }
            return false;
        } catch (IOException e) {
            System.err.println("Erro ao deletar secretária: " + e.getMessage());
            throw e;
        }
    }

    // ==================== CRUD DISCIPLINAS ====================

    /**
     * Cadastra uma nova disciplina
     * @param nome Nome da disciplina
     * @param valor Valor da disciplina
     * @param tipo Tipo da disciplina (OBRIGATORIA ou OPTATIVA)
     * @param professor Professor responsável pela disciplina
     * @return Disciplina criada
     */
    public Disciplina cadastrarDisciplina(String nome, double valor, TipoDisciplina tipo, Professor professor) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da disciplina não pode ser nulo ou vazio");
        }
        if (valor < 0) {
            throw new IllegalArgumentException("Valor da disciplina não pode ser negativo");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo da disciplina não pode ser nulo");
        }
        if (professor == null) {
            throw new IllegalArgumentException("Professor não pode ser nulo");
        }

        Disciplina disciplina = new Disciplina(tipo);
        disciplina.setNome(nome.trim());
        disciplina.setValor(valor);
        disciplina.setProfessor(professor);
        
        // Adicionar a disciplina ao professor
        professor.addDisciplina(disciplina);
        
        return disciplina;
    }

    /**
     * Lista todas as disciplinas cadastradas
     * @return Lista com todas as disciplinas
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Disciplina> listarTodasDisciplinas() throws IOException {
        try {
            return DisciplinaDAO.getDAO().carregar();
        } catch (IOException e) {
            System.err.println("Erro ao carregar disciplinas: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca uma disciplina pelo ID
     * @param id ID da disciplina
     * @return Disciplina encontrada ou null se não encontrada
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public Disciplina buscarDisciplinaPorId(int id) throws IOException {
        try {
            return DisciplinaDAO.getDAO().carregarPorId(String.valueOf(id));
        } catch (IOException e) {
            System.err.println("Erro ao buscar disciplina: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca disciplinas pelo nome
     * @param nome Nome da disciplina (busca parcial)
     * @return Lista de disciplinas que contêm o nome
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Disciplina> buscarDisciplinasPorNome(String nome) throws IOException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }

        try {
            List<Disciplina> todasDisciplinas = DisciplinaDAO.getDAO().carregar();
            return todasDisciplinas.stream()
                    .filter(d -> d.getNome().toLowerCase().contains(nome.toLowerCase()))
                    .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            System.err.println("Erro ao buscar disciplinas por nome: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca disciplinas por tipo
     * @param tipo Tipo da disciplina
     * @return Lista de disciplinas do tipo especificado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Disciplina> buscarDisciplinasPorTipo(TipoDisciplina tipo) throws IOException {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo não pode ser nulo");
        }

        try {
            List<Disciplina> todasDisciplinas = DisciplinaDAO.getDAO().carregar();
            return todasDisciplinas.stream()
                    .filter(d -> d.getTipo() == tipo)
                    .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            System.err.println("Erro ao buscar disciplinas por tipo: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca disciplinas por status
     * @param status Status da disciplina
     * @return Lista de disciplinas com o status especificado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Disciplina> buscarDisciplinasPorStatus(StatusDisciplina status) throws IOException {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }

        try {
            List<Disciplina> todasDisciplinas = DisciplinaDAO.getDAO().carregar();
            return todasDisciplinas.stream()
                    .filter(d -> d.getStatus() == status)
                    .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            System.err.println("Erro ao buscar disciplinas por status: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Edita uma disciplina pelo ID
     * @param id ID da disciplina a ser editada
     * @param novoNome Novo nome (pode ser null para não alterar)
     * @param novoValor Novo valor (pode ser null para não alterar)
     * @param novoTipo Novo tipo (pode ser null para não alterar)
     * @param novoStatus Novo status (pode ser null para não alterar)
     * @param novoProfessor Novo professor (pode ser null para não alterar)
     * @return true se a disciplina foi editada com sucesso, false se não foi encontrada
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public boolean editarDisciplinaPorId(int id, String novoNome, Double novoValor, TipoDisciplina novoTipo, 
                                        StatusDisciplina novoStatus, Professor novoProfessor) throws IOException {
        // Buscar a disciplina primeiro
        Disciplina disciplina = buscarDisciplinaPorId(id);
        if (disciplina == null) {
            return false; // Disciplina não encontrada
        }

        // Aplicar as alterações
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            disciplina.setNome(novoNome.trim());
        }
        
        if (novoValor != null && novoValor >= 0) {
            disciplina.setValor(novoValor);
        }
        
        if (novoTipo != null) {
            disciplina.setTipo(novoTipo);
        }
        
        if (novoStatus != null) {
            disciplina.setStatus(novoStatus);
        }
        
        if (novoProfessor != null) {
            // Remover da disciplina anterior se existir
            if (disciplina.getProfessor() != null) {
                disciplina.getProfessor().removerDisciplina(disciplina.getId());
            }
            
            // Atualizar professor
            disciplina.setProfessor(novoProfessor);
            novoProfessor.addDisciplina(disciplina);
        }

        // Salvar as alterações
        return salvarDisciplinaAtualizada(disciplina);
    }

    /**
     * Deleta uma disciplina pelo ID
     * @param id ID da disciplina a ser deletada
     * @return true se a disciplina foi deletada com sucesso, false se não foi encontrada
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public boolean deletarDisciplinaPorId(int id) throws IOException {
        // Buscar a disciplina primeiro
        Disciplina disciplina = buscarDisciplinaPorId(id);
        if (disciplina == null) {
            return false; // Disciplina não encontrada
        }

        // Remover a disciplina do professor se existir
        if (disciplina.getProfessor() != null) {
            disciplina.getProfessor().removerDisciplina(disciplina.getId());
        }

        // Deletar a disciplina
        return deletarDisciplina(disciplina);
    }

    /**
     * Salva uma disciplina atualizada no arquivo
     * @param disciplina Disciplina com as alterações
     * @return true se salva com sucesso
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private boolean salvarDisciplinaAtualizada(Disciplina disciplina) throws IOException {
        try {
            List<Disciplina> disciplinas = DisciplinaDAO.getDAO().carregar();
            
            // Encontrar e substituir a disciplina na lista
            for (int i = 0; i < disciplinas.size(); i++) {
                if (disciplinas.get(i).getId() == disciplina.getId()) {
                    disciplinas.set(i, disciplina);
                    break;
                }
            }
            
            // Salvar a lista atualizada
            salvarListaDisciplinas(disciplinas);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar disciplina atualizada: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Deleta uma disciplina do arquivo
     * @param disciplina Disciplina a ser deletada
     * @return true se deletada com sucesso
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private boolean deletarDisciplina(Disciplina disciplina) throws IOException {
        try {
            List<Disciplina> disciplinas = DisciplinaDAO.getDAO().carregar();
            
            // Remover a disciplina da lista
            boolean removido = disciplinas.removeIf(d -> d.getId() == disciplina.getId());
            
            if (removido) {
                // Salvar a lista atualizada
                salvarListaDisciplinas(disciplinas);
                return true;
            }
            return false;
        } catch (IOException e) {
            System.err.println("Erro ao deletar disciplina: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Salva uma lista de disciplinas no arquivo
     * @param disciplinas Lista de disciplinas para salvar
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private void salvarListaDisciplinas(List<Disciplina> disciplinas) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("implementação/src/Data/File/disciplina.txt", false), "UTF-8"))) {
            
            for (Disciplina disciplina : disciplinas) {
                escritor.write(disciplina.toPersist());
                escritor.newLine();
            }
            escritor.flush();
        }
    }

    // ==================== CRUD CURSOS ====================

    /**
     * Cadastra um novo curso
     * @param nome Nome do curso
     * @param numCreditos Número de créditos do curso
     * @param departamento Departamento do curso (opcional)
     * @return Curso criado
     */
    public Curso cadastrarCurso(String nome, int numCreditos, String departamento) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do curso não pode ser nulo ou vazio");
        }
        if (numCreditos <= 0) {
            throw new IllegalArgumentException("Número de créditos deve ser maior que zero");
        }

        Curso curso = new Curso(nome.trim(), numCreditos);
        if (departamento != null && !departamento.trim().isEmpty()) {
            curso.setDepartamento(departamento.trim());
        }
        
        return curso;
    }

    /**
     * Lista todos os cursos cadastrados
     * @return Lista com todos os cursos
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Curso> listarTodosCursos() throws IOException {
        try {
            return CursoDAO.getDAO().carregar();
        } catch (IOException e) {
            System.err.println("Erro ao carregar cursos: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca um curso pelo ID
     * @param id ID do curso
     * @return Curso encontrado ou null se não encontrado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public Curso buscarCursoPorId(int id) throws IOException {
        try {
            return CursoDAO.getDAO().carregarPorId(String.valueOf(id));
        } catch (IOException e) {
            System.err.println("Erro ao buscar curso: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca cursos pelo nome
     * @param nome Nome do curso (busca parcial)
     * @return Lista de cursos que contêm o nome
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Curso> buscarCursosPorNome(String nome) throws IOException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }

        try {
            List<Curso> todosCursos = CursoDAO.getDAO().carregar();
            return todosCursos.stream()
                    .filter(c -> c.getNome().toLowerCase().contains(nome.toLowerCase()))
                    .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            System.err.println("Erro ao buscar cursos por nome: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca cursos por departamento
     * @param departamento Departamento do curso
     * @return Lista de cursos do departamento especificado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Curso> buscarCursosPorDepartamento(String departamento) throws IOException {
        if (departamento == null || departamento.trim().isEmpty()) {
            throw new IllegalArgumentException("Departamento não pode ser nulo ou vazio");
        }

        try {
            List<Curso> todosCursos = CursoDAO.getDAO().carregar();
            return todosCursos.stream()
                    .filter(c -> c.getDepartamento() != null && 
                               c.getDepartamento().toLowerCase().contains(departamento.toLowerCase()))
                    .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            System.err.println("Erro ao buscar cursos por departamento: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca cursos por faixa de créditos
     * @param minCreditos Número mínimo de créditos
     * @param maxCreditos Número máximo de créditos
     * @return Lista de cursos na faixa de créditos especificada
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Curso> buscarCursosPorCreditos(int minCreditos, int maxCreditos) throws IOException {
        if (minCreditos < 0 || maxCreditos < 0) {
            throw new IllegalArgumentException("Número de créditos não pode ser negativo");
        }
        if (minCreditos > maxCreditos) {
            throw new IllegalArgumentException("Créditos mínimos não podem ser maiores que os máximos");
        }

        try {
            List<Curso> todosCursos = CursoDAO.getDAO().carregar();
            return todosCursos.stream()
                    .filter(c -> c.getNumCreditos() >= minCreditos && c.getNumCreditos() <= maxCreditos)
                    .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            System.err.println("Erro ao buscar cursos por créditos: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Edita um curso pelo ID
     * @param id ID do curso a ser editado
     * @param novoNome Novo nome (pode ser null para não alterar)
     * @param novoNumCreditos Novo número de créditos (pode ser null para não alterar)
     * @param novoDepartamento Novo departamento (pode ser null para não alterar)
     * @return true se o curso foi editado com sucesso, false se não foi encontrado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public boolean editarCursoPorId(int id, String novoNome, Integer novoNumCreditos, String novoDepartamento) throws IOException {
        // Buscar o curso primeiro
        Curso curso = buscarCursoPorId(id);
        if (curso == null) {
            return false; // Curso não encontrado
        }

        // Aplicar as alterações
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            curso.setNome(novoNome.trim());
        }
        
        if (novoNumCreditos != null && novoNumCreditos > 0) {
            curso.setNumCreditos(novoNumCreditos);
        }
        
        if (novoDepartamento != null) {
            curso.setDepartamento(novoDepartamento.trim().isEmpty() ? null : novoDepartamento.trim());
        }

        // Salvar as alterações
        return salvarCursoAtualizado(curso);
    }

    /**
     * Deleta um curso pelo ID
     * @param id ID do curso a ser deletado
     * @return true se o curso foi deletado com sucesso, false se não foi encontrado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public boolean deletarCursoPorId(int id) throws IOException {
        // Buscar o curso primeiro
        Curso curso = buscarCursoPorId(id);
        if (curso == null) {
            return false; // Curso não encontrado
        }

        // Deletar o curso
        return deletarCurso(curso);
    }

    /**
     * Salva um curso atualizado no arquivo
     * @param curso Curso com as alterações
     * @return true se salvo com sucesso
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private boolean salvarCursoAtualizado(Curso curso) throws IOException {
        try {
            List<Curso> cursos = CursoDAO.getDAO().carregar();
            
            // Encontrar e substituir o curso na lista
            for (int i = 0; i < cursos.size(); i++) {
                if (cursos.get(i).getId() == curso.getId()) {
                    cursos.set(i, curso);
                    break;
                }
            }
            
            // Salvar a lista atualizada
            salvarListaCursos(cursos);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar curso atualizado: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Deleta um curso do arquivo
     * @param curso Curso a ser deletado
     * @return true se deletado com sucesso
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private boolean deletarCurso(Curso curso) throws IOException {
        try {
            List<Curso> cursos = CursoDAO.getDAO().carregar();
            
            // Remover o curso da lista
            boolean removido = cursos.removeIf(c -> c.getId() == curso.getId());
            
            if (removido) {
                // Salvar a lista atualizada
                salvarListaCursos(cursos);
                return true;
            }
            return false;
        } catch (IOException e) {
            System.err.println("Erro ao deletar curso: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Salva uma lista de cursos no arquivo
     * @param cursos Lista de cursos para salvar
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private void salvarListaCursos(List<Curso> cursos) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("implementação/src/Data/File/curso.txt", false), "UTF-8"))) {
            
            for (Curso curso : cursos) {
                escritor.write(curso.toPersist());
                escritor.newLine();
            }
            escritor.flush();
        }
    }

    // ==================== CRUD CURRÍCULOS ====================

    /**
     * Cadastra um novo currículo
     * @param curso Curso associado ao currículo
     * @return Curriculo criado
     */
    public Curriculo cadastrarCurriculo(Curso curso) {
        if (curso == null) {
            throw new IllegalArgumentException("Curso não pode ser nulo");
        }

        return new Curriculo(curso);
    }

    /**
     * Lista todos os currículos cadastrados
     * @return Lista com todos os currículos
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Curriculo> listarTodosCurriculos() throws IOException {
        try {
            return CurriculoDAO.getDAO().carregar();
        } catch (IOException e) {
            System.err.println("Erro ao carregar currículos: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca um currículo pelo ID
     * @param id ID do currículo
     * @return Curriculo encontrado ou null se não encontrado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public Curriculo buscarCurriculoPorId(int id) throws IOException {
        try {
            return CurriculoDAO.getDAO().carregarPorId(String.valueOf(id));
        } catch (IOException e) {
            System.err.println("Erro ao buscar currículo: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca currículos por curso
     * @param curso Curso associado ao currículo
     * @return Lista de currículos do curso especificado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Curriculo> buscarCurriculosPorCurso(Curso curso) throws IOException {
        if (curso == null) {
            throw new IllegalArgumentException("Curso não pode ser nulo");
        }

        try {
            List<Curriculo> todosCurriculos = CurriculoDAO.getDAO().carregar();
            return todosCurriculos.stream()
                    .filter(c -> c.getCurso() != null && c.getCurso().getId() == curso.getId())
                    .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            System.err.println("Erro ao buscar currículos por curso: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca currículos por ID do curso
     * @param idCurso ID do curso
     * @return Lista de currículos do curso especificado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Curriculo> buscarCurriculosPorIdCurso(int idCurso) throws IOException {
        try {
            List<Curriculo> todosCurriculos = CurriculoDAO.getDAO().carregar();
            return todosCurriculos.stream()
                    .filter(c -> c.getCurso() != null && c.getCurso().getId() == idCurso)
                    .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            System.err.println("Erro ao buscar currículos por ID do curso: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca currículos que contêm uma disciplina específica
     * @param nomeDisciplina Nome da disciplina
     * @return Lista de currículos que contêm a disciplina
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public List<Curriculo> buscarCurriculosPorDisciplina(String nomeDisciplina) throws IOException {
        if (nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da disciplina não pode ser nulo ou vazio");
        }

        try {
            List<Curriculo> todosCurriculos = CurriculoDAO.getDAO().carregar();
            return todosCurriculos.stream()
                    .filter(c -> c.getDisciplinas().contains(nomeDisciplina.trim()))
                    .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            System.err.println("Erro ao buscar currículos por disciplina: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Adiciona uma disciplina a um currículo
     * @param idCurriculo ID do currículo
     * @param nomeDisciplina Nome da disciplina a ser adicionada
     * @return true se a disciplina foi adicionada com sucesso
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public boolean adicionarDisciplinaAoCurriculo(int idCurriculo, String nomeDisciplina) throws IOException {
        if (nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da disciplina não pode ser nulo ou vazio");
        }

        Curriculo curriculo = buscarCurriculoPorId(idCurriculo);
        if (curriculo == null) {
            return false; // Currículo não encontrado
        }

        boolean adicionado = curriculo.addDisciplina(nomeDisciplina.trim());
        if (adicionado) {
            return salvarCurriculoAtualizado(curriculo);
        }
        return false;
    }

    /**
     * Remove uma disciplina de um currículo
     * @param idCurriculo ID do currículo
     * @param nomeDisciplina Nome da disciplina a ser removida
     * @return true se a disciplina foi removida com sucesso
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public boolean removerDisciplinaDoCurriculo(int idCurriculo, String nomeDisciplina) throws IOException {
        if (nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da disciplina não pode ser nulo ou vazio");
        }

        Curriculo curriculo = buscarCurriculoPorId(idCurriculo);
        if (curriculo == null) {
            return false; // Currículo não encontrado
        }

        try {
            curriculo.removerDisciplina(nomeDisciplina.trim());
            return salvarCurriculoAtualizado(curriculo);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao remover disciplina: " + e.getMessage());
            return false;
        }
    }

    /**
     * Edita um currículo pelo ID
     * @param id ID do currículo a ser editado
     * @param novoCurso Novo curso (pode ser null para não alterar)
     * @return true se o currículo foi editado com sucesso, false se não foi encontrado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public boolean editarCurriculoPorId(int id, Curso novoCurso) throws IOException {
        // Buscar o currículo primeiro
        Curriculo curriculo = buscarCurriculoPorId(id);
        if (curriculo == null) {
            return false; // Currículo não encontrado
        }

        // Aplicar as alterações
        if (novoCurso != null) {
            curriculo.setCurso(novoCurso);
        }

        // Salvar as alterações
        return salvarCurriculoAtualizado(curriculo);
    }

    /**
     * Deleta um currículo pelo ID
     * @param id ID do currículo a ser deletado
     * @return true se o currículo foi deletado com sucesso, false se não foi encontrado
     * @throws IOException Se houver erro ao acessar os arquivos
     */
    public boolean deletarCurriculoPorId(int id) throws IOException {
        // Buscar o currículo primeiro
        Curriculo curriculo = buscarCurriculoPorId(id);
        if (curriculo == null) {
            return false; // Currículo não encontrado
        }

        // Deletar o currículo
        return deletarCurriculo(curriculo);
    }

    /**
     * Salva um currículo atualizado no arquivo
     * @param curriculo Curriculo com as alterações
     * @return true se salvo com sucesso
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private boolean salvarCurriculoAtualizado(Curriculo curriculo) throws IOException {
        try {
            List<Curriculo> curriculos = CurriculoDAO.getDAO().carregar();
            
            // Encontrar e substituir o currículo na lista
            for (int i = 0; i < curriculos.size(); i++) {
                if (curriculos.get(i).getId() == curriculo.getId()) {
                    curriculos.set(i, curriculo);
                    break;
                }
            }
            
            // Salvar a lista atualizada
            salvarListaCurriculos(curriculos);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar currículo atualizado: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Deleta um currículo do arquivo
     * @param curriculo Curriculo a ser deletado
     * @return true se deletado com sucesso
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private boolean deletarCurriculo(Curriculo curriculo) throws IOException {
        try {
            List<Curriculo> curriculos = CurriculoDAO.getDAO().carregar();
            
            // Remover o currículo da lista
            boolean removido = curriculos.removeIf(c -> c.getId() == curriculo.getId());
            
            if (removido) {
                // Salvar a lista atualizada
                salvarListaCurriculos(curriculos);
                return true;
            }
            return false;
        } catch (IOException e) {
            System.err.println("Erro ao deletar currículo: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Salva uma lista de currículos no arquivo
     * @param curriculos Lista de currículos para salvar
     * @throws IOException Se houver erro ao acessar o arquivo
     */
    private void salvarListaCurriculos(List<Curriculo> curriculos) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("implementação/src/Data/File/curriculo.txt", false), "UTF-8"))) {
            
            for (Curriculo curriculo : curriculos) {
                escritor.write(curriculo.toPersist());
                escritor.newLine();
            }
            escritor.flush();
        }
    }

    public void consolidarMatriculas(ArrayList<IEfetivavel> disciplinas, ArrayList<Aluno> alunos) {
        disciplinas.stream().forEach(d -> d.efetivar());

        alunos.stream().forEach(a -> {
            IEfetivavel planoDeEnsino = a.getPlanoAtivo();
            planoDeEnsino.efetivar();
        });
    }

    @Override
    public String toString() {
        String string = String.format(
                "Código da Pessoa: %d%nNome: %s%nEmail: %s%nData de Cadastro: %s",
                this.getCodPessoa(),
                this.getNome(),
                this.getEmail(),
                this.getDataCadastro().format(this.formatter));

        return string;
    }

    @Override
    public String toPersist() {
        return String.format(
                "%d;%s;%s;%s;%s;%s",
                this.getCodPessoa(),
                this.getNome(),
                this.getEmail(),
                this.getSenha(),
                this.isAtivo() ? "true" : "false",
                this.getDataCadastro().toString());
    }
}