package com.example.agendabotucatu.models;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nome;
    private String email;
    private String id;
    private String imagem;
    private String tipo;


    public Usuario(){

    }

    public Usuario(String nome, String email, String id){
        this.nome = nome;
        this.email = email;
        this.id = id;
        this.imagem = null;
        this.tipo = "Comum";

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
