import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class Escola {
    private int idEscola;
    private String nome;
    private String logradouro;
    private Integer numLogradouro;
    private Diretoria diretoria;
    private Integer idRegiao;

    public Escola(){}

    public Escola(int idEscola, String nome, String logradouro, Integer numLogradouro, Diretoria diretoria, Integer idRegiao) {
        this.idEscola = idEscola;
        this.nome = nome;
        this.logradouro = logradouro;
        this.numLogradouro = numLogradouro;
        this.diretoria = diretoria;
        this.idRegiao = idRegiao;
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumLogradouro() {
        return numLogradouro;
    }

    public void setNumLogradouro(Integer numLogradouro) {
        this.numLogradouro = numLogradouro;
    }

    public Diretoria getDiretoria() {
        return diretoria;
    }

    public void setDiretoria(Diretoria diretoria) {
        this.diretoria = diretoria;
    }

    public Integer getIdRegiao() {
        return idRegiao;
    }

    public void setIdRegiao(Integer idRegiao) {
        this.idRegiao = idRegiao;
    }
}
