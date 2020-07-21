package com.example.coletaagro.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.coletaagro.R;
import com.example.coletaagro.helper.FazendaDAO;
import com.example.coletaagro.model.Fazenda;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarFazendaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Fazenda fazendaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_fazenda);

        editTarefa = findViewById(R.id.textFazenda);

        //Recuperar tarefa caso seja edição
        fazendaAtual = (Fazenda) getIntent().getSerializableExtra("fazendaSelecionada");
        //Configurar caixa de texto
        if(fazendaAtual != null){
            editTarefa.setText(fazendaAtual.getNomeFazenda());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_fazenda, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.itemSalvar :
                //executa acão para o item salvar
                FazendaDAO tarefaDAO = new FazendaDAO(getApplicationContext());
                String nomeTarefa = editTarefa.getText().toString();

                if(fazendaAtual != null){ //edição

                    if( !nomeTarefa.isEmpty() ){
                        Fazenda tarefa = new Fazenda();
                        tarefa.setNomeFazenda( nomeTarefa );
                        tarefa.setId( fazendaAtual.getId() );

                        if ( tarefaDAO.atualizar( tarefa ) ) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao atualizar tarefa!",
                                    Toast.LENGTH_SHORT).show();

                        }
                        else{

                            Toast.makeText(getApplicationContext(), "Erro ao atualizar tarefa!",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }

                }
                else{ //salvar

                    if( !nomeTarefa.isEmpty() ){
                        Fazenda tarefa = new Fazenda();
                        tarefa.setNomeFazenda(nomeTarefa);

                        if( tarefaDAO.salvar(tarefa) ){
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Erro ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();

                        }



                    }
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}