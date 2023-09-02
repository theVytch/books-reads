package br.edu.utfpr.eduardomelentovytch.controledelivroslido.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.eduardomelentovytch.controledelivroslido.DAO.LivroDao;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades.Livro;

@Database(entities = {Livro.class}, version = 2, exportSchema = false)
public abstract class LivrosDatabase extends RoomDatabase {

    public abstract LivroDao livroDao();

    private static LivrosDatabase instance;

    public static LivrosDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (LivrosDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                                    LivrosDatabase.class,
                                    "livros.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }
}
