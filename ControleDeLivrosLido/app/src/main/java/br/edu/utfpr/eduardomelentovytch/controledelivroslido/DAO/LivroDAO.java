package br.edu.utfpr.eduardomelentovytch.controledelivroslido.DAO;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades.Comentario;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades.Livro;

public class LivroDAO {

    private final static List<Livro> livrosDao = new ArrayList<>();
    private static int contadorDeIds = 1;
    public List<Livro> getAll(){
        return new ArrayList<>(livrosDao);
    }

    @SuppressLint("NewApi")
    public List<Livro> getAllOrderBy(String opcaoDeOrdenacao){
        if(opcaoDeOrdenacao.equals("orderTitulo")) {
            return new ArrayList<>(livrosDao).stream()
                    .sorted(Comparator.comparing(Livro::getNomeLivro))
                    .collect(Collectors.toList());
        }

        if(opcaoDeOrdenacao.equals("orderAutor")) {
            return new ArrayList<>(livrosDao).stream()
                    .sorted(Comparator.comparing(Livro::getNomeAutor))
                    .collect(Collectors.toList());
        }

        if(opcaoDeOrdenacao.equals("orderLidoTrue")) {
            return new ArrayList<>(livrosDao).stream()
                    .filter(livro -> livro.getLivroJaFoiLido())
                    .collect(Collectors.toList());
        }

        if(opcaoDeOrdenacao.equals("orderLidoFalse")) {
            return new ArrayList<>(livrosDao).stream()
                    .filter(livro -> !livro.getLivroJaFoiLido())
                    .collect(Collectors.toList());
        }

        return getAll();
    }

    public Livro getLivroPelaPosicao(int posicao){
        return getAll().get(posicao);
    }

    public void add(Livro livro){
        livro.setId(contadorDeIds);
        livrosDao.add(livro);
        atualizaIds();
    }

    public void removePorPosicao(int posicao) {
        livrosDao.remove(posicao);
    }

    public void setComentarioNoLivro(Livro livro, String comentario){
        for (Livro lv: getAll()) {
            if(livro.getId() == lv.getId()){
                lv.setComentario(new Comentario(comentario));
            }
        }
    }

    public void setLivroAtualizado(Livro livroEditado){
        for (Livro livroVelho: getAll()) {
            if(livroEditado.getId() == livroVelho.getId()){
                atualizaLivro(livroVelho, livroEditado);
            }
        }
    }

    public void atualizaLivro(Livro livroVelho, Livro livroEditado){
        livroVelho.setId(livroEditado.getId());
        livroVelho.setNomeLivro(livroEditado.getNomeLivro());
        livroVelho.setNomeAutor(livroEditado.getNomeAutor());
        livroVelho.setTipoDeLivro(livroEditado.getTipoDeLivro());
        livroVelho.setLivroJaFoiLido(livroEditado.getLivroJaFoiLido());
        livroVelho.setCategoriaLivro(livroEditado.getCategoriaLivro());
        if (livroVelho.getComentario() != null){
            livroVelho.setComentario(livroEditado.getComentario());
        }
    }

    private void atualizaIds() {
        contadorDeIds++;
    }
}
