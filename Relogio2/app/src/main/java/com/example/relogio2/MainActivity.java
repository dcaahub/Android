package com.example.relogio2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    // private ImageView imageView;
    private TextView textView;
    private ConstraintLayout constraintLayout;
    private int vetorImagens[] = {
            R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
            R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8,
            R.drawable.image9, R.drawable.image10
    };


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);*/

        constraintLayout = findViewById(R.id.layout);
        textView = findViewById(R.id.textView);

        constraintLayout.setBackgroundResource(R.drawable.image10);


        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        final SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm");


        Date data = new Date();

        final Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        final Date data_atual;
        final String hora_atual;


        data_atual = cal.getTime();

        //String data_completa = dateFormat.format(data_atual);

        hora_atual = dateFormat_hora.format(data_atual);
        textView.setText(hora_atual);

        final int i = 0;



        final Handler handler = new Handler();
        class MyRunnable implements Runnable {
            private Handler handler;
            private String hora_atual;
            private TextView textView;
            private Date data_atual;
            private Date data = new Date();
            private Calendar cal;
            private int i = 0;
            //private ConstraintLayout constraintLayout;
            //private int vetorImagens[];

            public MyRunnable(Handler handler, int i, Calendar cal, Date data_atual, String hora_atual, TextView textView) {
                this.handler = handler;
                this.data_atual = data_atual;
                this.hora_atual = hora_atual;
                this.textView = textView;
                this.cal = cal;
                this.i = i;
                //this.constraintLayout = constraintLayout;
                //this.vetorImagens = vetorImagens;

            }
            @Override
            public void run() {
                this.handler.postDelayed(this, 500);
                Date data = new Date();
                cal = Calendar.getInstance();
                cal.setTime(data);
                this.data_atual = cal.getTime();
                this.hora_atual = dateFormat_hora.format(data_atual);
                this.textView.setText(hora_atual);
                String c = this.hora_atual.substring(4);


                if((c.equals("0"))){

                    constraintLayout.setBackgroundResource(vetorImagens[0]);

                }
                if((c.equals("1"))){
                    constraintLayout.setBackgroundResource(vetorImagens[1]);
                }
                if((c.equals("2"))){
                    constraintLayout.setBackgroundResource(vetorImagens[2]);
                }
                if((c.equals("3"))){
                    constraintLayout.setBackgroundResource(vetorImagens[3]);
                }
                if((c.equals("4"))){
                    constraintLayout.setBackgroundResource(vetorImagens[4]);
                }
                if((c.equals("5"))){
                    constraintLayout.setBackgroundResource(vetorImagens[5]);
                }
                if((c.equals("6"))){
                    constraintLayout.setBackgroundResource(vetorImagens[6]);
                }
                if((c.equals("7"))){
                    constraintLayout.setBackgroundResource(vetorImagens[7]);
                }
                if((c.equals("8"))){
                    constraintLayout.setBackgroundResource(vetorImagens[8]);
                }
                if((c.equals("9"))){
                    constraintLayout.setBackgroundResource(vetorImagens[9]);
                }
            }
        }
        handler.post(new MyRunnable(handler, i, cal, data_atual, hora_atual, textView));




    }
}