package com.example.coletaagro2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.coletaagro2.R;
import com.google.android.material.textfield.TextInputEditText;

public class ImagemActivity extends AppCompatActivity {
    private TextInputEditText campoLegenda;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem);

        campoLegenda = findViewById(R.id.editLegenda);
        imageView = findViewById(R.id.imageView);



    }

    public void salvarImagem(){

    }
}