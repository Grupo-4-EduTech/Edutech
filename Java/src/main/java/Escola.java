public class Escola {
    private int idEscola;
    private String nome;
    private String cep;
    private Integer numLog;
    private Diretoria diretoria;

    public Escola(int idEscola, String nome, String cep, Integer numLog, Diretoria diretoria) {
        this.idEscola = idEscola;
        this.nome = nome;
        this.cep = cep;
        this.numLog = numLog;
        this.diretoria = diretoria;
    }

    public int getIdEscola() {
        return idEscola;
    }

    public void setIdEscola(int idEscola) {
        this.idEscola = idEscola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumLog() {
        return numLog;
    }

    public void setNumLog(Integer numLog) {
        this.numLog = numLog;
    }

    public Diretoria getDiretoria() {
        return diretoria;
    }

    public void setDiretoria(Diretoria diretoria) {
        this.diretoria = diretoria;
    }
}
