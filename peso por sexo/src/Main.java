import java.util.Scanner;
import java.util.Locale;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Locale.setDefault(Locale.US);
        System.out.println("sexo:");
        String Sexo = scanner.next();
        System.out.println("Altura:");
        double Altura = scanner.nextDouble();
        double Homem = 72.7*Altura;
        double CalculoH = Homem - 58;
        double Mulher = 62.1*Altura;
        double CalculoM = Mulher - 44.7;
        if (Sexo.equals("homem")) {
            System.out.println("peso recomendada:" +CalculoH);
        }
      if (Mulher == Mulher) {
          System.out.println("peso recomendada:" +CalculoM);
      }
    }
}