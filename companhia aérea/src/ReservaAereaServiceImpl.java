import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ReservaAereaServiceImpl implements ReservaAereaService {

    @Override
    public void cadastrarVoo(Scanner scanner, ArrayList<Voo> voos) {
        System.out.print("Número do voo: ");
        String numero = scanner.next();
        System.out.print("Origem: ");
        String origem = scanner.next();
        System.out.print("Destino: ");
        String destino = scanner.next();
        System.out.print("Quantidade de assentos disponíveis: ");
        int assentosDisponiveis = scanner.nextInt();
        System.out.print("Valor da passagem: ");
        double valorPassagem = scanner.nextDouble();

        Voo voo = new Voo(numero, origem, destino, assentosDisponiveis, valorPassagem);
        voos.add(voo);

        System.out.println("Voo cadastrado com sucesso!");
    }

    @Override
    public void venderPassagens(Scanner scanner, ArrayList<Voo> voos, ArrayList<Reserva> reservas, Map<String, Integer> descontos) {
        System.out.print("Digite o número do voo: ");
        String numeroVoo = scanner.next();
        
        // Utiliza o método encontrarVooPorNumero para obter o voo correto
        Voo voo = encontrarVooPorNumero(voos, numeroVoo);
    
        if (voo == null) {
            System.out.println("Voo não encontrado.");
            return;
        }
    
        System.out.print("Código da reserva: ");
        String codigoReserva = scanner.next();
        System.out.print("Nome do cliente: ");
        String nomeCliente = scanner.next();
        System.out.print("Número do assento: ");
        int assento = scanner.nextInt();
    
        // Corrige o cálculo do valor da passagem com desconto
        double valorPassagem = calcularValorPassagemComDesconto(voo, reservas, descontos);
    
        Reserva reserva = new Reserva(codigoReserva, nomeCliente, numeroVoo, assento, valorPassagem);
        reservas.add(reserva);
        voo.assentosDisponiveis--;
    
        System.out.println("Reserva realizada com sucesso!");
    }

    @Override
    public void cancelarPassagem(Scanner scanner, ArrayList<Reserva> reservas, Map<String, Integer> descontos, ArrayList<Voo> voos) {
        System.out.print("Digite o código da reserva a ser cancelada: ");
        String codigoReserva = scanner.next();
        Reserva reserva = encontrarReservaPorCodigo(reservas, codigoReserva);
    
        if (reserva == null) {
            System.out.println("Reserva não encontrada.");
            return;
        }
    
        // Repassar desconto para a próxima posição
        int indexReserva = reservas.indexOf(reserva);
        if (indexReserva < reservas.size() - 1) {
            Reserva proximaReserva = reservas.get(indexReserva + 1);
            Voo proximoVoo = encontrarVooPorNumero(voos, proximaReserva.numeroVoo);
            proximaReserva.valorPago = calcularValorPassagemComDesconto(proximoVoo, reservas, descontos);
        }
    
        // Liberar assento para nova venda
        Voo voo = encontrarVooPorNumero(voos, reserva.numeroVoo);
        if (voo != null) {
            voo.assentosDisponiveis++;
        }
    
        reservas.remove(reserva);
        System.out.println("Reserva cancelada com sucesso!");
    }

    @Override
    public void buscarPassagemPorCodigo(Scanner scanner, ArrayList<Reserva> reservas) {
        System.out.print("Digite o código da reserva: ");
        String codigoReserva = scanner.next();
        Reserva reserva = encontrarReservaPorCodigo(reservas, codigoReserva);

        if (reserva == null) {
            System.out.println("Reserva não encontrada.");
            return;
        }

        System.out.println("Detalhes da reserva:");
        System.out.println("Código da reserva: " + reserva.codigo);
        System.out.println("Número do voo: " + reserva.numeroVoo);
        System.out.println("Nome do cliente: " + reserva.nomeCliente);
        System.out.println("Número do assento: " + reserva.assento);
        System.out.println("Valor da passagem: " + reserva.valorPago);
    }

    @Override
    public void imprimirAssentosDisponiveis(Scanner scanner, ArrayList<Voo> voos) {
        System.out.print("Digite o número do voo: ");
        String numeroVoo = scanner.next();
        Voo voo = encontrarVooPorNumero(voos, numeroVoo);

        if (voo == null) {
            System.out.println("Voo não encontrado.");
            return;
        }

        System.out.println("Assentos disponíveis no voo " + numeroVoo + ": " + voo.assentosDisponiveis);
    }

    @Override
    public void imprimirEstatisticasDescontos(ArrayList<Reserva> reservas) {
        double epsilon = 0.001; // Margem de erro para comparações de ponto flutuante
        int count25 = 0;
        int count15 = 0;
        int count5 = 0;
        int countIntegral = 0;
    
        for (Reserva reserva : reservas) {
            double desconto25 = reserva.valorPassagem * 0.75;
            double desconto15 = reserva.valorPassagem * 0.85;
            double desconto5 = reserva.valorPassagem * 0.95;
    
            if (Math.abs(reserva.valorPago - desconto25) < epsilon) {
                count25++;
            } else if (Math.abs(reserva.valorPago - desconto15) < epsilon) {
                count15++;
            } else if (Math.abs(reserva.valorPago - desconto5) < epsilon) {
                count5++;
            } else {
                countIntegral++;
            }
        }
    
        System.out.println("Quantidade de reservas com 25% de desconto: " + count25);
        System.out.println("Quantidade de reservas com 15% de desconto: " + count15);
        System.out.println("Quantidade de reservas com 5% de desconto: " + count5);
        System.out.println("Quantidade de reservas com valor integral: " + countIntegral);
    }

    @Override
    public void imprimirValorTotalArrecadado(ArrayList<Reserva> reservas) {
        double valorTotalArrecadado = 0;

        for (Reserva reserva : reservas) {
            valorTotalArrecadado += reserva.valorPago;
        }

        System.out.println("Valor total arrecadado: " + valorTotalArrecadado);
    }

    private static double calcularValorPassagemComDesconto(Voo voo, ArrayList<Reserva> reservas, Map<String, Integer> descontos) {
        int totalReservas = reservas.size();
        
        for (Map.Entry<String, Integer> entry : descontos.entrySet()) {
            if (totalReservas < entry.getValue()) {
                double desconto = Double.parseDouble(entry.getKey().replace("%", ""));
                return voo.valorPassagem * (1 - desconto / 100.0);
            }
        }
    
        return voo.valorPassagem; // Valor integral se ultrapassar a última faixa de desconto
    }

    private static Reserva encontrarReservaPorCodigo(ArrayList<Reserva> reservas, String codigoReserva) {
        for (Reserva reserva : reservas) {
            if (reserva.codigo.equals(codigoReserva)) {
                return reserva;
            }
        }
        return null;
    }

    private static Voo encontrarVooPorNumero(ArrayList<Voo> voos, String numeroVoo) {
        for (Voo voo : voos) {
            if (voo.numero.equals(numeroVoo)) {
                return voo;
            }
        }
        return null;
    }

    

    
}
