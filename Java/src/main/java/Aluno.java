public class Aluno {
    private String ra;
    private String nome;
    private String dtNasc;
    private String dtMatricula;
    private Escola escola;
    private Diretoria diretoria;

    public Aluno(String ra, String nome, String dtNasc, String dtMatricula, Escola escola, Diretoria diretoria) {
        this.ra = ra;
        this.nome = nome;
        this.dtNasc = dtNasc;
        this.dtMatricula = dtMatricula;
        this.escola = escola;
        this.diretoria = diretoria;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(String dtNasc) {
        this.dtNasc = dtNasc;
    }

    public String getDtMatricula() {
        return dtMatricula;
    }

    public void setDtMatricula(String dtMatricula) {
        this.dtMatricula = dtMatricula;
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
