package Business.Pessoa;

import java.util.ArrayList;

import Business.PlanoDeEnsino;
import Enum.StatusPlano;

public class Aluno extends Usuario {
    private int matricula; // TODO:
    private ArrayList<PlanoDeEnsino> planosDeEnsino;

    public Aluno(String nome, String email, String senha) {
        super(nome, email, senha);
        this.planosDeEnsino = new ArrayList<>();
    }

    public int getMatricula() {
        return matricula;
    }

    public PlanoDeEnsino getPlanoAtivo() {
        if (this.planosDeEnsino.isEmpty())
            throw new RuntimeException("Não há nenhum plano de ensino criado até o momento.");

        return this.planosDeEnsino.get(this.planosDeEnsino.size() - 1);
    }

    public PlanoDeEnsino criarPlanoDeEnsino() {
        PlanoDeEnsino planoDeEnsino = new PlanoDeEnsino(this);

        this.planosDeEnsino.add(planoDeEnsino);
        return planoDeEnsino;
    }

    public boolean confirmarPlanoDeEnsino() {
        PlanoDeEnsino planoDeEnsino = this.getPlanoAtivo();

        if (StatusPlano.CONFIRMADO.equals(planoDeEnsino.getStatus())) {
            throw new RuntimeException("O plano de ensino atual já está confirmado.");
        }

        if (!StatusPlano.RASCUNHO.equals(planoDeEnsino.getStatus())) {
            throw new RuntimeException("Antes de confirmar, crie um novo plano de ensino.");
        }

        planoDeEnsino.setStatus(StatusPlano.CONFIRMADO);
        return true;
    }

    public PlanoDeEnsino cancelarPlanoAtivo() {
        PlanoDeEnsino planoDeEnsino = this.getPlanoAtivo();

        if (StatusPlano.EFETIVADO.equals(planoDeEnsino.getStatus())) {
            throw new RuntimeException("Não é possível cancelar um plano de ensino que já foi efetivado.");
        }

        planoDeEnsino.setStatus(StatusPlano.CANCELADO);
        this.planosDeEnsino.remove(planoDeEnsino);
        return planoDeEnsino;
    }

    @Override
    public String toString() {
        String string = String.format(
                "Código da Pessoa: %d%nMatrícula: %d%nNome: %s%nEmail: %s%nData de Cadastro: %s",
                this.getCodPessoa(),
                this.matricula,
                this.getNome(),
                this.getEmail(),
                this.getDataCadastro().format(this.formatter));

        return string;
    }
}