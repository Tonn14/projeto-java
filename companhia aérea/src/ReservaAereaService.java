import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;


public interface ReservaAereaService {
    void cadastrarVoo(Scanner scanner, ArrayList<Voo> voos);
    void venderPassagens(Scanner scanner, ArrayList<Voo> voos, ArrayList<Reserva> reservas, Map<String, Integer> descontos);
    void cancelarPassagem(Scanner scanner, ArrayList<Reserva> reservas, Map<String, Integer> descontos, ArrayList<Voo> voos);
    void buscarPassagemPorCodigo(Scanner scanner, ArrayList<Reserva> reservas);
    void imprimirAssentosDisponiveis(Scanner scanner, ArrayList<Voo> voos);
    void imprimirEstatisticasDescontos(ArrayList<Reserva> reservas);
    void imprimirValorTotalArrecadado(ArrayList<Reserva> reservas);
  
}