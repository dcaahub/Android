package com.example.coletaagro2.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.icu.util.TimeUnit;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coletaagro2.R;
import com.example.coletaagro2.config.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/*Descartado para usar a classe PDFDocument*/
public class Relatorio {
    private static com.itextpdf.text.Font catFont = new com.itextpdf.text.Font(
            com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 18, com.itextpdf.text.Font.BOLD);
    private static com.itextpdf.text.Font redFont = new com.itextpdf.text.Font(
            com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.NORMAL, BaseColor.RED);
    private static com.itextpdf.text.Font subFont = new com.itextpdf.text.Font(
            com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 16, com.itextpdf.text.Font.BOLD);
    private static com.itextpdf.text.Font smallBold = new com.itextpdf.text.Font(
            com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.BOLD);

    private static Font titulo = new Font(
            Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static com.itextpdf.text.Font subTitulo = new Font(
            Font.FontFamily.HELVETICA, 12, Font.BOLD);


    Document document;

    private List<String> legenda1 = new ArrayList<>();
    private String leg;

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private List<MovimentacaoTalhao> movimentacoes = new ArrayList<>();





    public Relatorio() {
    }

    //write method takes two parameter pdf name and content
    //return true if pdf successfully created
    public void write(final String fname, final Context context, final Intent intent) {

        final Movimentacao movimentacao = (Movimentacao) intent.getSerializableExtra("movimentacao");
        DatabaseReference movimentacaoRef = firebaseRef.child("talhao").child(movimentacao.getIdentificador());

        movimentacaoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                try {
                    Log.i("erro", "eroo0000"+context.getApplicationContext().getExternalFilesDir(null));

                    //Create file path for Pdf
                    Random rand = new Random();
                    String fpath = context.getExternalFilesDir(null) + "/" + fname + "-" +rand.nextInt() + ".pdf";
                    File file = new File(fpath);
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    Log.i("erro", "eroo0000");


                    // create an instance of itext document
                    document = new Document();

                    PdfWriter.getInstance(document,
                            new FileOutputStream(file));
                    document.open();
                    //using add method in document to insert a paragraph
                    String texto = movimentacao.getNomeFazenda();

                    Paragraph paragraph = new Paragraph("EMPRESA DE CONSULTORIA", titulo);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraph);

                    //adiciona uma linha em branco;
                    document.add(new Paragraph(" "));

                    paragraph = new Paragraph(movimentacao.getNomeFazenda(), titulo);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraph);

                    paragraph = new Paragraph(movimentacao.getEndereco());
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraph);
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph(" "));

                    //List<MovimentacaoTalhao> movimentacoes = new ArrayList<>();

                    //movimentacoes.clear();
                    for(DataSnapshot dados: dataSnapshot.getChildren()){
                        MovimentacaoTalhao mov= dados.getValue(MovimentacaoTalhao.class);
                        //movimentacoes.add(mov);
                        paragraph = new Paragraph(mov.getNomeTalhao(), subTitulo);
                        document.add(paragraph);
                        document.add(new Paragraph(" "));
                        paragraph = new Paragraph("Talhão visitado em: "+mov.getData());
                        document.add(paragraph);
                        paragraph = new Paragraph(mov.getDescricao());
                        document.add(paragraph);

                        //verificar se há imagem e adicionar imagem

                        StorageReference storageReference = ConfiguracaoFirebase.getFirebaseStorage();
                        StorageReference imagemRef = storageReference.child("imagens")
                                .child(movimentacao.getIdentificador())
                                .child(mov.getIdentificador()).child("imagem1.jpeg");
                        Log.i("erro", "erooD"+mov.getLegenda1());

                        final long ONE_MEGABYTE = 1024 * 1024;



                        //Uri uri = imagemRef.getDownloadUrl().getResult();
                        //Log.i("erro", "erooDURL"+uri);

                        StorageReference imagemRef2 = storageReference
                                .child("imagens")
                                .child(movimentacao.getIdentificador())
                                .child(mov.getIdentificador())
                                .child("imagem1.jpeg");

                       // final byte[][] dadosImagem = new byte[1][1];


                        Task<byte[]> task = imagemRef2.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Log.i("erro", "erooDddd    CERTO");


                            }
                        });

                    //    Image img  = Image.getInstance(String.valueOf(R.drawable.montanha));

                       // SystemClock.sleep(5000);
                        //Thread.sleep(5000);

                        //Uri uri = imagemRef2.getDownloadUrl().getResult();
                       /* long l = 0;
                        Task<byte[]> task = imagemRef2.getBytes(l);
                        Log.i("erro", "erooD"+mov.getIdentificador());*/

                       /* Task<Uri> uri = imagemRef.getDownloadUrl();
                        Tasks.whenAllComplete(task);
                        Log.i("erro", "erooD"+"Galaxy"+task);*/

                       //Task<Image> ig2 = Image.


                        //URL url = new URL("https://fazendavendida.com/wp-content/uploads/2018/07/cf73e4ee-2104-4f17-98a6-66c4985e4d9e.jpg");
                        //Image img = Image.getInstance("https://fazendavendida.com/wp-content/uploads/2018/07/cf73e4ee-2104-4f17-98a6-66c4985e4d9e.jpg");
                       // img.setAlignment(Element.ALIGN_CENTER);
                      //  img.scaleAbsolute(400,200);
                       // document.add(img);
                       /* try {
                            Tasks.await(null, 35, TimeUnit.SECONDS);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        }*/


                        Log.i("erro", "erooURL");


                    }


                    //pegaDados(intent);
                   // Log.i("erro", "erooAA: "+movimentacoes.size());
                    //Log.i("erro", "erooAAV: "+legenda1.size());
                    //Log.i("erro", "erooAAA"+leg);


                    // close document
                    document.close();
                    //return true;
                    Log.i("erro", "erooFIM");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("erro", "eroo1");
                    //return false;
                } catch (DocumentException e) {
                    e.printStackTrace();
                    Log.i("erro", "eroo2");
                    //return false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public Boolean write2(String fname, Context context, Intent intent) {

        try {
            Log.i("erro", "eroo0000"+context.getApplicationContext().getExternalFilesDir(null));


            //Create file path for Pdf
            Random rand = new Random();
            String fpath = context.getExternalFilesDir(null) + "/" + fname + "-" +rand.nextInt() + ".pdf";
            File file = new File(fpath);
            if (!file.exists()) {
                file.createNewFile();
            }

            Log.i("erro", "eroo0000");

            Movimentacao movimentacao = (Movimentacao) intent.getSerializableExtra("movimentacao");



            // create an instance of itext document
            document = new Document();

            PdfWriter.getInstance(document,
                    new FileOutputStream(file));
            document.open();
            //using add method in document to insert a paragraph
            String texto = movimentacao.getNomeFazenda();

            document.add(new Paragraph(movimentacao.getNomeFazenda(),catFont));
            document.add(new Paragraph(movimentacao.getEndereco()));
            document.add(new Paragraph(""));
            document.add(new Paragraph("oioi"));




            pegaDados(intent);
            Log.i("erro", "erooAA: "+movimentacoes.size());
            Log.i("erro", "erooAAV: "+legenda1.size());
            //Log.i("erro", "erooAAA"+leg);



            document.add(new Paragraph("My First Pdf !"));
            document.add(new Paragraph("Hello World"));





            // close document
            document.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("erro", "eroo1");
            return false;
        } catch (DocumentException e) {
            e.printStackTrace();
            Log.i("erro", "eroo2");
            return false;
        }
    }

    public void pegaDados(Intent intent){
        Log.i("erro", "erooD");

        Movimentacao movimentacao = (Movimentacao) intent.getSerializableExtra("movimentacao");

        DatabaseReference movimentacaoRef = firebaseRef.child("talhao").child(movimentacao.getIdentificador());

        movimentacaoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 movimentacoes.clear();
                 for(DataSnapshot dados: dataSnapshot.getChildren()){
                     MovimentacaoTalhao mov= dados.getValue(MovimentacaoTalhao.class);
                     movimentacoes.add(mov);
                     Log.i("erro", "erooD"+mov.getLegenda1());

                     legenda1.add("ooo");
                 }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void gerarPDF(Context context){


       // movimentacoes.clear();

       // for(DataSnapshot dados: dataSnapshot.getChildren()){
           // MovimentacaoTalhao mov= dados.getValue(MovimentacaoTalhao.class);
            //movimentacoes.add(mov);
            //Log.i("erro", "erooD"+mov.getLegenda1());
       // }



        //Cria um documento para gerar o PDF
        PdfDocument documentoPDF = new PdfDocument();

        //Especifica detalhes da página
        PdfDocument.PageInfo detalhesDaPagina = new PdfDocument.PageInfo.Builder(595, 842, 1).create();

        PdfDocument.Page page = documentoPDF.startPage(detalhesDaPagina);
        Canvas canvas = page.getCanvas();

        Paint corDoTexto = new Paint();
        corDoTexto.setColor(Color.rgb(0,0,0));

        canvas.drawText("Fazenda", 10, 10, corDoTexto);
        canvas.drawText("Fazenda2", 10, 100, corDoTexto);
        canvas.drawText("Fazenda3", 10, 150, corDoTexto);

        documentoPDF.finishPage(page);

        //criar no sdcard
        //String targetPdf;
        //File filePath = new File(targetPdf);

        File output = null;

        File outputDir = new File(context.getFilesDir()+"/Output/PDF");
        if(!outputDir.exists()){
            outputDir.mkdirs();
        }
        Random rand = new Random();
        output = new File(context.getFilesDir()+"/Output/PDF/data-"+rand.nextInt()+".pdf");


        String path = context.getFilesDir().toString() +"/PDF";
        Log.i("erro", "eroo"+path);

        File dir = new File("/sdcard");
       // if (!dir.exists())
            //dir.mkdirs();


        File filePath = new File(dir,"Testtt.pdf");

        try{


            documentoPDF.writeTo(new FileOutputStream(filePath));






        } catch (IOException e ){
            e.printStackTrace();
            Log.i("erro", "eroo");

        }
        documentoPDF.close();
    }
}
