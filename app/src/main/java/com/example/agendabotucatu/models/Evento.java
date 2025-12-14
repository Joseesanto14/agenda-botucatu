package com.example.agendabotucatu.models;

public class Evento {

    private String idEvento;
    private String titulo;
    private String descricao;
    private String data;
    private String local;
    private String categoria;
    private String idUsuarioCriador;
    private String imagemUrl;
    private int favoritos;

    public Evento(){

    }

    public Evento(String titulo, String descricao, String data, String local, String categoria, String idUsuarioCriador, String imagemUrl, int favoritos) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.categoria = categoria;
        this.idUsuarioCriador = idUsuarioCriador;
        this.imagemUrl = imagemUrl;
        this.favoritos = favoritos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIdUsuarioCriador() {
        return idUsuarioCriador;
    }

    public void setIdUsuarioCriador(String idUsuarioCriador) {
        this.idUsuarioCriador = idUsuarioCriador;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public int getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(int favoritos) {
        this.favoritos = favoritos;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }
}
