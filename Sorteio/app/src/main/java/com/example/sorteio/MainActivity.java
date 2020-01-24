package com.example.sorteio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void numeroAleatorio(View view){
        Random random = new Random();

        int num = random.nextInt(10);
        TextView texto = findViewById(R.id.textoResultado);
        texto.setText("O número escolhido é: "+num);


    }
}
