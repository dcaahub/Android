package com.example.calculadoradegorjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private EditText textoValor;
    private SeekBar barraGorjeta;
    private TextView textoPorcentagem;
    private TextView textoGorjeta;
    private TextView textoResultado;
    private double porcentagem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoValor = findViewById(R.id.textoValor);
        barraGorjeta = findViewById(R.id.seekBarGorjeta);
        textoPorcentagem = findViewById(R.id.textoPorcentagem);
        textoGorjeta = findViewById(R.id.textoGorjeta);
        textoResultado = findViewById(R.id.textoResultado);

        //adicinar listener
        barraGorjeta.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                porcentagem = progress;
                textoPorcentagem.setText(porcentagem+"%");
                calcularGorjeta();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void calcularGorjeta(){

        String valorDado = textoValor.getText().toString();
        double valorDigitado;
        double valorGorjeta, valorTotal;

        if(valorDado.equals("") || valorDado == null){
            Toast.makeText(getApplicationContext(),
                    "Digite o valor da conta",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            valorDigitado = Double.parseDouble(valorDado);
            valorGorjeta = valorDigitado*(porcentagem/100);
            valorTotal = valorDigitado + valorGorjeta;
            textoGorjeta.setText("R$ "+ valorGorjeta);
            textoResultado.setText("R$ "+ valorTotal);


        }


    }
}
