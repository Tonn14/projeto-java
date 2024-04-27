public class Reserva {
    String codigo;
    String nomeCliente;
    String numeroVoo;
    int assento;
    double valorPago;
    public double valorPassagem;

    Reserva(String codigo, String nomeCliente, String numeroVoo, int assento, double valorPago) {
        this.codigo = codigo;
        this.nomeCliente = nomeCliente;
        this.numeroVoo = numeroVoo;
        this.assento = assento;
        this.valorPago = valorPago;
    }
}
