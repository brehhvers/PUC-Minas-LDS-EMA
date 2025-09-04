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
import Data.DAO.ProfessorDAO;
import Data.DAO.SecretariaDAO;
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