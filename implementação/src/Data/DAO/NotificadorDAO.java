package Data.DAO;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class NotificadorDAO {
    private String caminhoArquivo;

    public NotificadorDAO(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void salvar(String string) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(caminhoArquivo, true), "UTF-8"))) {

            escritor.write(string);
            escritor.newLine();
            escritor.flush();
        }
    }
}
