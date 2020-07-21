package com.example.coletaagro.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.coletaagro.R;
import com.example.coletaagro.adapter.FazendaAdapter;
import com.example.coletaagro.helper.FazendaDAO;
import com.example.coletaagro.helper.RecyclerItemClickListener;
import com.example.coletaagro.model.Fazenda;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FazendaAdapter fazendaAdapter;
    private List<Fazenda> listaFazendas = new ArrayList<>();
    private Fazenda fazendaSelecionada;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configurar recycler
        recyclerView = findViewById(R.id.recyclerListaFazendas);

        //Adicionar evento de click
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(), recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Recuperar tarefa para edição
                                fazendaSelecionada = listaFazendas.get(position);
                                //Envia tarefa para tela adicionar tarefa
                                Intent intent = new Intent(MainActivity.this,
                                        AdicionarFazendaActivity.class);
                                intent.putExtra("fazendaSelecionada", fazendaSelecionada);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                //Recuperar tarefa para deletar
                                fazendaSelecionada = listaFazendas.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                //Configura titulo e mensagem
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir a tarefa: "
                                        + fazendaSelecionada.getNomeFazenda() + "?");
                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        FazendaDAO tarefaDAO = new FazendaDAO(getApplicationContext());
                                        if( tarefaDAO.deletar( fazendaSelecionada ) ){

                                            carregarListaDeFazendas();
                                            Toast.makeText(getApplicationContext(), "Sucesso ao excluir tarefa!",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Erro ao excluir tarefa!",
                                                    Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                //Exibir dialog
                                dialog.create();
                                dialog.show();


                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarFazendaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarListaDeFazendas(){
        //Listar Fazendas
        FazendaDAO fazendaDAO = new FazendaDAO(getApplicationContext());
        listaFazendas = fazendaDAO.listar();


        /*Exibe lista de tarefas no RecyclerView*/


        //configurar adapter
        fazendaAdapter = new FazendaAdapter(listaFazendas);

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(fazendaAdapter);
    }

    @Override
    protected void onStart() {
        carregarListaDeFazendas();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}