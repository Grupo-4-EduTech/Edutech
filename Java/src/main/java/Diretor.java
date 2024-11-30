public class Diretor extends Usuario{
    private Escola escola;
    private Diretoria diretoria;

    public Diretor() {
    }

    public Diretor(Integer idUsuario, String nome, String email, String senha, String telefone, String dtCadastro, Cargo cargo, Escola escola, Diretoria diretoria, Materia materia, Escola escola1, Diretoria diretoria1) {
        super(idUsuario, nome, email, senha, telefone, dtCadastro, cargo, escola, diretoria, materia);
        this.escola = escola1;
        this.diretoria = diretoria1;
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
}
