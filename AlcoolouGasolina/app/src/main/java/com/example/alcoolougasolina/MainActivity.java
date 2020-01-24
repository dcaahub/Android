package com.example.alcoolougasolina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText precoGasolina, precoAlcool;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        precoGasolina = findViewById(R.id.precoGasolina);
        precoAlcool = findViewById(R.id.precoAlcool);
        resultado = findViewById(R.id.textoResultado);
    }

    public void calcular(View view){
        Double preco1, preco2, valor;
        String precoA, precoG;

        precoA = precoAlcool.getText().toString();
        precoG = precoGasolina.getText().toString();

        //Validar dados
        if(validacaoEntradas(precoA, precoG)){
            preco1 = Double.parseDouble(precoG);
            preco2 = Double.parseDouble(precoA);
            valor = preco2/preco1;

            if(valor > 0.7){
                resultado.setText("Gasolina");
            }
            else
                resultado.setText("Alcool");

        }
        else{
            resultado.setText("Preencha os valores de todas as entradas.");
        }
    }


    public boolean validacaoEntradas(String A, String B){
        boolean valor = true;
        if(A.equals("")|| A == null){
            valor = false;
        }
        if(B.equals("")|| B== null){
            valor = false;
        }
        return  valor;
    }
}
