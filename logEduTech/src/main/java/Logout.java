public class Logout {
    public void logout(Usuario usuario) {
        usuario.nome = null;
        usuario.senha = null;
    }
}