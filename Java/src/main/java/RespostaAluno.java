import java.util.List;

public class RespostaAluno {
    private int idRespostaAluno;
    private List<String> resposta;
    private Aluno aluno;
    private Questao questao;

    public RespostaAluno(){}

    public RespostaAluno(int idRespostaAluno, List<String> resposta, Aluno aluno, Questao questao) {
        this.idRespostaAluno = idRespostaAluno;
        this.resposta = resposta;
        this.aluno = aluno;
        this.questao = questao;
    }

    public int getIdRespostaAluno() {
        return idRespostaAluno;
    }

    public void setIdRespostaAluno(int idRespostaAluno) {
        this.idRespostaAluno = idRespostaAluno;
    }

    public List<String> getResposta() {
        return resposta;
    }

    public void setResposta(List<String> resposta) {
        this.resposta = resposta;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }
}
