package View;

public class MenuUtils {
    
    public static void imprimirMenu(String header, String[] opcoes) {
        System.out.println(gerarHeaderMenu(header));
        for (String opc : opcoes) {
            System.out.println(preencherLinha(opc));
        }
        System.out.print(solicitarInput());
    }

    public static String gerarHeaderMenu(String header) {
        StringBuilder linha = new StringBuilder();
        final int MAX_CARACTERES = 45;
        int blanks = MAX_CARACTERES - (header.length() + 6);

        linha.append("#".repeat(MAX_CARACTERES))
                .append("\n");

        linha.append("###");
        linha.append(" ".repeat(Math.max(0, blanks / 2)));
        linha.append(header);
        linha.append(" ".repeat(Math.max(0, (blanks / 2 + blanks % 2))));
        linha.append("###")
                .append("\n");

        linha.append("#".repeat(MAX_CARACTERES));
        return linha.toString();
    }

    public static String preencherLinha(String texto) {
        StringBuilder linha = new StringBuilder();

        final int MAX_CARACTERES = 45;
        int blanks = MAX_CARACTERES - (texto.length() + 2);

        linha.append("#");
        linha.append(" ".repeat(Math.max(0, blanks / 2)));
        linha.append(texto);
        linha.append(" ".repeat(Math.max(0, (blanks / 2 + blanks % 2))));
        linha.append("#");

        return linha.toString();
    }

    public static String solicitarInput() {
        String texto = "Digite a opção desejada:";
        StringBuilder linha = new StringBuilder();

        final int MAX_CARACTERES = 45;
        int hashtags = MAX_CARACTERES - (texto.length() + 3);

        linha.append("#".repeat(MAX_CARACTERES))
                .append("\n");
        linha.append("#".repeat(hashtags))
                .append(" ");
        linha.append(texto)
                .append(" ");
        return linha.toString();
    }
}
