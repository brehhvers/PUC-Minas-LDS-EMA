package Business;

import java.util.ArrayList;
import java.util.List;

public class Curriculo {

    private int id;
    private final List<String> disciplinas;

    public Curriculo() {
        disciplinas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

//    public boolean adicionarDisciplina(String nomeDisciplina){}

    public String removerDisciplina(String nomeDisciplina){
        return this.disciplinas.remove(nomeDisciplina) ? nomeDisciplina : null;
    }
}
