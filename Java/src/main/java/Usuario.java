public class Usuario {

    private Integer idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String dtCadastro;
    private Cargo cargo;

    public Usuario(){};

    public Usuario(Integer idUsuario, String nome, String email, String senha, String telefone, String dtCadastro, Cargo cargo, Escola escola, Diretoria diretoria, Materia materia) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.dtCadastro = dtCadastro;
        this.cargo = cargo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(String dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    //    @Override
//    public String toString(){
//        return "";
//    }
}
