package com.example.coletaagro2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coletaagro2.R;
import com.example.coletaagro2.config.ConfiguracaoFirebase;
import com.example.coletaagro2.model.Movimentacao;
import com.example.coletaagro2.model.MovimentacaoTalhao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GerarRelatorioActivity extends AppCompatActivity {


    private TextView textRelatorio;
    private String nomeConsultoria = "GLOBAL CONSULTURIA";

    private Button botaoRelatorio;

    private Movimentacao movimentacao;


    private StorageReference storageReference = ConfiguracaoFirebase.getFirebaseStorage();
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();

    private ImageLoader imageLoader = ImageLoader.getInstance();


    int flag = 0;
    int indiceImg = 1;
    int indiceTalhao = 0;


    /*private Bitmap imageTalhao[] = new Bitmap[100];
    private String nomeTalhao[] = new String[100];
    private String idTalhao[] = new String[100];*/


    ArrayList<String> nomeTalhaoList = new ArrayList<>();
    ArrayList<String> idTalhaoList = new ArrayList<>();
    ArrayList<String> descricaoTalhaoList = new ArrayList<>();
    ArrayList<String> legenda1TalhaoList = new ArrayList<>();
    ArrayList<String> legenda2TalhaoList = new ArrayList<>();
    ArrayList<String> legenda3TalhaoList = new ArrayList<>();
    ArrayList<String> dataTalhaoList = new ArrayList<>();
    ArrayList<Bitmap> bitmapList = new ArrayList<>();


    ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerar_relatorio);

        textRelatorio = findViewById(R.id.textRelatorio);
        botaoRelatorio = findViewById(R.id.botaoRelatorio);
        botaoRelatorio.setVisibility(View.INVISIBLE);

        botaoRelatorio.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fechar();

            }
        });


        movimentacao = (Movimentacao) getIntent().getSerializableExtra("movimentacao2");


        imageLoader.init(ImageLoaderConfiguration.createDefault(GerarRelatorioActivity.this));










        DatabaseReference movimentacaoRef = firebaseRef.child("talhao").child(movimentacao.getIdentificador());
        movimentacaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    MovimentacaoTalhao mov= dados.getValue(MovimentacaoTalhao.class);
                    nomeTalhaoList.add(mov.getNomeTalhao());
                    idTalhaoList.add(mov.getIdentificador());
                    descricaoTalhaoList.add(mov.getDescricao());
                    legenda1TalhaoList.add(mov.getLegenda1());
                    legenda2TalhaoList.add(mov.getLegenda2());
                    legenda3TalhaoList.add(mov.getLegenda3());
                    dataTalhaoList.add(mov.getData());

                    Log.i("INFOTALHAO", "TalhaoArray:::"+mov.getNomeTalhao());



                    
                }

                baixarImagens2();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        /*StorageReference imagemRef1 = storageReference
                .child("imagens")
                .child(movimentacao.getIdentificador())
                .child("07012021111728-890914540")
                .child("imagem1.jpeg");


        imagemRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    pegaimagem(uri.toString());

                }
        });*/



    }

   private void fechar(){
        finish();
   }

    private void baixarImagens2(){
        Log.i("INFOTALHAO", "iimages" + idTalhaoList.size());

        StorageReference imagemRef1;


        imagemRef1 = storageReference
                .child("imagens")
                .child(movimentacao.getIdentificador())
                .child(idTalhaoList.get(indiceTalhao))
                .child("imagem" + indiceImg + ".jpeg");


        imagemRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //pegaimagem(uri.toString());

                Log.i("INFOTALHAO", "link " + uri.toString());
                funcao(uri.toString());

                // Log.i("INFOTALHAO", "link "+ uri.toString());

            }
        });





    }



    private void funcao(String linkImagem){




        imageLoader.loadImage(linkImagem, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                bitmapList.add(loadedImage);
                Log.i("INFOTALHAO", "funcao"+ bitmapList);


                flag = flag +1;
                if(flag == 3*idTalhaoList.size()){
                    printPDF();
                }
                else if(indiceImg == 3){
                    indiceTalhao = indiceTalhao +1;
                    indiceImg = 1;
                    baixarImagens2();
                }
                else if(indiceImg == 2){
                    indiceImg = 3;
                    baixarImagens2();
                }
                else{
                    /*indiceImg == 1*/
                    indiceImg = 2;
                    baixarImagens2();
                }





            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });



    }



    private void printPDF(){

        Log.i("INFOTALHAO", "PDF ");

        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        Paint titlePaint = new Paint();

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextSize(20);

        int i;
        Canvas canvas;
        Bitmap sbm;

        for(i = 0; i< idTalhaoList.size(); i++) {


            PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(598, 842, i + 1).create();
            PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);

            canvas = myPage1.getCanvas();

            canvas.drawText(nomeConsultoria, 300, 40, titlePaint);
            canvas.drawText("Cliente: " + movimentacao.getNomeFazenda(), 300, 80, titlePaint);


           /* canvas.drawText(nomeTalhaoList.get(i), 30, 120, myPaint);
            //canvas.drawText(descricaoTalhaoList.get(0), 30, 140, myPaint);
            TextPaint mTextPaint;
            mTextPaint = new TextPaint();
            StaticLayout mTextLayout = new StaticLayout(descricaoTalhaoList.get(i), mTextPaint,
                    canvas.getWidth() - 100, Layout.Alignment.ALIGN_NORMAL,
                    1.0f, 0.0f, false);
            canvas.save();
            int textX = 30;
            int textY = 140;
            canvas.translate(textX, textY);
            mTextLayout.draw(canvas);
            canvas.restore();*/


            sbm = Bitmap.createScaledBitmap(bitmapList.get(i*3), 300, 150, true);
            canvas.drawBitmap(sbm, 30, 250, myPaint);
            canvas.drawText(legenda1TalhaoList.get(i), 30, 420, myPaint);

            sbm = Bitmap.createScaledBitmap(bitmapList.get(i*3 + 1), 300, 150, false);
            canvas.drawBitmap(sbm, 30, 450, myPaint);
            canvas.drawText(legenda2TalhaoList.get(i), 30, 620, myPaint);

            sbm = Bitmap.createScaledBitmap(bitmapList.get(i*3 + 2), 300, 150, false);
            canvas.drawBitmap(sbm, 30, 650, myPaint);
            canvas.drawText(legenda3TalhaoList.get(i), 30, 820, myPaint);

            canvas.drawText(nomeTalhaoList.get(i), 30, 120, myPaint);
            //canvas.drawText(descricaoTalhaoList.get(0), 30, 140, myPaint);
            TextPaint mTextPaint;
            mTextPaint = new TextPaint();
            StaticLayout mTextLayout = new StaticLayout(descricaoTalhaoList.get(i), mTextPaint,
                    canvas.getWidth() - 100, Layout.Alignment.ALIGN_NORMAL,
                    1.0f, 0.0f, false);
            canvas.save();
            int textX = 30;
            int textY = 140;
            canvas.translate(textX, textY);
            mTextLayout.draw(canvas);
            canvas.restore();


            myPdfDocument.finishPage(myPage1);
        }



        File file = new File(Environment.getExternalStorageDirectory(), "/First2.pdf");


        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            Log.i("INFOTALHAO", "erro "+ e);
            e.printStackTrace();
        }

        myPdfDocument.close();

        Log.i("INFOTALHAO", "PDF2 ");

        botaoRelatorio.setVisibility(View.VISIBLE);
        textRelatorio.setText("Relatório finalizado!");

    }


}






