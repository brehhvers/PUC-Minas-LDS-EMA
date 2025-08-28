package Business.Pessoa;

import java.time.LocalDate;

public abstract class Usuario {

    private int codPessoa; // TODO:
    private String nome;
    private LocalDate dataCadastro;
    private boolean ativo;
    private String email;
    private String senha;

    public Usuario(
            String nome,
            LocalDate dataCadastro,
            boolean ativo,
            String email,
            String senha
    ) {
        this.nome = nome;
        this.dataCadastro = dataCadastro;
        this.ativo = ativo;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
