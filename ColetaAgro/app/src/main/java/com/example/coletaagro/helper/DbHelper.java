package com.example.coletaagro.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String NOME_DB = "DB_COLETAS";
    public static String TABELA_FAZENDA = "fazenda";
    public static String TABELA_TALHAO = "talhao";
    public static String TABELA_PONTO = "ponto";
    public static String TABELA_INSETO = "inseto";
    public static String TABELA_PLANTA = "planta";



    public DbHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = " CREATE TABLE IF NOT EXISTS " + TABELA_FAZENDA
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + " nome TEXT NOT NULL ); ";
        sql += " CREATE TABLE IF NOT EXISTS " + TABELA_TALHAO
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + " nome TEXT NOT NULL," +
                " recomendacao TEXT NOT NULL, fazendaID INTEGER DEFAULT NULL, " +
                "FOREIGN KEY('fazendaID') REFERENCES "+TABELA_FAZENDA+"(id)); ";

        try{
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela");

        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
