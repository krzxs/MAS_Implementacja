package com.krzxs.wypozyczalnia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Dodatek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nazwa;
    private String opis;
    private BigDecimal cenaZaDzien;

    public Dodatek() {
    }

    public Dodatek(String nazwa, String opis, BigDecimal cenaZaDzien) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.cenaZaDzien = cenaZaDzien;
    }

    public Long getId() {
        return id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public BigDecimal getCenaZaDzien() {
        return cenaZaDzien;
    }

    public void setCenaZaDzien(BigDecimal cenaZaDzien) {
        this.cenaZaDzien = cenaZaDzien;
    }
}
