package Utils;

public class CodPessoa extends Codigo {
    public static int gerar() {
        return 100000 + RANDOM.nextInt(900000);
    }
}
