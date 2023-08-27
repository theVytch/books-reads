package br.edu.utfpr.eduardomelentovytch.controledelivroslido.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import br.edu.utfpr.eduardomelentovytch.controledelivroslido.DAO.LivroDAO;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.R;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades.Livro;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNomeLivro, editTextNomeAutor;
    private CheckBox cbLivroLido;
    private RadioGroup radioGroupTipoDeLivroEscolhido;
    private RadioButton radioButtonEstudo;
    private RadioButton radioButtonEntretenimento;
    private Spinner spinnerCategoria;
    private Livro livroCriado;
    private Livro livroEditado;
    private ArrayList<String> lista;
    private Integer tipo = 0;
    private Integer numeroRadioButon = 1;
    private LivroDAO livroDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciaComponentes();

        Intent intent = getIntent();
        livroEditado = (Livro) intent.getSerializableExtra("livro_para_editar");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edicaoDoLivro(livroEditado);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.livro_opcoes_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (id == R.id.menuItemSalvar) {
            salvar();
        } else if (id == R.id.menuItemCancelar) {
            limparCampos();
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
        return super.onContextItemSelected(item);
    }
    private void edicaoDoLivro(Livro livro) {
        if(livro != null){
            if(editarLivro(livro) == numeroRadioButon){
               radioButtonEstudo.setChecked(true);
            }else{
                radioButtonEntretenimento.setChecked(true);
            }
        }
    }

    private void iniciaComponentes() {
        editTextNomeLivro = findViewById(R.id.editTextNomeLivro);
        editTextNomeAutor = findViewById(R.id.editTextNomeAutor);
        cbLivroLido = findViewById(R.id.checkBoxLivroLido);
        radioGroupTipoDeLivroEscolhido = findViewById(R.id.radioGroupTipoDeLivro);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        radioButtonEstudo = findViewById(R.id.radioButtonEstudo);
        radioButtonEntretenimento = findViewById(R.id.radioButtonEntretenimento);
        popularSpinner();
    }

    private void popularSpinner() {
        //String[] categoria = getResources().getStringArray(R.array.categoria);
        String[] categoria = getResources().getStringArray(R.array.categoria);
        lista = new ArrayList<>(Arrays.asList(categoria));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                lista);
        spinnerCategoria.setAdapter(adapter);
    }

    public void limparCampos() {
        limpaCampoDosComponentes();
        Toast.makeText(this, R.string.campos_limpos, Toast.LENGTH_SHORT).show();
    }

    private void limpaCampoDosComponentes() {
        editTextNomeLivro.setText(null);
        editTextNomeAutor.setText(null);
        cbLivroLido.setChecked(false);
        editTextNomeLivro.requestFocus();
        radioGroupTipoDeLivroEscolhido.clearCheck();
    }

    public void salvar() {
        Intent intent = new Intent(this, ListaActivity.class);
        if (!validacao()) {
            Toast.makeText(this,
                    R.string.necess_rio_preencher_todos_os_campos,
                    Toast.LENGTH_LONG).show();
            return;
        }

        if(livroEditado == null){
            intent.putExtra("livro", livroCriado);
            startActivity(intent);
        }else{
            intent.putExtra("livro", livroCriado);
            startActivity(intent);
        }
    }

    public Integer editarLivro(Livro livro){
        Integer i;
        editTextNomeLivro.setText(livro.getNomeLivro());
        editTextNomeAutor.setText(livro.getNomeAutor());
        cbLivroLido.setChecked(livro.getLivroJaFoiLido());

        for(i = 0; i < lista.size(); i++){
            if(lista.get(i).toString().equals(livro.getCategoriaLivro())){
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerCategoria.getAdapter();
                spinnerCategoria.setSelection(i);
            }
        }

        if(livro.getTipoDeLivro().equals(getString(R.string.estudo))){
            tipo = 1;
        }
        if(livro.getTipoDeLivro().equals(getString(R.string.entretenimento))){
            tipo = 2;
        }

        editTextNomeLivro.requestFocus();
        radioGroupTipoDeLivroEscolhido.clearCheck();
        return tipo;
    }

    public Boolean validacao() {
        Boolean valido;
        String nomeLivro = editTextNomeLivro.getText().toString();
        String nomeAutor = editTextNomeAutor.getText().toString();
        String tipoDeLivro = "";
        Boolean livroLido;
        String categoria = "";

        if (nomeLivro == null || nomeLivro.trim().isEmpty()) {
            Toast.makeText(this, R.string.erro_nomeLivro, Toast.LENGTH_LONG).show();
            editTextNomeLivro.requestFocus();
            valido = false;
            return valido;
        }

        if (nomeAutor == null || nomeAutor.trim().isEmpty()) {
            Toast.makeText(this, R.string.erro_nomeAutor, Toast.LENGTH_SHORT).show();
            editTextNomeAutor.requestFocus();
            valido = false;
            return valido;
        }

        if (cbLivroLido.isChecked()) {
            livroLido = true;
        } else {
            livroLido = false;
        }

        int checkedRadioButtonId = radioGroupTipoDeLivroEscolhido.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.radioButtonEstudo) {
            tipoDeLivro = getString(R.string.estudo);
        } else if (checkedRadioButtonId == R.id.radioButtonEntretenimento) {
            tipoDeLivro = getString(R.string.entretenimento);
        } else {
            Toast.makeText(this, R.string.erro_radioGroup, Toast.LENGTH_SHORT).show();
            valido = false;
            return valido;
        }

        categoria = (String) spinnerCategoria.getSelectedItem();

        if (categoria == null) {
            Toast.makeText(this, R.string.erro_categoria, Toast.LENGTH_SHORT).show();
            valido = false;
            return valido;
        }

        if(livroEditado != null){
            livroCriado = new Livro();
            livroCriado.setId(livroEditado.getId());
            livroCriado.setNomeLivro(nomeLivro);
            livroCriado.setNomeAutor(nomeAutor);
            livroCriado.setTipoDeLivro(tipoDeLivro);
            livroCriado.setLivroJaFoiLido(livroLido);
            livroCriado.setCategoriaLivro(categoria);
            if(livroEditado.getComentario() != null){
                livroCriado.setComentario(livroEditado.getComentario());
            }
            livroDAO = new LivroDAO();
            livroDAO.setLivroAtualizado(livroCriado);
        }else {
            livroCriado = new Livro(nomeLivro, nomeAutor, tipoDeLivro, livroLido, categoria);
        }
        return true;
    }
}