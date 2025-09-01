package Utils.Identificador;

public class Matricula extends Id {
    public static int gerar() {
        return 1000000 + RANDOM.nextInt(9000000);
    }
}
