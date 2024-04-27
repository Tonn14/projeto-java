import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SistemaReservaAerea {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Voo> voos = new ArrayList<>();
        ArrayList<Reserva> reservas = new ArrayList<>();
        Map<String, Integer> descontos = new HashMap<>();

        descontos.put("25%", 10);
        descontos.put("15%", 20);
        descontos.put("5%", 30);

        ReservaAereaService reservaAereaService = new ReservaAereaServiceImpl();


        int opcao;
        do {
            // ... (chame os métodos da interface)
            System.out.println("1. Cadastrar vôo");
            System.out.println("2. Vender passagens");
            System.out.println("3. Cancelar uma passagem");
            System.out.println("4. Finalizar cadastro de reservas");
            System.out.print("Escolha uma opção (-1 para sair): ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    reservaAereaService.cadastrarVoo(scanner, voos);
                    break;
                case 2:
                    reservaAereaService.venderPassagens(scanner, voos, reservas, descontos);
                    break;
                case 3:
                    reservaAereaService.cancelarPassagem(scanner, reservas, descontos, voos);
                    break;
                case 4:
                    // Finalizar o cadastro de reservas
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != -1);

        // Menu após finalizar o cadastro
        int opcaoMenuFinal;
        do {
            // ... (chame os métodos da interface)
            System.out.println("1. Buscar passagem pelo código da reserva");
            System.out.println("2. Imprimir assentos disponíveis no voo");
            System.out.println("3. Imprimir estatísticas de descontos");
            System.out.println("4. Imprimir valor total arrecadado");
            System.out.print("Escolha uma opção (-1 para sair): ");
            opcaoMenuFinal = scanner.nextInt();

            switch (opcaoMenuFinal) {
                case 1:
                    reservaAereaService.buscarPassagemPorCodigo(scanner, reservas);
                    break;
                case 2:
                    reservaAereaService.imprimirAssentosDisponiveis(scanner, voos);
                    break;
                case 3:
                    reservaAereaService.imprimirEstatisticasDescontos(reservas);
                    break;
                case 4:
                    reservaAereaService.imprimirValorTotalArrecadado(reservas);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcaoMenuFinal != -1);
    }
}
