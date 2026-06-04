package com.krzxs.wypozyczalnia.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class SamochodDostawczy extends Pojazd {

    private double ladownosc;
    private double pojemnoscLadunkowa;

    public SamochodDostawczy() {
        super();
    }

    public SamochodDostawczy(String numerRejestracyjny, String marka, String model, int rokProdukcji, int przebieg, BigDecimal stawkaDzienna, double ladownosc, double pojemnoscLadunkowa) {
        super(numerRejestracyjny, marka, model, rokProdukcji, przebieg, stawkaDzienna);
        this.ladownosc = ladownosc;
        this.pojemnoscLadunkowa = pojemnoscLadunkowa;
    }

    @Override
    public String getTypPojazdu() {
        return "Samochód dostawczy";
    }

    public double getLadownosc() {
        return ladownosc;
    }

    public void setLadownosc(double ladownosc) {
        this.ladownosc = ladownosc;
    }

    public double getPojemnoscLadunkowa() {
        return pojemnoscLadunkowa;
    }

    public void setPojemnoscLadunkowa(double pojemnoscLadunkowa) {
        this.pojemnoscLadunkowa = pojemnoscLadunkowa;
    }
}
