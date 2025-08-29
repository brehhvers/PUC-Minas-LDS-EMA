package Interface;

public interface IGerenciavel<V> {
    public boolean addDisciplina(V identificador);
    public V removerDisciplina(V identificador);
}
