public class Questao {
    private int idQuestao;
    private Integer numero;
    private Double valor;
    private Character respostaCorreta;
    private String descritor;
    private Materia materia;
    private Prova prova;
    private Aluno aluno;

    public Questao(int idQuestao, Integer numero, Double valor, Character respostaCorreta, String descritor, Materia materia, Prova prova, Aluno aluno) {
        this.idQuestao = idQuestao;
        this.numero = numero;
        this.valor = valor;
        this.respostaCorreta = respostaCorreta;
        this.descritor = descritor;
        this.materia = materia;
        this.prova = prova;
        this.aluno = aluno;
    }

    public int getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(int idQuestao) {
        this.idQuestao = idQuestao;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Character getRespostaCorreta() {
        return respostaCorreta;
    }

    public void setRespostaCorreta(Character respostaCorreta) {
        this.respostaCorreta = respostaCorreta;
    }

    public String getDescritor() {
        return descritor;
    }

    public void setDescritor(String descritor) {
        this.descritor = descritor;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Prova getProva() {
        return prova;
    }

    public void setProva(Prova prova) {
        this.prova = prova;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}
