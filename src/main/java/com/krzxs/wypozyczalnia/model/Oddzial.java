package com.krzxs.wypozyczalnia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Oddzial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nazwa;
    private String adres;
    private String miasto;

    @OneToMany(mappedBy = "oddzial")
    private List<Pojazd> flota = new ArrayList<>();

    @OneToMany(mappedBy = "oddzial")
    private List<Pracownik> pracownicy = new ArrayList<>();

    public Oddzial() {
    }

    public Oddzial(String nazwa, String adres, String miasto) {
        this.nazwa = nazwa;
        this.adres = adres;
        this.miasto = miasto;
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

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public List<Pojazd> getFlota() {
        return flota;
    }

    public List<Pracownik> getPracownicy() {
        return pracownicy;
    }

    public List<Pojazd> dostepnePojazdy(LocalDate dataOd, LocalDate dataDo) {
        return flota.stream().filter(p -> p.isDostepny(dataOd, dataDo)).toList();
    }
}
