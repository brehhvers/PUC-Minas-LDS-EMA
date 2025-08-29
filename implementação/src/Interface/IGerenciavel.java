package Interface;

public interface IGerenciavel<V, I> {
    public boolean addDisciplina(V identificador);
    public V removerDisciplina(I identificador);
}
