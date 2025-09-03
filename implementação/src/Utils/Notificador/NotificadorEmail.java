package Utils.Notificador;

import Data.DAO.EmailDAO;
import Interface.INotificavel;

public class NotificadorEmail implements INotificavel<String, String> {
    private static final NotificadorEmail INSTANCIA = new NotificadorEmail();

    private NotificadorEmail() {
    }

    public static NotificadorEmail getNotificador() {
        return INSTANCIA;
    }

    @Override
    public void notificar(String mensagem, String email) {
        String notificacao = String.format(
                "=== Enviando e-mail ===%nPara: %s%nMensagem: %s%n=======================%n%n",
                email,
                mensagem);

        try {
            EmailDAO.getDAO().salvar(notificacao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
