public class Usuario {

    private Integer idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String dtCadastro;
    private Cargo cargo;
    private Escola escola;
    private Diretoria diretoria;
    private Materia materia;

    public Usuario(){};

    public Usuario(Integer idUsuario, String nome, String email, String senha, String telefone, String dtCadastro, Cargo cargo, Escola escola, Diretoria diretoria, Materia materia) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.dtCadastro = dtCadastro;
        this.cargo = cargo;
        this.escola = escola;
        this.diretoria = diretoria;
        this.materia = materia;
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

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    public Diretoria getDiretoria() {
        return diretoria;
    }

    public void setDiretoria(Diretoria diretoria) {
        this.diretoria = diretoria;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    //    @Override
//    public String toString(){
//        return "";
//    }
}
