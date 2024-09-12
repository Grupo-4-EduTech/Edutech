import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        Usuario usuario = new Usuario("Erick", "123");

        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String YELLOW = "\u001B[33m";
        String BLUE =  "\u001B[34m";

        System.out.println(getDataHora()+ " - "+BLUE+"INFO"+RESET+": Iniciando a aplicação");

        System.out.print("Nome de usuário: ");
        String nome = in.next();
        System.out.print("Senha: ");
        String senha = in.next();

        Boolean usuarioExiste = autenticar(usuario, nome, senha);

        if (usuarioExiste) {
            System.out.println(getDataHora()+ " - "+BLUE+"INFO"+RESET+": Usuário "+nome+" entrou no sistema!");
            Thread.sleep(1000);
            System.out.println(getDataHora()+ " - "+YELLOW+"WARN"+RESET+": O usuário "+nome+" não tem permissão para acessar esta funcionalidade");
            Thread.sleep(1000);
            System.out.println(getDataHora()+ " - "+BLUE+"INFO"+RESET+": Usuário "+nome+" saiu do sistema!");
            Thread.sleep(1000);
            System.out.println(getDataHora()+ " - "+RED+"ERROR"+RESET+": Ocorreu um erro inesperado.");
            Thread.sleep(1000);
            System.out.println(getDataHora()+ " - "+RED+"ERROR"+RESET+": Erro de conexão não foi possível conectar ao servidor.");
            Thread.sleep(1000);
            System.out.println(getDataHora()+ " - "+RED+"ERROR"+RESET+": Falha ao conectar com o banco de dados.");
            Thread.sleep(1000);
            System.out.println(getDataHora()+ " - "+BLUE+"INFO"+RESET+": Fechando a aplicação.");
            Thread.sleep(1000);
            System.out.println(getDataHora()+ " - "+BLUE+"INFO"+RESET+": Reiniciando a aplicação.");
        } else {
            System.out.println(getDataHora()+ " - "+RED+"ERROR"+RESET+": Falha no Login, credenciais inválidas!");
        }
    }

    public static boolean autenticar(Usuario usuario, String inputNome, String inputSenha) {
        return usuario.nome.equals(inputNome) && usuario.senha.equals(inputSenha);
    }

    public static String getDataHora(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}