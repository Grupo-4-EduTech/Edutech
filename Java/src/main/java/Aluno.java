public class Aluno {
    private String idAluno;
    private String nome;
    private Integer idade;
    private String sexo;
    private Double proficienciaMT;
    private Double proficienciaLP;
    private Integer nivel;
    private Turma turma;
    private Escola escola;
    private Diretoria diretoria;

    public Aluno(){}

    public Aluno(String idAluno, String nome, Integer idade, String sexo, Double proficienciaMT, Double proficienciaLP, Integer nivel, Turma turma, Escola escola, Diretoria diretoria) {
        this.idAluno = idAluno;
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.proficienciaMT = proficienciaMT;
        this.proficienciaLP = proficienciaLP;
        this.nivel = nivel;
        this.turma = turma;
        this.escola = escola;
        this.diretoria = diretoria;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Double getProficienciaMT() {
        return proficienciaMT;
    }

    public void setProficienciaMT(Double proficienciaMT) {
        this.proficienciaMT = proficienciaMT;
    }

    public Double getProficienciaLP() {
        return proficienciaLP;
    }

    public void setProficienciaLP(Double proficienciaLP) {
        this.proficienciaLP = proficienciaLP;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
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
