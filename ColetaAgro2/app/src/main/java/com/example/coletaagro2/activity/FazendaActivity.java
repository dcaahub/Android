package com.example.coletaagro2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.coletaagro2.R;
import com.example.coletaagro2.model.Movimentacao;
import com.google.android.material.textfield.TextInputEditText;

public class FazendaActivity extends AppCompatActivity {
    private TextInputEditText campoId, campoFazenda, campoEndereco;
    private Movimentacao movimentacao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazenda);

        campoId = findViewById(R.id.editId);
        campoFazenda = findViewById(R.id.editNomeFazenda);
        campoEndereco = findViewById(R.id.editEndereco);

    }

    public void salvarFazenda(View view){
        if(validarCamposFazenda()){
            movimentacao = new Movimentacao();


            movimentacao.setNomeFazenda(campoFazenda.getText().toString());
            movimentacao.setEndereco(campoEndereco.getText().toString());
            movimentacao.setIdentificador(campoId.getText().toString());


            movimentacao.salvar();
            finish();

        }

    }

    public Boolean validarCamposFazenda(){
        String textoId = campoId.getText().toString();
        String textoFazenda = campoFazenda.getText().toString();
        String textoEndereco = campoEndereco.getText().toString();

        if(!textoId.isEmpty()){
            if (!textoFazenda.isEmpty()){
                if(!textoEndereco.isEmpty()){

                    return true;


                }
                else{
                    Toast.makeText(FazendaActivity.this, "Preencha o endereço!", Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Toast.makeText(FazendaActivity.this, "Preencha o Nome da fazenda!", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(FazendaActivity.this, "Preencha o identificador!", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

}