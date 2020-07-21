package com.example.coletaagro.model;

import java.io.Serializable;

public class Fazenda implements Serializable {
    private Long id;
    private String nomeFazenda;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFazenda() {
        return nomeFazenda;
    }

    public void setNomeFazenda(String nomeFazenda) {
        this.nomeFazenda = nomeFazenda;
    }
}
