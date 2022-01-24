package com.example.teste02.SistemData;


public class Restaurante {
    private String nome;
    private String ID;
    private float rating;
    private Localizacao localizacao;
    public Restaurante(String nome, String ID,
                       float rating,  String[] keywords,
                       float latitude, float longitude)
    {
        this.nome = nome;
        this.ID = ID;
        this.rating = rating;
        this.localizacao = new Localizacao(0,latitude,longitude,keywords);
    }

    public Restaurante(String placeName, String vicinity, float latitude, float longitude, String reference) {
        this.nome = placeName;
        this.ID = reference;
        this.localizacao = new Localizacao(0,latitude,longitude,new String[]{});
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }
}
