public class Diretoria {
    private int idDiretoria;
    private String nome;

    public Diretoria(int idDiretoria, String nome) {
        this.idDiretoria = idDiretoria;
        this.nome = nome;
    }

    public int getIdDiretoria() {
        return idDiretoria;
    }

    public void setIdDiretoria(int idDiretoria) {
        this.idDiretoria = idDiretoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
