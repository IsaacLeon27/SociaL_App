package com.iwalnexus.tsn.socialapp.Entidades;

public class Fotos {

    private Integer idfotos;
    private String url;
    private Post post;

    public Fotos() {
    }

    public Integer getIdfotos() {
        return idfotos;
    }

    public void setIdfotos(Integer idfotos) {
        this.idfotos = idfotos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
