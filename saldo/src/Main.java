import java.util.Locale;
import java.util.Scanner;
public class Main {
    public static void main(String args[]) {
        Locale.setDefault(Locale.US);
        Scanner leia = new Scanner (System.in);
        System.out.println("digite o saldo da aplicaçao atual:");
        double saldo = leia.nextDouble();
        double reajuste = saldo * 0.1;
        double novoSaldo = saldo + reajuste;
        System.out.println("novo saldo e de:" +novoSaldo);



    }

}