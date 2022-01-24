package com.example.teste02.SistemData;

public class Viagem {
    Localizacao destino;
    Localizacao atual;
    double distancia;
    Restaurante restaurante;
    boolean terminada;

    public Viagem(Localizacao destino, Localizacao atual, double distancia, Restaurante restaurante, boolean terminada) {
        this.destino = destino;
        this.atual = atual;
        this.distancia = distancia;
        this.restaurante = restaurante;
        this.terminada = terminada;
    }

    public Localizacao getDestino() {
        return destino;
    }

    public void setDestino(Localizacao destino) {
        this.destino = destino;
    }

    public Localizacao getAtual() {
        return atual;
    }

    public void setAtual(Localizacao atual) {
        this.atual = atual;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public boolean isTerminada() {
        return terminada;
    }

    public void setTerminada(boolean terminada) {
        this.terminada = terminada;
    }
}
