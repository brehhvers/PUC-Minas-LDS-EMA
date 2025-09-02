package Data;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public abstract class DAO<T> {
    private String caminhoArquivo;

    public DAO(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }
    
    protected abstract T parse(String linha);

    public void salvar(T objeto) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(caminhoArquivo, true), "UTF-8"))) {

            escritor.write(objeto.toString());
            escritor.newLine();
            escritor.flush();
        }
    }

    public ArrayList<T> carregar() throws IOException {
        ArrayList<T> objetos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo, StandardCharsets.UTF_8))) {

            String linha;
            while ((linha = leitor.readLine()) != null) {
                objetos.add(parse(linha));
            }
        }

        return objetos;
    }
}
