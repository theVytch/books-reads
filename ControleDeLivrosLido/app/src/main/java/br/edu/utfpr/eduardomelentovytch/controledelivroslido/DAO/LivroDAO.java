package br.edu.utfpr.eduardomelentovytch.controledelivroslido.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades.Livro;
@Dao
public interface LivroDao {

    @Insert
    Long insert(Livro livro);

    @Delete
    void delete(Livro livro);

    @Update
    void update(Livro livro);

    @Query("SELECT * FROM livro WHERE id = :id")
    Livro queryForId(Long id);

    @Query("SELECT * FROM livro ORDER BY id ASC")
    List<Livro> queryAll();

    @Query("SELECT * FROM livro ORDER BY nomeLivro ASC")
    List<Livro> queryAllOrderByTituloLivro();

    @Query("SELECT * FROM livro ORDER BY nomeAutor ASC")
    List<Livro> queryAllOrderByNomeAutor();

    @Query("SELECT * FROM livro WHERE livroJaFoiLido = 1")
    List<Livro> queryAllLivroJaFoiLidoTrue();

    @Query("SELECT * FROM livro WHERE livroJaFoiLido = 0")
    List<Livro> queryAllLivroJaFoiLidoFalse();
}
