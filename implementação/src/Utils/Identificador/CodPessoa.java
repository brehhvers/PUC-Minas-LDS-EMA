package Utils.Identificador;

public class CodPessoa extends Id {
    public static int gerar() {
        return 100000 + RANDOM.nextInt(900000);
    }
}
