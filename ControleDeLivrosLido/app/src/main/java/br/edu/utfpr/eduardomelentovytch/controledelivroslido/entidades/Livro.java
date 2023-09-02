package br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades;


import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Livro implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @NonNull
    private String nomeLivro;
    @NonNull
    private String nomeAutor;
    @NonNull
    private String tipoDeLivro;
    private Integer livroJaFoiLido;
    private String categoriaLivro;
    @Embedded
    private Comentario comentario;

    public Livro() {
    }

    public Livro(String nomeLivro, String nomeAutor, String tipoDeLivro, Integer livroJaFoiLido, String categoriaLivro) {
        this.nomeLivro = nomeLivro;
        this.nomeAutor = nomeAutor;
        this.tipoDeLivro = tipoDeLivro;
        this.livroJaFoiLido = livroJaFoiLido;
        this.categoriaLivro = categoriaLivro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getLivroJaFoiLido() {
        return livroJaFoiLido;
    }

    public void setLivroJaFoiLido(Integer livroJaFoiLido) {
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
}
