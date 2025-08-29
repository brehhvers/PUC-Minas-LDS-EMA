package Utils;

public class Matricula extends Codigo {
    public static int gerar() {
        return 1000000 + RANDOM.nextInt(9000000);
    }
}
