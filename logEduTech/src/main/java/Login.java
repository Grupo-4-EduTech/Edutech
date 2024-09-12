public class Login {
    public boolean autenticar(Usuario usuario, String inputNome, String inputSenha) {
        if (usuario.nome.equals(inputNome) && usuario.senha.equals(inputSenha)) {
            return true;
        }
        return false;
    }
}