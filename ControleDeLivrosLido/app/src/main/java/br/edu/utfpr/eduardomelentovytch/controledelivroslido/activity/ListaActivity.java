package br.edu.utfpr.eduardomelentovytch.controledelivroslido.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.eduardomelentovytch.controledelivroslido.R;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.adapter.LivroAdapter;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades.Livro;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.persistencia.LivrosDatabase;

public class ListaActivity extends AppCompatActivity {

    private static final String ARQUIVO =
            "br.edu.utfpr.eduardomelentovytch.controledelivroslido.PREFERENCIA_ORDENACAO";
    private static final String ORDENACAO = "ORDENACAO";
    private String opcao = "orderId";
    private ListView listViewLivros;
    private LivroAdapter livroAdapter;
    private List<Livro> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        inicializaComponentes();

        adicionaOuAtualizaLista();
        registerForContextMenu(listViewLivros);

        lerPreferenciaOrdenacao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuItemNovoLivro) {
            mudarParaTelaDeCadastro();
        } else if (id == R.id.menuItemSobreAplicativo) {
            mudarParaTelaSobreAplicativo();
        } else if (id == R.id.itemOrdenarTitulo) {
            salvarPreferencia("orderTitulo");
        } else if (id == R.id.itemOrdenarAutor) {
            salvarPreferencia("orderAutor");
        } else if (id == R.id.itemOrdenarLivroLidoTrue) {
            salvarPreferencia("orderLidoTrue");
        } else if (id == R.id.itemOrdenarLivroLidoFalse) {
            salvarPreferencia("orderLidoFalse");
        } else {
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.principal_menu_contexto, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (id == R.id.menuItemComentario) {
            mudarParaTelaComentario(info.position);
        } else if (id == R.id.menuItemEditar) {
            mudarParaTelaDeCadastroParaEditar(info.position);
        } else if (id == R.id.menuItemExcluir) {
            excluir(info.position);
        } else {
            return super.onContextItemSelected(item);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (opcao) {
            case "orderTitulo":
                menu.findItem(R.id.itemOrdenarTitulo).setChecked(true);
                return true;
            case "orderAutor":
                menu.findItem(R.id.itemOrdenarAutor).setChecked(true);
                return true;
            case "orderLidoTrue":
                menu.findItem(R.id.itemOrdenarLivroLidoTrue).setChecked(true);
                return true;
            case "orderLidoFalse":
                menu.findItem(R.id.itemOrdenarLivroLidoFalse).setChecked(true);
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void lerPreferenciaOrdenacao() {
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        opcao = shared.getString(ORDENACAO, opcao);

        mudarOrdenacao();
    }

    private void salvarPreferencia(String novaOpcao) {
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(ORDENACAO, novaOpcao);
        editor.commit();
        opcao = novaOpcao;
        mudarOrdenacao();
    }

    private void mudarOrdenacao() {
        atualizarLista();
    }

    private void inicializaComponentes() {
        listViewLivros = findViewById(R.id.listViewLivros);
    }

    private void adicionaOuAtualizaLista() {
        LivrosDatabase database = LivrosDatabase.getDatabase(this);
        lista = database.livroDao().queryAll();
        if (!lista.isEmpty()) {
            atualizarLista();
        }
    }

    public void mudarParaTelaDeCadastro() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void mudarParaTelaDeCadastroParaEditar(int posicao) {
        LivrosDatabase database = LivrosDatabase.getDatabase(this);
        atualizarLista();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("livro_para_editar", lista.get(posicao));
        startActivity(intent);
    }

    public void mudarParaTelaSobreAplicativo() {
        Intent intent = new Intent(this, TelaSobreActivity.class);
        startActivity(intent);
    }

    public void mudarParaTelaComentario(int posicao) {
        Intent intent = new Intent(this, TelaComentarioActivity.class);
        atualizarLista();
        if (!lista.isEmpty()) {
            intent.putExtra("objeto_clicado", lista.get(posicao));
        }
        startActivity(intent);
    }

    public void excluir(int posicao) {
        LivrosDatabase database = LivrosDatabase.getDatabase(this);
        atualizarLista();
        database.livroDao().delete(lista.get(posicao));
        atualizarLista();
    }

    private void adicionaLivroNaLista(Livro livro) {
        atualizarLista();
    }

    public void atualizarLista() {
        lista = retornaListaOrdenada(opcao);
        livroAdapter = new LivroAdapter(this, lista);
        livroAdapter.notifyDataSetChanged();
        listViewLivros.setAdapter(livroAdapter);
    }

    public List<Livro> retornaListaOrdenada(String ordenacao) {
        LivrosDatabase database = LivrosDatabase.getDatabase(this);
        List<Livro> listaOrdenada = new ArrayList<>();
        if (ordenacao.equals("orderTitulo")) {
            listaOrdenada = database.livroDao().queryAllOrderByTituloLivro();
        } else if (ordenacao.equals("orderAutor")) {
            listaOrdenada = database.livroDao().queryAllOrderByNomeAutor();
        } else if (ordenacao.equals("orderLidoTrue")) {
            listaOrdenada = database.livroDao().queryAllLivroJaFoiLidoTrue();
        } else if (ordenacao.equals("orderLidoFalse")) {
            listaOrdenada = database.livroDao().queryAllLivroJaFoiLidoFalse();
        } else {
            listaOrdenada = database.livroDao().queryAll();
        }
        return listaOrdenada;
    }
}