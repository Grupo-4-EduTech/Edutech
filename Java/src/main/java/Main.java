import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Connection dbConnection = new Connection();
        JdbcTemplate connection = dbConnection.getConnection();

        List<Usuario> listaUsuarios = connection.query("SELECT * FROM usuario", new BeanPropertyRowMapper<>(Usuario.class));

        System.out.println("Usuários:");
        for (Usuario usuario : listaUsuarios){
            System.out.println(usuario.getNome());
            System.out.println(usuario.getEmail());
            System.out.println(usuario.getTelefone());
        }
    }
}
