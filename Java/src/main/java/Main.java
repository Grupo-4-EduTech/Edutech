import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection dbConnection = new Connection();
        JdbcTemplate connection = dbConnection.getConnection();

        String emailUsuario = login();

        List<Usuario> listaUsuarioCargo = connection.query(
                "SELECT u.idUsuario, u.nome, u.email, u.senha, u.telefone, u.dtCadastro, " +
                        "c.idCargo AS fkCargo, c.descricao AS cargo, " +
                        "e.idEscola, e.nome AS nomeEscola, e.cep, e.numLog, " +
                        "d.idDiretoria, d.nome AS nomeDiretoria, " +
                        "m.idMateria, m.nome AS nomeMateria " +
                        "FROM usuario AS u " +
                        "JOIN cargo AS c ON u.fkCargo = c.idCargo " +
                        "LEFT JOIN escola AS e ON u.fkEscola = e.idEscola " +
                        "LEFT JOIN diretoria AS d ON u.fkDiretoria = d.idDiretoria " +
                        "LEFT JOIN materia AS m ON u.fkMateria = m.idMateria " +
                        "WHERE u.email = ?",
                new UsuarioRowMapper(), emailUsuario
        );

        /*
            MENU
            • 1. Diretores
               1.1. Visualizar diretores
               1.2. Cadastrar diretores
            • 2.


        * */

        for (Usuario usuario : listaUsuarioCargo) {
            if(usuario.getCargo().getDescricao().equals("Ministerio")){
                System.out.println("Ministerio");
            }
            else if(usuario.getCargo().getDescricao().equals("Diretor")){
                System.out.println("Diretor");
            }
            else{
                System.out.println("Professor");
            }
        }


        List<Usuario> listaUsuarios = connection.query(
                "SELECT u.idUsuario, u.nome, u.email, u.senha, u.telefone, u.dtCadastro, " +
                        "c.idCargo AS fkCargo, c.descricao AS cargo, " +
                        "e.idEscola, e.nome AS nomeEscola, e.cep, e.numLog, " +
                        "d.idDiretoria, d.nome AS nomeDiretoria, " +
                        "m.idMateria, m.nome AS nomeMateria " +
                        "FROM usuario AS u " +
                        "JOIN cargo AS c ON u.fkCargo = c.idCargo " +
                        "LEFT JOIN escola AS e ON u.fkEscola = e.idEscola " +
                        "LEFT JOIN diretoria AS d ON u.fkDiretoria = d.idDiretoria " +
                        "LEFT JOIN materia AS m ON u.fkMateria = m.idMateria",
                new UsuarioRowMapper()
        );



       /* System.out.println("Usuários:");
        for (Usuario usuario : listaUsuarios) {
            System.out.println("ID: " + usuario.getIdUsuario());
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("Email: " + usuario.getEmail());
            // System.out.println("Telefone: " + usuario.getTelefone());
            // System.out.println("Data de Cadastro: " + usuario.getDtCadastro());
            System.out.println("Cargo: " + usuario.getCargo().getDescricao());


            // Informações da Escola
            if (usuario.getEscola().getNome() != null) {
                System.out.println("Escola: " + usuario.getEscola().getNome());
                // System.out.println("CEP: " + usuario.getEscola().getCep());
                // System.out.println("Número: " + usuario.getEscola().getNumLog());
            } else {
                System.out.println("Escola: Não disponível");
            }

            // Informações da Diretoria
            *//*if (usuario.getDiretoria() != null) {
                System.out.println("Diretoria: " + usuario.getDiretoria().getNome());
            } else {
                System.out.println("Diretoria: Não disponível");
            }*//*

            // Informações da Matéria
            if (usuario.getMateria().getNome() != null) {
                System.out.println("Matéria: " + usuario.getMateria().getNome());
            } else {
                System.out.println("Matéria: Não disponível");
            }

            System.out.println("--------------------------");*/
        //}
    }

    public static String login(){
        Connection dbConnection = new Connection();
        JdbcTemplate connection = dbConnection.getConnection();

        // SIMULAÇÃO LOGIN
        List<Usuario> listaUsuariosLogin = connection.query(
                "SELECT email, senha FROM usuario", new BeanPropertyRowMapper<>(Usuario.class)
        );

        Boolean usuarioValido = false;
        do{
            Scanner sc = new Scanner(System.in);
            System.out.println(" ================ LOGIN ================ ");
            System.out.println("• Email");
            String emailUsuarioLogin = sc.nextLine();
            System.out.println("• Senha");
            String senhaUsuarioLogin = sc.nextLine();

            for(Usuario usuario : listaUsuariosLogin){
                if(emailUsuarioLogin.equals(usuario.getEmail()) && senhaUsuarioLogin.equals(usuario.getSenha())){
                    usuarioValido = true;
                }
            }

            if(usuarioValido){
                System.out.println("\nLOGIN REALIZADO COM SUCESSO!");
                return emailUsuarioLogin;
            } else{
                System.out.println("\nUSUÁRIO INVÁLIDO - EMAIL OU SENHA ESTÃO INCORRETOS\n");
            }

        }while(!usuarioValido);

        return "";
    }
}
