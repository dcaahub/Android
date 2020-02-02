package com.example.recyclerview.activity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.recyclerview.R;
import com.example.recyclerview.activity.RecyclerItemClickListener;
import com.example.recyclerview.activity.adapter.Adapter;
import com.example.recyclerview.activity.model.Filme;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Filme> listaFilmes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        /*Listagem de filmes*/
        this.criarFilmes();

        /*Configurar adapter*/
        Adapter adapter = new Adapter(listaFilmes);

        /*Configurar RecyclerView*/
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);

        /*Evento de clique*/
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Filme filme = listaFilmes.get(position);
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Item pressionado: " + filme.getTituloFilme(),
                                        Toast.LENGTH_SHORT
                                        ).show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Filme filme = listaFilmes.get(position);
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Clique longo: " + filme.getTituloFilme(),
                                        Toast.LENGTH_SHORT
                                ).show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

    }

    public void criarFilmes(){
        Filme filme = new Filme("Um Sonho de Liberdade", "1994", "Drama");
        this.listaFilmes.add(filme);
        filme = new Filme("O Poderoso Chefão", "1972", "Crime, Drama");
        this.listaFilmes.add(filme);
        filme = new Filme("O Poderoso Chefão II", "1974", "Crime, Drama");
        this.listaFilmes.add(filme);
        filme = new Filme("Batman: O Cavaleiro das Trevas", "2008", "Action, Crime, Drama");
        this.listaFilmes.add(filme);
        filme = new Filme("12 Homens e uma Sentença", "1957", "Drama");
        this.listaFilmes.add(filme);
        filme = new Filme("A Lista de Schindler", "1993", "Biography, Drama, History");
        this.listaFilmes.add(filme);
        filme = new Filme("O Senhor dos Anéis: O Retorno do Rei", "2003", "Adventure, Drama, Fantasy");
        this.listaFilmes.add(filme);
        filme = new Filme("Pulp Fiction: Tempo de Violência", "1994", "Crime, Drama");
        this.listaFilmes.add(filme);
        filme = new Filme("Três Homens em Conflito", "1966", "Western");
        this.listaFilmes.add(filme);
        filme = new Filme("O Senhor dos Anéis: A Sociedade do Anel", "2001", "Adventure, Drama, Fantasy");
        this.listaFilmes.add(filme);
        filme = new Filme("Clube da Luta", "1999", "Drama");
        this.listaFilmes.add(filme);
        filme = new Filme("Forrest Gump: O Contador de Histórias", "1994", "Drama, Romance");
        this.listaFilmes.add(filme);
        filme = new Filme("A Origem", "2010", "Action, Adventure, Sci-Fi");
        this.listaFilmes.add(filme);

    }
}
