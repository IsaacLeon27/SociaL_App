package com.iwalnexus.tsn.socialapp.Entidades;


public class Amigos {


    private Integer idamigos;
    private int estado;
    private Usuario emisor;
    private Usuario receptor;

    public Amigos() {
    }

    public Integer getIdamigos() {
        return idamigos;
    }

    public void setIdamigos(Integer idamigos) {
        this.idamigos = idamigos;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }
}
