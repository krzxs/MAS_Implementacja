package com.krzxs.wypozyczalnia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class KategoriaPojazdu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nazwa;
    private String opis;
    private BigDecimal mnoznikCeny;

    @OneToMany(mappedBy = "kategoria")
    private List<Pojazd> pojazdy = new ArrayList<>();

    public KategoriaPojazdu() {
    }

    public KategoriaPojazdu(String nazwa, String opis, BigDecimal mnoznikCeny) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.mnoznikCeny = mnoznikCeny;
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

    public BigDecimal getMnoznikCeny() {
        return mnoznikCeny;
    }

    public void setMnoznikCeny(BigDecimal mnoznikCeny) {
        this.mnoznikCeny = mnoznikCeny;
    }

    public List<Pojazd> getPojazdy() {
        return pojazdy;
    }
}
