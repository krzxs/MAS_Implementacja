package com.krzxs.wypozyczalnia.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Motocykl extends Pojazd {

    private int pojemnoscSilnika;

    public Motocykl() {
        super();
    }

    public Motocykl(String numerRejestracyjny, String marka, String model, int rokProdukcji, int przebieg, BigDecimal stawkaDzienna, int pojemnoscSilnika) {
        super(numerRejestracyjny, marka, model, rokProdukcji, przebieg, stawkaDzienna);
        this.pojemnoscSilnika = pojemnoscSilnika;
    }

    @Override
    public String getTypPojazdu() {
        return "Motocykl";
    }

    public int getPojemnoscSilnika() {
        return pojemnoscSilnika;
    }

    public void setPojemnoscSilnika(int pojemnoscSilnika) {
        this.pojemnoscSilnika = pojemnoscSilnika;
    }
}
