package com.example.coletaagro2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coletaagro2.R;
import com.example.coletaagro2.model.MovimentacaoTalhao;

import java.util.List;

public class AdapterMovimentacaoTalhao extends RecyclerView.Adapter<AdapterMovimentacaoTalhao.MyViewHolder>{
    List<MovimentacaoTalhao> movimentacoes;
    Context context;

    public AdapterMovimentacaoTalhao(List<MovimentacaoTalhao> movimentacoes, Context context) {
        this.movimentacoes = movimentacoes;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMovimentacaoTalhao.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_talhao_adapter, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMovimentacaoTalhao.MyViewHolder holder, int position) {
        MovimentacaoTalhao movimentacao = movimentacoes.get(position);

        holder.nome.setText(movimentacao.getNomeTalhao());
        holder.data.setText(movimentacao.getData());
        holder.descricao.setText(movimentacao.getDescricao());

    }

    @Override
    public int getItemCount() {
        return movimentacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome, data, descricao;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textTalhao);
            data = itemView.findViewById(R.id.textData);
            descricao = itemView.findViewById(R.id.textDescricao);


        }

    }
}
