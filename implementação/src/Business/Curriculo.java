package Business;

import java.util.ArrayList;

public class Curriculo {

    private int id; //TODO
    private final ArrayList<String> disciplinas;

    public Curriculo() {
        disciplinas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

//    public boolean addDisciplina(String nomeDisciplina){}

    public String removerDisciplina(String nomeDisciplina){
        return this.disciplinas.remove(nomeDisciplina) ? nomeDisciplina : null;
    }
}