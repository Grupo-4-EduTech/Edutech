public class Questao {
    private int idQuestao;
    private Integer bloco;
    private Integer numero;
    private String respostaCorreta;
    private String descritor;
    private Materia materia;

    public Questao(){}

    public Questao(int idQuestao, Integer bloco, Integer numero, String respostaCorreta, String descritor, Materia materia) {
        this.idQuestao = idQuestao;
        this.bloco = bloco;
        this.numero = numero;
        this.respostaCorreta = respostaCorreta;
        this.descritor = descritor;
        this.materia = materia;
    }

    public int getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(int idQuestao) {
        this.idQuestao = idQuestao;
    }

    public Integer getBloco() {
        return bloco;
    }

    public void setBloco(Integer bloco) {
        this.bloco = bloco;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getRespostaCorreta() {
        return respostaCorreta;
    }

    public void setRespostaCorreta(String respostaCorreta) {
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
}
