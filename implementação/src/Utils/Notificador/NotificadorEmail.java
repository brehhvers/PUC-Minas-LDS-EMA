package Utils.Notificador;

public class NotificadorEmail extends Notificador<String, String> {
    public static void notificar(String mensagem, String email) {
        System.out.println("=== Enviando e-mail ===");
        System.out.println("Para: " + email);
        System.out.println("Mensagem: " + mensagem);
        System.out.println("=======================");
    }
}
