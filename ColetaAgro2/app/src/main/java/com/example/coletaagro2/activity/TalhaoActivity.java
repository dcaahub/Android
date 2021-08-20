package com.example.coletaagro2.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.coletaagro2.R;
import com.example.coletaagro2.config.ConfiguracaoFirebase;
import com.example.coletaagro2.helper.DateCustom;
import com.example.coletaagro2.model.Movimentacao;
import com.example.coletaagro2.model.MovimentacaoTalhao;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Random;

public class TalhaoActivity extends AppCompatActivity {
    private TextInputEditText campoId, campoData, campoTalhao,  campoDescricao;
    private TextInputEditText campoLegenda1, campoLegenda2, campoLegenda3;
    private ImageView imagemTalhao1, imagemTalhao2, imagemTalhao3;

    private MovimentacaoTalhao movimentacaoTalhao;
    private Movimentacao movimentacao;

    private static final int SELECAO_GALERIA_1 = 1;
    private static final int SELECAO_GALERIA_2 = 2;
    private static final int SELECAO_GALERIA_3 = 3;

//    Drawable imagemFundo = getResources().getDrawable(R.drawable.montanha);
   // Bitmap imagemFundoBitmap = ((BitmapDrawable) imagemFundo).getBitmap();


    private StorageReference storageReference = ConfiguracaoFirebase.getFirebaseStorage();

    private StorageReference imagemRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talhao);

        campoId = findViewById(R.id.editIdTalhao);
        campoData = findViewById(R.id.editData);
        campoTalhao = findViewById(R.id.editNomeTalhao);
        campoDescricao = findViewById(R.id.editDescricao);

        campoLegenda1 = findViewById(R.id.editLegenda1);
        campoLegenda2 = findViewById(R.id.editLegenda2);
        campoLegenda3 = findViewById(R.id.editLegenda3);

        imagemTalhao1 = findViewById(R.id.imageView1);
        imagemTalhao2 = findViewById(R.id.imageView2);
        imagemTalhao3 = findViewById(R.id.imageView3);



        movimentacao = (Movimentacao) getIntent().getSerializableExtra("movimentacao1");

        //preenche o campo com a data atual
        campoData.setText(DateCustom.dataAtual());
        Random random = new Random();
        campoId.setText(DateCustom.dataParaId()+random.nextInt());

        imagemTalhao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                if ( i.resolveActivity(getPackageManager()) != null ){
                    startActivityForResult(i, SELECAO_GALERIA_1 );
                }
            }
        });

        imagemTalhao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                if ( i.resolveActivity(getPackageManager()) != null ){
                    startActivityForResult(i, SELECAO_GALERIA_2 );
                }
            }
        });

        imagemTalhao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                if ( i.resolveActivity(getPackageManager()) != null ){
                    startActivityForResult(i, SELECAO_GALERIA_3 );
                }
            }
        });

    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean status = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return status;

    }

    public void salvarTalhao(View view){
        if(validarCamposTalhao()){
            if (isConnected(view.getContext())) {
                movimentacaoTalhao = new MovimentacaoTalhao();
                String idTalhao = campoId.getText().toString();

                movimentacaoTalhao.setIdentificador(campoId.getText().toString());
                movimentacaoTalhao.setData(campoData.getText().toString());
                movimentacaoTalhao.setNomeTalhao(campoTalhao.getText().toString());
                movimentacaoTalhao.setDescricao(campoDescricao.getText().toString());
                movimentacaoTalhao.setLegenda1(campoLegenda1.getText().toString());
                movimentacaoTalhao.setLegenda2(campoLegenda2.getText().toString());
                movimentacaoTalhao.setLegenda3(campoLegenda3.getText().toString());

                movimentacaoTalhao.salvar(movimentacao.getIdentificador(), idTalhao);

                subirImagem(((BitmapDrawable) imagemTalhao1.getDrawable()).getBitmap(), SELECAO_GALERIA_1, idTalhao);
                subirImagem(((BitmapDrawable) imagemTalhao2.getDrawable()).getBitmap(), SELECAO_GALERIA_2, idTalhao);
                subirImagem(((BitmapDrawable) imagemTalhao3.getDrawable()).getBitmap(), SELECAO_GALERIA_3, idTalhao);

                finish();
            }
            else{

            }
        }

    }


    public Boolean validarCamposTalhao(){
        String textoData = campoData.getText().toString();
        String textoTalhao = campoTalhao.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();
        String textoId = campoId.getText().toString();


        if(!textoId.isEmpty()) {
            if (!textoData.isEmpty()) {
                if (!textoTalhao.isEmpty()) {
                    if (!textoDescricao.isEmpty()){
                        if(imagemTalhao1.getDrawable() != null) {
                            if(imagemTalhao2.getDrawable() != null) {
                                if(imagemTalhao3.getDrawable() != null) {

                                    return true;

                                }
                                else{
                                    Toast.makeText(TalhaoActivity.this, "Preencha a imagem 3!", Toast.LENGTH_SHORT).show();

                                }


                            }
                            else{
                                Toast.makeText(TalhaoActivity.this, "Preencha a imagem 2!", Toast.LENGTH_SHORT).show();

                            }

                        }
                        else{
                            Toast.makeText(TalhaoActivity.this, "Preencha a imagem 1!", Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        Toast.makeText(TalhaoActivity.this, "Preencha a descrição!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(TalhaoActivity.this, "Preencha o Nome do talhão!", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(TalhaoActivity.this, "Preencha a data!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(TalhaoActivity.this, "Preencha o identificador!", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == RESULT_OK ){
            Bitmap imagem = null;

            try {

                Uri localImagemSelecionada = data.getData();
                imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada );

                if ( imagem != null ){

                    switch (requestCode){
                        case SELECAO_GALERIA_1:
                            imagemTalhao1.setImageBitmap( imagem );
                            break;
                        case SELECAO_GALERIA_2:
                            imagemTalhao2.setImageBitmap( imagem );
                            break;
                        case SELECAO_GALERIA_3:
                            imagemTalhao3.setImageBitmap( imagem );
                            break;
                    }


                    /*
                    //Recuperar dados da imagem para o firebase
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos );
                    byte[] dadosImagem = baos.toByteArray();

                    //Salvar imagem no firebase
                    StorageReference imagemRef = storageReference
                            .child("imagens")
                            .child(movimentacao.getIdentificador())
                            .child( campoId.getText().toString() )
                            .child("imagem" + flag + ".jpeg");

                    UploadTask uploadTask = imagemRef.putBytes( dadosImagem );
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TalhaoActivity.this,
                                    "Erro ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(TalhaoActivity.this,
                                    "Sucesso ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });*/

                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void subirImagem(Bitmap imagem, final int flag, final String idTalhao){
        if(imagem != null) {
            Log.i("erro", "Imagem");
            //Recuperar dados da imagem para o firebase
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] dadosImagem = baos.toByteArray();


            //Salvar imagem no firebase
            imagemRef = storageReference
                    .child("imagens")
                    .child(movimentacao.getIdentificador())
                    .child(idTalhao)
                    .child("imagem" + flag + ".jpeg");

            UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TalhaoActivity.this,
                            "Erro ao fazer upload da imagem",
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(TalhaoActivity.this,
                            "Sucesso ao fazer upload da imagem",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }




    }
}