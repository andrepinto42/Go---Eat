package com.example.teste02;


public class Restaurante {
    private String nome;
    private String ID;
    private int priceLevel;
    private float rating;
    private int numberRating;
    private String[] keywords;
    private float latitude;
    private float longitude;

    public Restaurante(String nome, String ID, int priceLevel,
                       float rating, int numberRating, String[] keywords,
                       float latitude, float longitude)
    {
        this.nome = nome;
        this.ID = ID;
        this.priceLevel = priceLevel;
        this.rating = rating;
        this.numberRating = numberRating;
        this.keywords = keywords;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNumberRating() {
        return numberRating;
    }

    public void setNumberRating(int numberRating) {
        this.numberRating = numberRating;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
