public class Prova {
    private int idProva;
    private String dtProva;
    private Double nota;
    private Integer bloco;
    private Materia materia;
    private Aluno aluno;
    private Escola escola;

    public Prova(int idProva, String dtProva, Double nota, Integer bloco, Materia materia, Aluno aluno, Escola escola) {
        this.idProva = idProva;
        this.dtProva = dtProva;
        this.nota = nota;
        this.bloco = bloco;
        this.materia = materia;
        this.aluno = aluno;
        this.escola = escola;
    }

    public int getIdProva() {
        return idProva;
    }

    public void setIdProva(int idProva) {
        this.idProva = idProva;
    }

    public String getDtProva() {
        return dtProva;
    }

    public void setDtProva(String dtProva) {
        this.dtProva = dtProva;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Integer getBloco() {
        return bloco;
    }

    public void setBloco(Integer bloco) {
        this.bloco = bloco;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }
}
