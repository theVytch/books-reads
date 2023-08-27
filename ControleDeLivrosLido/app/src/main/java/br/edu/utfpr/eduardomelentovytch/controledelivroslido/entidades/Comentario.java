package br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades;

import android.os.Build;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Comentario implements Serializable {

    private String comentario;
    private String data;

    public Comentario(String comentario) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.data = (String )LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        this.comentario = comentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getData() {
        return data;
    }
}
