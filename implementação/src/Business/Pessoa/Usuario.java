package Business.Pessoa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Utils.Identificador.CodPessoa;

public abstract class Usuario {
    private int codPessoa;
    private String nome;
    private String email;
    private String senha;
    private boolean isAtivo;
    private LocalDate dataCadastro;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Usuario(String nome, String email, String senha) {
        this.codPessoa = CodPessoa.gerar();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAtivo = true;
        this.dataCadastro = LocalDate.now();
    }

    public int getCodPessoa() {
        return this.codPessoa;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAtivo() {
        return this.isAtivo;
    }

    public void setAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public abstract String toString();
    public abstract String toPersist();
}
