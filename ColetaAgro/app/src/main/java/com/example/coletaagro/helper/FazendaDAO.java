package com.example.coletaagro.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.coletaagro.model.Fazenda;


import java.util.ArrayList;
import java.util.List;

public class FazendaDAO implements IFazendaDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public FazendaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Fazenda fazenda) {
        ContentValues cv = new ContentValues();
        cv.put("nome", fazenda.getNomeFazenda());

        try{
            escreve.insert(DbHelper.TABELA_FAZENDA, null, cv);
            Log.i("INFO", " Fazenda salva com sucesso");
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar Fazenda " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Fazenda fazenda) {
        ContentValues cv = new ContentValues();
        cv.put("nome", fazenda.getNomeFazenda());

        try{
            String[] args = {fazenda.getId().toString()};
            escreve.update(DbHelper.TABELA_FAZENDA, cv, "id=?", args);

            Log.i("INFO", " Tarefa atualizada com sucesso");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar tarefa " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Fazenda fazenda) {

        try{
            String[] args = {fazenda.getId().toString()};
            escreve.delete(DbHelper.TABELA_FAZENDA, "id=?", args);

            Log.i("INFO", " Tarefa removida com sucesso");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover tarefa " + e.getMessage());
            return false;
        }


        return true;
    }

    @Override
    public List<Fazenda> listar() {
        List<Fazenda> fazendas = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_FAZENDA + " ;";
        Cursor c = le.rawQuery(sql, null);

        while(c.moveToNext()){

            Fazenda fazenda = new Fazenda();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeFazenda = c.getString(c.getColumnIndex("nome"));


            fazenda.setId(id);
            fazenda.setNomeFazenda(nomeFazenda);

            fazendas.add(fazenda);
        }

        return fazendas;
    }
}
