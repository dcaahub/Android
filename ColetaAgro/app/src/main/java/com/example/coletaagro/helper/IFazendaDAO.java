package com.example.coletaagro.helper;

import com.example.coletaagro.model.Fazenda;


import java.util.List;

public interface IFazendaDAO {

    public boolean salvar(Fazenda fazenda);
    public boolean atualizar(Fazenda fazenda);
    public boolean deletar(Fazenda fazenda);
    public List<Fazenda> listar();

}
