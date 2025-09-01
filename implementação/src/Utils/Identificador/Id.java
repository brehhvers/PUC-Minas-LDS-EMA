package Utils.Identificador;

import java.util.Random;

public class Id {
    protected static final Random RANDOM = new Random();

    public static int gerar() {
        return 10 + RANDOM.nextInt(90);
    }
}
