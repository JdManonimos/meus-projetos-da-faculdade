/*trabalo com tema 1,
                4 participaram!
 * 1   Mateus felipe dos reis        (ADS, RA:202411335)
 * 2   Jonathan Moreira Barreiros    (CDC, RA:324142131)
 * 3   Marizan tomaz da silva junior (ADS, RA:202412318)
 * 4   Brenda tais Silva  Reis       (ADS, RS:202411500)
 *  */

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import connection.ConexaoMySQL;
import java.text.SimpleDateFormat;
public class Main {

    //Método que reinicia sua conexão
    private static final String USUARIO = "root"; // Coloque seu nome de usuário do MySQL
    private static final String SENHA = ""; // Coloque sua senha do MySQL

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("=== Sistema de Gerenciamento de Resíduos ===");
            System.out.println("1. Registrar novo cadastro");
            System.out.println("2. Registrar processo de separação de materiais recicláveis");
            System.out.println("3. Editar cadastro existente");
            System.out.println("4. Visualizar informações sobre pontos de coleta");
            System.out.println("5. gerar relatorio pela media e 3 anos");
            System.out.println("6. remover cadastro existente");
            System.out.println("7. buscarInformacoes por pessoas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    registrarNovoVoluntario();
                    break;
                case 2:
                    registrarSeparacao();
                    break;
                case 3:
                    editarCadastro();
                    break;
                case 4:
                      visualizarPontosColeta();
                    break;
                case 5:
                     gerarRelatoriosEstatisticas();
                    break;
                case 6:
                    removerCadastro();
                    break;
                case 7:
                    buscarInformacoesPorId();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    public static void buscarInformacoesPorId() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o ID do usuário que deseja buscar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer de entrada

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cadastro", USUARIO, SENHA)) {
            String sql = "SELECT * FROM reciclaveis WHERE pessoa_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        java.sql.Date data = rs.getDate("dataHoraFormatada");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        String dataFormatada = sdf.format(data);
                        String quantidade = rs.getString("quantidade");
                        double quantidadeFormatada = Double.parseDouble(quantidade);
                        // Exibir os detalhes do registro
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("Tipo Material: " + rs.getString("tipoMaterial"));
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("Quantidade reciclada: " + quantidadeFormatada + "kg");
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("Local de descarte: " + rs.getString("local1"));
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("Data: " + dataFormatada);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
        }

    }

    public static void removerCadastro() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o ID do voluntário que deseja remover: ");
        int id = scanner.nextInt();
        try (Connection conn = ConexaoMySQL.getInstance()) {
            String sql = "DELETE FROM pessoas WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Voluntário removido com sucesso!");
                } else {
                    System.out.println("Não foi possível encontrar o voluntário com o ID informado.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
        }
    }

    public static void editarCadastro() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o ID do voluntário que deseja editar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer de entrada

        // Solicitar novos valores para os campos
        System.out.print("Novo nome: ");
        String novoNome = scanner.nextLine();
        System.out.print("Novo email: ");
        String novoEmail = scanner.nextLine();


        // Atualizar os dados no banco de dados
        try (Connection conn = ConexaoMySQL.getInstance()) {
            String sql = "UPDATE pessoas SET nome = ?, email = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, novoNome);
                stmt.setString(2, novoEmail);
                stmt.setInt(3, id);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Cadastro atualizado com sucesso!");
                } else {
                    System.out.println("Não foi possível encontrar o cadastro com o ID informado.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
        }
    }
    public static void visualizarPontosColeta() {
        //logica para pontos de coleta
        System.out.println("Visualizando informações sobre pontos de coleta");
        System.out.println("--------------------------------------------------");
        System.out.println("Luizote de Freitas | Rua Wilson Gonçalves de Souza, 10");
        System.out.println("--------------------------------------------------");
        System.out.println("São Jorge | Avenida Serra do Mar, 411");
        System.out.println("--------------------------------------------------");
        System.out.println("Morumbi   | Rua Mangaba, esquina com as ruas Ingá e Camaleão");
        System.out.println("--------------------------------------------------");
        System.out.println("São Lucas | Rua do Cientista, esquina com rua do Gari ");
        System.out.println("--------------------------------------------------");
        System.out.println("Tocantins | Rua Docelino de Freitas Costa, esquina com rua Bernadete Silva Arantes ");
        System.out.println("--------------------------------------------------");
        System.out.println("Cruzeiro do Sul | Rua Sudoeste, esquina com a rua Pedro Quirino da Silva");
        System.out.println("--------------------------------------------------");
        System.out.println("Mansour | Rua Rio Corumbá, 20");
        System.out.println("--------------------------------------------------");
        System.out.println("Canaã   | esquina com a rua Menfins e Biblios");
        System.out.println("--------------------------------------------------");
        System.out.println("Parque do Sabiá | Avenida Anselmo Alves dos Santos, 925,Santa Mônica ");
        System.out.println("--------------------------------------------------");
        System.out.println("Centro (Centro de Reservação Cruzeiro dos Peixotos) | Rua Cruzeiro dos Peixotos, 544 ");
        System.out.println("--------------------------------------------------");
    }
    private static void gerarRelatoriosEstatisticas() {
        System.out.println("     Tabela de residuos reciclaveis               ");
        System.out.println("ANOS | Janeiro | Fvereiro | Março  | Aabril  |  Maio  |  junho |  julho | Agosto | Setembro | Outubro | Novembro | Dzembro |   TOTAL |");
        System.out.println("2022 | 13.602  | 55.046,0 | 44.170 |  43.390 | 52.080 | 58.270 | 67.060 | 57.908 |  78.909  |  57.980 | 47.090   |  66.575 | 572,652 |");
        System.out.println("2023 | 12.606  | 47.046,0 | 54.170 |  87.390 | 94.080 | 19.270 | 38.060 | 50.908 |  78.909  |  57.980 | 45.090   |  38.575 | 579,652 |");
        System.out.println("2024 | 449.609 |     0    |     0  |     0   |     0  |     0  |     0  |     0  |     0    |     0   |     0    |     0   | 449.609 |");
    }
    public static void registrarSeparacao() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o ID do voluntário : ");
        int pessoa_id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Registro de Processo de Separação de Materiais Recicláveis");
        System.out.print("Tipo de material reciclável: ");
        String tipoMaterial = scanner.nextLine();
        System.out.print("Quantidade (em kg, unidades, etc.): ");
        double quantidade = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer de entrada

        // Obter a data e hora atual
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy/MM/dd");
        String dataHoraFormatada = dataHoraAtual.format(formatter);
        System.out.print("Local de separação: ");
        String local1 = scanner.nextLine();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoMySQL.getInstance();
            String sql = "INSERT INTO cadastro.reciclaveis (tipoMaterial, quantidade, local1, dataHoraFormatada, pessoa_id) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, tipoMaterial);
            stmt.setString(2, String.valueOf(quantidade));
            stmt.setString(3, local1);
            stmt.setString(4, dataHoraFormatada);
            stmt.setInt(5, pessoa_id);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // Recuperar o ID gerado
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int novoId = rs.getInt(1);
                }
                System.out.println("registro criado apartir do id: "+pessoa_id);
            } else {
                System.out.println("Falha ao registrar reciclaveis verificar os dados!");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
        } finally {
            // Fechar ResultSet, PreparedStatement e Connection
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar recursos: " + ex.getMessage());
            }
        }
        System.out.println("Processo de separação registrado com sucesso!");
        System.out.println("Tipo de material: " + tipoMaterial);
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Data e hora: " + dataHoraFormatada);
        System.out.println("Local: " + local1);
        System.out.println("---------------------------------------------");
    }


    public static void registrarNovoVoluntario() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do voluntário: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o email do voluntário: ");
        String email = scanner.nextLine();
        System.out.print("Digite o telefone do voluntário: ");
        scanner.nextLine();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexaoMySQL.getInstance();
            String sql = "INSERT INTO pessoas (nome, email) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // Recuperar o ID gerado
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int novoId = rs.getInt(1);
                    System.out.println("Novo cadastro criado com o ID: " + novoId);
                }
                System.out.println("Voluntário registrado com sucesso!");
            } else {
                System.out.println("Falha ao registrar voluntário.");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
        }
    }
}