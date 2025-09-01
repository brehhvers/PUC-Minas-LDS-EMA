package Utils.Notificador;

public class NotificadorEmail extends Notificador<String, String> {
    private static final NotificadorEmail INSTANCIA = new NotificadorEmail();

    private NotificadorEmail() {}

    public static NotificadorEmail getNotificador() {
        return INSTANCIA;
    }

    @Override
    public void notificar(String mensagem, String email) {
        System.out.println("=== Enviando e-mail ===");
        System.out.println("Para: " + email);
        System.out.println("Mensagem: " + mensagem);
        System.out.println("=======================");
    }
}
