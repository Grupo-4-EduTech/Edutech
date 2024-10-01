public class Materia {
    private int idMateria;
    private String nome;

    public Materia(int idMateria, String nome) {
        this.idMateria = idMateria;
        this.nome = nome;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
