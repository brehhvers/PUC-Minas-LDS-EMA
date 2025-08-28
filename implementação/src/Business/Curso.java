package Business;

public class Curso {

    private int id; // TODO:
    private String nome;
    private String numCreditos;
    public  Curso(String nome, String numCreditos) {
        this.nome = nome;
        this.numCreditos = numCreditos;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumCreditos() {
        return numCreditos;
    }

    public void setNumCreditos(String numCreditos) {
        this.numCreditos = numCreditos;
    }
}
