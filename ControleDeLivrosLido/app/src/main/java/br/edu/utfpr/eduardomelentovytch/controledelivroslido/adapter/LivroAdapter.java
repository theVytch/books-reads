package br.edu.utfpr.eduardomelentovytch.controledelivroslido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.eduardomelentovytch.controledelivroslido.R;
import br.edu.utfpr.eduardomelentovytch.controledelivroslido.entidades.Livro;

public class LivroAdapter extends BaseAdapter {

    private Context context;
    private List<Livro> livros;

    private static class LivroHolder{
        public TextView textViewValorNomeAutor;
        public TextView textViewValorNomeLivro;
        public TextView textViewValorCategoria;
    }

    public LivroAdapter(Context context, List<Livro> livros){
        this.context = context;
        this.livros = livros;
    }


    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int position) {
        return livros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LivroHolder holder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_linha_lista_livro, parent, false);

            holder = new LivroHolder();

            holder.textViewValorNomeLivro = convertView.findViewById(R.id.linhaListaNomeLivroResposta);
            holder.textViewValorNomeAutor = convertView.findViewById(R.id.linhaListaNomeAutorResposta);
            holder.textViewValorCategoria = convertView.findViewById(R.id.linhaListaNomeCategoriaResposta);

            convertView.setTag(holder);
        } else {
            holder = (LivroHolder) convertView.getTag();
        }

        //convertView.setBackgroundColor(Color.parseColor("#D76C6C"));
        holder.textViewValorNomeLivro.setText(livros.get(position).getNomeLivro());
        holder.textViewValorNomeAutor.setText(livros.get(position).getNomeAutor());
        holder.textViewValorCategoria.setText(livros.get(position).getCategoriaLivro());

        return convertView;
    }
}
