package com.iwalnexus.tsn.socialapp.Entidades;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.util.List;

@Entity(tableName = "Usuario")
public class Usuario {

    @PrimaryKey
    @Expose
    private Integer idusuario;
    @Expose
    private String usuario;
    @Expose
    private String password;
    @Ignore
    private List<Post> postList;
    @Ignore
    private List<Amigos> amigosList;
    @Ignore
    private List<Amigos> amigosList1;
    @Ignore
    private List<Likes> likesList;

    private String icon;
    private String pushId;

    public Usuario() {
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public List<Amigos> getAmigosList() {
        return amigosList;
    }

    public void setAmigosList(List<Amigos> amigosList) {
        this.amigosList = amigosList;
    }

    public List<Amigos> getAmigosList1() {
        return amigosList1;
    }

    public void setAmigosList1(List<Amigos> amigosList1) {
        this.amigosList1 = amigosList1;
    }

    public List<Likes> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<Likes> likesList) {
        this.likesList = likesList;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
