package com.example.teste02.SistemData;

public class Localizacao {
    int id_localizacao;
    float latitude;
    float longitude;
    String[] categorias;

    public Localizacao(int id_localizacao, float latitude, float longitude, String[] categorias) {
        this.id_localizacao = id_localizacao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categorias = categorias;
    }

    public int getId_localizacao() {
        return id_localizacao;
    }

    public void setId_localizacao(int id_localizacao) {
        this.id_localizacao = id_localizacao;
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

    public String[] getCategorias() {
        return categorias;
    }

    public void setCategorias(String[] categorias) {
        this.categorias = categorias;
    }
}
