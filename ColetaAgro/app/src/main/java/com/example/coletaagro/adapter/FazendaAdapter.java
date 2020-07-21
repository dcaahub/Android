package com.example.coletaagro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coletaagro.R;
import com.example.coletaagro.model.Fazenda;

import java.util.List;

public class FazendaAdapter extends RecyclerView.Adapter<FazendaAdapter.MyViewHolder> {
    private List<Fazenda> listaFazenda;

    public FazendaAdapter(List<Fazenda> lista) {
        this.listaFazenda = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_fazenda_adapter, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Fazenda fazenda = listaFazenda.get(position);
        holder.fazenda.setText(fazenda.getNomeFazenda());

    }

    @Override
    public int getItemCount() {
        return this.listaFazenda.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fazenda;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fazenda = itemView.findViewById(R.id.textFazenda);
        }
    }
}
