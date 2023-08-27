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

import br.edu.utfpr.eduardomelentovytch.controledelivroslido.DAO.LivroDAO;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.adapter.LivroAdapter;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.R;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades.Livro;

public class ListaActivity extends AppCompatActivity {

    private static final String ARQUIVO =
            "br.edu.utfpr.eduardomelentovytch.controledelivroslido.PREFERENCIA_ORDENACAO";

    private static final String ORDENACAO = "ORDENACAO";

    private String opcao = "orderId";

    private ListView listViewLivros;
    private LivroDAO livroDAO;
    private LivroAdapter livroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        livroDAO = new LivroDAO();
        inicializaComponentes();

        Intent intent = getIntent();
        Livro livro = (Livro) intent.getSerializableExtra("livro");

        adicionaOuAtualizaLista(livro);
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
        }else {
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
        }else {
            return super.onContextItemSelected(item);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item;
        if(opcao.equals("orderTitulo")){
            menu.findItem(R.id.itemOrdenarTitulo).setChecked(true);
            return true;
        }else if(opcao.equals("orderAutor")){
            menu.findItem(R.id.itemOrdenarAutor).setChecked(true);
            return true;
        }else if (opcao.equals("orderLidoTrue")) {
            menu.findItem(R.id.itemOrdenarLivroLidoTrue).setChecked(true);
            return true;
        } else if (opcao.equals("orderLidoFalse")) {
            menu.findItem(R.id.itemOrdenarLivroLidoFalse).setChecked(true);
            return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void lerPreferenciaOrdenacao(){
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        opcao = shared.getString(ORDENACAO, opcao);

        mudarOrdenacao();
    }

    private void salvarPreferencia(String novaOpcao){
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(ORDENACAO, novaOpcao);
        editor.commit();
        opcao = novaOpcao;
        mudarOrdenacao();
    }

    private void mudarOrdenacao(){
        atualizarLista(opcao);
    }

    private void inicializaComponentes() {
        listViewLivros = findViewById(R.id.listViewLivros);
    }

    private void adicionaOuAtualizaLista(Livro livro) {
        if (!livroDAO.getAll().isEmpty()) {
            if (buscaLivroPorId(livro.getId())) {
                atualizarLista(opcao);
                return;
            } else {
                adicionaLivroNaLista(livro);
                return;
            }
        }
       if (livro != null) {
            popularLista(livro);
       }
    }

    public void mudarParaTelaDeCadastro() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void mudarParaTelaDeCadastroParaEditar(int posicao){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("livro_para_editar", livroDAO.getLivroPelaPosicao(posicao));
        startActivity(intent);
    }

    public void mudarParaTelaSobreAplicativo() {
        Intent intent = new Intent(this, TelaSobreActivity.class);
        startActivity(intent);
    }

    public void mudarParaTelaComentario(int posicao) {
        Intent intent = new Intent(this, TelaComentarioActivity.class);
        intent.putExtra("objeto_clicado", livroDAO.getLivroPelaPosicao(posicao));
        startActivity(intent);
    }

    public void excluir(int posicao) {
        livroDAO.removePorPosicao(posicao);
        atualizarLista(opcao);
    }

    private void popularLista(Livro livro) {
        adicionaLivroNaLista(livro);
    }

    private void adicionaLivroNaLista(Livro livro) {
        livroDAO.add(livro);
        atualizarLista(opcao);
    }

    public boolean buscaLivroPorId(int id) {
        boolean encontrou = false;
        for (Livro livro : livroDAO.getAll()) {
            if (livro.getId() == id) {
                encontrou = true;
            }
        }
        return encontrou;
    }

    public void atualizarLista(String opcaoOrd) {
        if(opcaoOrd.equals("orderId")) {
            livroAdapter = new LivroAdapter(this, livroDAO.getAll());
            livroAdapter.notifyDataSetChanged();
            listViewLivros.setAdapter(livroAdapter);
        }else{
            livroAdapter = new LivroAdapter(this, livroDAO.getAllOrderBy(opcaoOrd));
            livroAdapter.notifyDataSetChanged();
            listViewLivros.setAdapter(livroAdapter);
        }
    }
}
