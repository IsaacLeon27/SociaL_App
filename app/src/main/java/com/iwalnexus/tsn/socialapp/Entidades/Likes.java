package com.iwalnexus.tsn.socialapp.Entidades;

public class Likes {

    private Integer idlikes;
    private Post post;
    private Usuario usuario;

    public Likes() {
    }

    public Integer getIdlikes() {
        return idlikes;
    }

    public void setIdlikes(Integer idlikes) {
        this.idlikes = idlikes;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
