package Data.DAO;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Interface.IPersistivel;

public abstract class DAO<T> {
    private String caminhoArquivo;
    protected Map<Integer, T> cache = new HashMap<>();

    public DAO(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    protected abstract T parse(String linha);

    public void salvar(IPersistivel objeto) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(caminhoArquivo, false), "UTF-8"))) {

            escritor.write(objeto.toPersist());
            escritor.newLine();
            escritor.flush();
        }
    }

    public void salvarTodos(ArrayList<? extends IPersistivel> objetos) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(caminhoArquivo, false), "UTF-8"))) {

            for (IPersistivel obj : objetos) {
                escritor.write(obj.toPersist());
                escritor.newLine();
            }

            escritor.flush();
        }
    }

    public ArrayList<T> carregar() throws IOException {
        ArrayList<T> objetos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo, StandardCharsets.UTF_8))) {

            String linha;
            while ((linha = leitor.readLine()) != null) {
                objetos.add(this.parse(linha));
            }
        }

        return objetos;
    }

    public T carregarPorId(String id) throws IOException {
        int idConvertido = Integer.parseInt(id);
        if (cache.containsKey(idConvertido)) {
            return cache.get(idConvertido);
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo, StandardCharsets.UTF_8))) {

            String linha;
            while ((linha = leitor.readLine()) != null) {

                if (linha.startsWith(id + ";")) {
                    return this.parse(linha);
                }
            }
        }

        return null;
    }
}
