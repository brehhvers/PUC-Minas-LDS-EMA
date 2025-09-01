package Utils.Notificador;

import Interface.INotificavel;

public class NotificadorCobranca implements INotificavel<String, Double> {
    private static final NotificadorCobranca INSTANCIA = new NotificadorCobranca();

    private NotificadorCobranca() {}

    public static NotificadorCobranca getNotificador() {
        return INSTANCIA;
    }

    @Override
    public void notificar(String descricao, Double valor) {
        System.out.println("=== Notificação de Cobrança ===");
        System.out.println("Descrição: " + descricao);
        System.out.println("Valor: R$ " + String.format("%.2f", valor));
        System.out.println("===============================");
    }
}
