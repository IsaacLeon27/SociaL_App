package com.iwalnexus.tsn.socialapp.Entidades;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Post {


    private Integer idpost;
    @Expose
    private String contenido;
    @Expose
    private Usuario usuario;
    private List<Fotos> fotosList;
    private List<Likes> likesList;

    public Post() {
    }

    public Integer getIdpost() {
        return idpost;
    }

    public void setIdpost(Integer idpost) {
        this.idpost = idpost;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Fotos> getFotosList() {
        return fotosList;
    }

    public void setFotosList(List<Fotos> fotosList) {
        this.fotosList = fotosList;
    }

    public List<Likes> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<Likes> likesList) {
        this.likesList = likesList;
    }
}
