import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRowMapper implements RowMapper<Usuario> {
    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cargo cargo = new Cargo(rs.getInt("fkCargo"), rs.getString("cargo"));

        // Criar o objeto Escola
        Escola escola = new Escola(
                rs.getInt("idEscola"),
                rs.getString("nomeEscola"),
                rs.getString("cep"),
                rs.getInt("numLog"),
                null // Atribua a Diretoria mais tarde
        );

        // Criar o objeto Diretoria
        Diretoria diretoria = new Diretoria(
                rs.getInt("idDiretoria"),
                rs.getString("nomeDiretoria")
        );

        // Criar o objeto Materia
        Materia materia = new Materia(
                rs.getInt("idMateria"),
                rs.getString("nomeMateria")
        );

        // Criar o objeto Usuario e preencher os dados
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("idUsuario"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setTelefone(rs.getString("telefone"));
        usuario.setDtCadastro(rs.getString("dtCadastro"));
        usuario.setCargo(cargo);
        usuario.setEscola(escola);
        usuario.setDiretoria(diretoria); // Atribuindo o objeto Diretoria ao Usuario
        usuario.setMateria(materia); // Atribuindo o objeto Materia ao Usuario

        return usuario;
    }
}
