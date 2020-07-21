package com.iwalnexus.tsn.socialapp.Entidades;

public class NotificationMessage {

    private String token;
    private String title;
    private String body;
    private String notidicationId;


    public NotificationMessage() {
    }

    public NotificationMessage(String token, String title, String body, String notidicationId) {
        this.token = token;
        this.title = title;
        this.body = body;
        this.notidicationId = notidicationId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNotidicationId() {
        return notidicationId;
    }

    public void setNotidicationId(String notidicationId) {
        this.notidicationId = notidicationId;
    }



}
