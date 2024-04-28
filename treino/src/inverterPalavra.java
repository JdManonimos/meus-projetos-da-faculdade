import java.io.IOException;
public class inverterPalavra {
        //String que inverte palavras
        public static String inverterPalavra(String palavra) {
            StringBuilder inverso = new StringBuilder();
            for (int i = palavra.length() - 1; i >= 0; i--) {
                //aqui eu adiciono caractere a StringBuilder
                inverso.append(palavra.charAt(i));
            }
            return inverso.toString();
        }

        public static void main(String[] args) {
            String palavra = "PAZ";
            String saida = inverterPalavra(palavra);
            System.out.println("palavra: " + palavra);
            System.out.println("palavra: " + saida);
        }

    }