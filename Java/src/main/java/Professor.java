public class Professor extends Diretor{
    private Materia materia;

    public Professor() {
    }

    public Professor(Integer idUsuario, String nome, String email, String senha, String telefone, String dtCadastro, Cargo cargo, Escola escola, Diretoria diretoria, Materia materia, Escola escola1, Diretoria diretoria1, Materia materia1) {
        super(idUsuario, nome, email, senha, telefone, dtCadastro, cargo, escola, diretoria, materia, escola1, diretoria1);
        this.materia = materia1;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
}
