package com.example.cardview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.cardview.R;
import com.example.cardview.adapter.PostagemAdapter;
import com.example.cardview.model.Postagem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerPostagem;
    private List<Postagem> postagens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerPostagem = findViewById(R.id.recyclerPostagem);

        /*Define layout*/
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerPostagem.setLayoutManager(layoutManager);

        /*Define Layout Horizontal*/
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setOrientation(LinearLayout.HORIZONTAL);
        //recyclerPostagem.setLayoutManager(layoutManager);

        /*Define Layout grid*/
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerPostagem.setLayoutManager(layoutManager);


        /*Define adapter*/
        prepararPostagens();
        PostagemAdapter adapter = new PostagemAdapter(postagens);
        recyclerPostagem.setAdapter( adapter );
    }
    public void prepararPostagens(){
        Postagem p = new Postagem("Danillo Christi", "#tbt viagem legal!", R.drawable.imagem1);
        this.postagens.add(p);

        p = new Postagem("Higor", "Tudo de bão!", R.drawable.imagem2);
        this.postagens.add(p);

        p = new Postagem("Rita", "Que legal", R.drawable.imagem3);
        this.postagens.add(p);

        p = new Postagem("Waldomiro", "Divertidoooo ooooooooo oooooooo oooooooooo", R.drawable.imagem4);
        this.postagens.add(p);

    }
}
