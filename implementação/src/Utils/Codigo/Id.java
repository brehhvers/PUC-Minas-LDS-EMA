package Utils.Codigo;

public class Id extends Codigo{
    public static int gerar() {
        return 10 + RANDOM.nextInt(90);
    }
}
