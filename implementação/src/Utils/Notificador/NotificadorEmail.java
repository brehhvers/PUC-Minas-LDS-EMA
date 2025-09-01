package Utils.Notificador;

import Interface.INotificavel;

public class NotificadorEmail implements INotificavel<String, String> {
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
