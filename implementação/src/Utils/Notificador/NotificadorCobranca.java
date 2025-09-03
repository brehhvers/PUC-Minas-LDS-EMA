package Utils.Notificador;

import Data.DAO.CobrancaDAO;
import Interface.INotificavel;

public class NotificadorCobranca implements INotificavel<String, Double> {
    private static final NotificadorCobranca INSTANCIA = new NotificadorCobranca();

    private NotificadorCobranca() {
    }

    public static NotificadorCobranca getNotificador() {
        return INSTANCIA;
    }

    @Override
    public void notificar(String descricao, Double valor) {
        String notificacao = String.format(
                "=== Notificação de Cobrança ===%nDescrição: %s%nValor: R$ %.2f%n===============================%n%n",
                descricao,
                valor);

        try {
            CobrancaDAO.getDAO().salvar(notificacao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
