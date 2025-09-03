package Data.DAO;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class EmailDAO {
    private static final String CAMINHO_ARQUIVO = "implementação/src/Data/File/email.txt";

    public static void salvar(String email) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(CAMINHO_ARQUIVO, true), "UTF-8"))) {

            escritor.write(email);
            escritor.newLine();
            escritor.flush();
        }
    }
}
