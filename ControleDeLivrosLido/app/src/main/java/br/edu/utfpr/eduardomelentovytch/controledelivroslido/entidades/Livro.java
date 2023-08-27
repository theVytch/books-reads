package br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades;

import java.io.Serializable;

public class Livro implements Serializable {

    private int id;
    private String nomeLivro;
    private String nomeAutor;
    private String tipoDeLivro;
    private Boolean livroJaFoiLido;
    private String categoriaLivro;
    private Comentario comentario;

    public Livro(){}

    public Livro(String nomeLivro, String nomeAutor, String tipoDeLivro, Boolean livroJaFoiLido, String categoriaLivro) {
        this.nomeLivro = nomeLivro;
        this.nomeAutor = nomeAutor;
        this.tipoDeLivro = tipoDeLivro;
        this.livroJaFoiLido = livroJaFoiLido;
        this.categoriaLivro = categoriaLivro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getTipoDeLivro() {
        return tipoDeLivro;
    }

    public void setTipoDeLivro(String tipoDeLivro) {
        this.tipoDeLivro = tipoDeLivro;
    }

    public Boolean getLivroJaFoiLido() {
        return livroJaFoiLido;
    }

    public void setLivroJaFoiLido(Boolean livroJaFoiLido) {
        this.livroJaFoiLido = livroJaFoiLido;
    }

    public String getCategoriaLivro() {
        return categoriaLivro;
    }

    public void setCategoriaLivro(String categoriaLivro) {
        this.categoriaLivro = categoriaLivro;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "nomeLivro: " + nomeLivro + '\n' +
                "categoriaLivro: " + categoriaLivro;
    }
}
