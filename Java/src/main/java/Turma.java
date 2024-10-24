public class Turma {
    private int idTurma;
    private Integer serie;
    private Escola escola;
    private Diretoria diretoria;

    public Turma(){}

    public Turma(int idTurma, Integer serie, Escola escola, Diretoria diretoria) {
        this.idTurma = idTurma;
        this.serie = serie;
        this.escola = escola;
        this.diretoria = diretoria;
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
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
