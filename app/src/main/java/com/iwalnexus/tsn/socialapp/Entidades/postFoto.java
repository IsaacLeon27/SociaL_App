package com.iwalnexus.tsn.socialapp.Entidades;

import com.google.gson.annotations.Expose;

import java.util.List;

public class postFoto {

    @Expose
    private int id;
    @Expose
    private List<Fotos> fotos;

    public postFoto(int id, List<Fotos> fotos) {
        this.id = id;
        this.fotos = fotos;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Fotos> getFotos() {
        return fotos;
    }

    public void setFotos(List<Fotos> fotos) {
        this.fotos = fotos;
    }

}
