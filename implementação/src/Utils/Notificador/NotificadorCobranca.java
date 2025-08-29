package Utils.Notificador;

public class NotificadorCobranca extends Notificador<String, Double> {
    public static void notificar(String descricao, Double valor) {
        System.out.println("=== Notificação de Cobrança ===");
        System.out.println("Descrição: " + descricao);
        System.out.println("Valor: R$ " + String.format("%.2f", valor));
        System.out.println("===============================");
    }
}
