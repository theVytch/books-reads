package br.edu.utfpr.eduardomelentovytch.controledelivroslido.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.eduardomelentovytch.controledelivroslido.R;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades.Comentario;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades.Livro;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.persistencia.LivrosDatabase;

public class TelaComentarioActivity extends AppCompatActivity {

    private EditText editTextComentario;
    private TextView txtData;
    private Livro livro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_comentario);

        inicializaComponentes();

        Intent intent = getIntent();
        livro = (Livro) intent.getSerializableExtra("objeto_clicado");

        preparaEditTextParaComentario();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void preparaEditTextParaComentario() {
        if(livro.getComentario() == null){
            editTextComentario.setText("");
            txtData.setText("");
        }else {
            editTextComentario.setText(livro.getComentario().getComentario());
            txtData.setText(livro.getComentario().getData());
        }
    }

    private void inicializaComponentes() {
        editTextComentario = findViewById(R.id.editTextComentario);
        txtData = findViewById(R.id.txtData);
    }

    public void salvarComentario(View view){
        LivrosDatabase database = LivrosDatabase.getDatabase(this);
        Livro livroAux = database.livroDao().queryForId(livro.getId());
        livroAux.setComentario(new Comentario(editTextComentario.getText().toString()));
        database.livroDao().update(livroAux);
        voltarParaTelaListaActivity(livroAux);
    }

    public void voltarParaTelaListaActivity(Livro livro){
        Intent intent = new Intent(this, ListaActivity.class);
        intent.putExtra("livro", livro);
        startActivity(intent);
    }

}
