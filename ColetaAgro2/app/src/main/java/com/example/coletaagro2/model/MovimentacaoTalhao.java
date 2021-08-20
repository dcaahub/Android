package com.example.coletaagro2.model;

import android.net.Uri;

import com.example.coletaagro2.config.ConfiguracaoFirebase;
import com.example.coletaagro2.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MovimentacaoTalhao {

    private String identificador;
    private String data;
    private String nomeTalhao;
    private String descricao;
    private String key;
    private String legenda1, legenda2, legenda3;
    //private Uri imagem1, imagem2, imagem3;


    public MovimentacaoTalhao() {
    }

    public void salvar(String idFazenda, String idTalhao){
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        //String mesAno = DateCustom.mesAnoDataEscolhida(dataEscolhida);
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("talhao")
                .child(idFazenda)
                //.child(idTalhao)
                .push()
                .setValue(this);
    }



    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getLegenda1() {
        return legenda1;
    }

    public void setLegenda1(String legenda1) {
        this.legenda1 = legenda1;
    }

    public String getLegenda2() {
        return legenda2;
    }

    public void setLegenda2(String legenda2) {
        this.legenda2 = legenda2;
    }

    public String getLegenda3() {
        return legenda3;
    }

    public void setLegenda3(String legenda3) {
        this.legenda3 = legenda3;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNomeTalhao() {
        return nomeTalhao;
    }

    public void setNomeTalhao(String nomeTalhao) {
        this.nomeTalhao = nomeTalhao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
