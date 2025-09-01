package Business.Pessoa;

import java.util.ArrayList;

import Business.Curriculo;
import Business.PlanoDeEnsino;
import Enum.StatusPlano;
import Utils.Identificador.Matricula;
import Utils.Notificador.NotificadorCobranca;

public class Aluno extends Usuario {
    private int matricula;
    private Curriculo curriculo;
    private ArrayList<PlanoDeEnsino> planosDeEnsino;

    public Aluno(String nome, String email, String senha) {
        super(nome, email, senha);
        this.matricula = Matricula.gerar();
        this.planosDeEnsino = new ArrayList<>();
    }

    public int getMatricula() {
        return this.matricula;
    }

    public Curriculo getCurriculo() {
        return this.curriculo;
    }

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }

    public PlanoDeEnsino getPlanoAtivo() {
        if (this.planosDeEnsino.isEmpty())
            throw new IllegalStateException("Não há nenhum plano de ensino criado até o momento.");

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
            throw new IllegalStateException("O plano de ensino atual já está confirmado.");
        }

        if (!StatusPlano.RASCUNHO.equals(planoDeEnsino.getStatus())) {
            throw new IllegalStateException("Antes de confirmar, crie um novo plano de ensino.");
        }

        planoDeEnsino.setStatus(StatusPlano.CONFIRMADO);
        planoDeEnsino.getDisciplinas().stream().forEach(d -> d.addAluno(this));
        return true;
    }

    public PlanoDeEnsino cancelarPlanoAtivo() {
        PlanoDeEnsino planoDeEnsino = this.getPlanoAtivo();

        if (!StatusPlano.CONFIRMADO.equals(planoDeEnsino.getStatus())) {
            throw new IllegalStateException("O plano de ensino não pode ser cancelado devido ao seu status atual: "
                    + planoDeEnsino.getStatus());
        }

        planoDeEnsino.setStatus(StatusPlano.CANCELADO);
        planoDeEnsino.getDisciplinas().stream().forEach(d -> d.removerAluno(this.matricula));

        NotificadorCobranca
                .getNotificador()
                .notificar("Plano de ensino " + planoDeEnsino.getId() + " cancelado",
                        planoDeEnsino.getValorTotal());

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