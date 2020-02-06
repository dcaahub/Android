package com.example.caraoucoroa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ResultadoActivity extends AppCompatActivity {
    private ImageView imagemResultado;
    private Button botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        imagemResultado = findViewById(R.id.imagemMoeda);
        botaoVoltar = findViewById(R.id.botaoVoltar);

        /*Recuperando dados*/
        Bundle dados = getIntent().getExtras();
        int numero = dados.getInt("numero");

        if(numero == 0){
            //cara
            imagemResultado.setImageResource(R.drawable.moeda_cara);
        }
        else{
            //coroa
            imagemResultado.setImageResource(R.drawable.moeda_coroa);
        }

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
