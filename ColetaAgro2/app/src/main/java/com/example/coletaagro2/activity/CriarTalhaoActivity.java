package com.example.coletaagro2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.coletaagro2.R;
import com.example.coletaagro2.adapter.AdapterMovimentacao;
import com.example.coletaagro2.adapter.AdapterMovimentacaoTalhao;
import com.example.coletaagro2.config.ConfiguracaoFirebase;
import com.example.coletaagro2.helper.Base64Custom;
import com.example.coletaagro2.model.Movimentacao;
import com.example.coletaagro2.model.MovimentacaoTalhao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CriarTalhaoActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private Movimentacao movimentacao;
    private MovimentacaoTalhao movimentacaoTalhao;



    private RecyclerView recyclerView;
    private AdapterMovimentacaoTalhao adapterMovimentacaoTalhao;
    private List<MovimentacaoTalhao> movimentacoes = new ArrayList<>();
    private DatabaseReference movimentacaoRef;
    private ValueEventListener valueEventListenerMovimentacoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_talhao);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        //toolbar.setTitle("ColetaAAA");
        //setSupportActionBar(toolbar);

        movimentacao = (Movimentacao) getIntent().getSerializableExtra("movimentacao");

        toolbar.setTitle(movimentacao.getNomeFazenda());
        setSupportActionBar(toolbar);

        FloatingActionButton fabCriar = findViewById(R.id.fabCriar);
        fabCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarTalhao(view);
            }
        });

        recyclerView = findViewById(R.id.recyclerViewFazenda);
        swipe();

        //configurar adapter
        adapterMovimentacaoTalhao = new AdapterMovimentacaoTalhao(movimentacoes,this);


        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterMovimentacaoTalhao);
    }

    public void swipe(){
        final ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                excluirMovimentacao(viewHolder, movimentacao.getIdentificador());

            }
        };
        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);
    }

    public void excluirMovimentacao(final RecyclerView.ViewHolder viewHolder, final String idFazenda){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //Configuração AlertDialog
        alertDialog.setTitle("Excluir talhão da conta");
        alertDialog.setMessage("Você tem certeza que realmente deseja excluir?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = viewHolder.getAdapterPosition();
                movimentacaoTalhao = movimentacoes.get(position);

                //apagar talhao
                movimentacaoRef = firebaseRef.child("talhao")
                        .child(idFazenda)
                        .child(movimentacaoTalhao.getKey());
                movimentacaoRef.removeValue();
                adapterMovimentacaoTalhao.notifyItemRemoved(position);
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CriarTalhaoActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                adapterMovimentacaoTalhao.notifyDataSetChanged();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();

    }



    public void adicionarTalhao(View view){

        Intent intent = new Intent(CriarTalhaoActivity.this, TalhaoActivity.class);
        intent.putExtra("movimentacao1", movimentacao);
        startActivity(intent);

    }



    public void recuperarMovimentacoes(){

        String idFazenda = movimentacao.getIdentificador();

        movimentacaoRef = firebaseRef.child("talhao")
                .child(idFazenda);


        valueEventListenerMovimentacoes = movimentacaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                movimentacoes.clear();

                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    MovimentacaoTalhao movimentacao = dados.getValue(MovimentacaoTalhao.class);
                    movimentacao.setKey(dados.getKey());
                    movimentacoes.add(movimentacao);
                }

                adapterMovimentacaoTalhao.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }



    @Override
    protected void onStart() {
        super.onStart();
        recuperarMovimentacoes();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //usuarioRef.removeEventListener(valueEventListenerUsuario);
        movimentacaoRef.removeEventListener(valueEventListenerMovimentacoes);
    }
}