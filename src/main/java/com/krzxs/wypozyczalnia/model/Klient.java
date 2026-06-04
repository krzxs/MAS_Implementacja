package com.krzxs.wypozyczalnia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Klient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numerKlienta;
    private LocalDate dataRejestracji;
    private String numerPrawaJazdy;
    private LocalDate dataWaznosciPrawaJazdy;

    @OneToOne(mappedBy = "rolaKlienta")
    private Osoba osoba;

    @OneToMany(mappedBy = "klient")
    private List<Rezerwacja> rezerwacje = new ArrayList<>();

    @OneToMany(mappedBy = "klient")
    private List<Wypozyczenie> wypozyczenia = new ArrayList<>();

    public Klient() {
    }

    public Klient(String numerKlienta, LocalDate dataRejestracji, String numerPrawaJazdy, LocalDate dataWaznosciPrawaJazdy) {
        this.numerKlienta = numerKlienta;
        this.dataRejestracji = dataRejestracji;
        this.numerPrawaJazdy = numerPrawaJazdy;
        this.dataWaznosciPrawaJazdy = dataWaznosciPrawaJazdy;
    }

    public Long getId() {
        return id;
    }

    public String getNumerKlienta() {
        return numerKlienta;
    }

    public void setNumerKlienta(String numerKlienta) {
        this.numerKlienta = numerKlienta;
    }

    public LocalDate getDataRejestracji() {
        return dataRejestracji;
    }

    public void setDataRejestracji(LocalDate dataRejestracji) {
        this.dataRejestracji = dataRejestracji;
    }

    public String getNumerPrawaJazdy() {
        return numerPrawaJazdy;
    }

    public void setNumerPrawaJazdy(String numerPrawaJazdy) {
        this.numerPrawaJazdy = numerPrawaJazdy;
    }

    public LocalDate getDataWaznosciPrawaJazdy() {
        return dataWaznosciPrawaJazdy;
    }

    public void setDataWaznosciPrawaJazdy(LocalDate d) {
        this.dataWaznosciPrawaJazdy = d;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    public List<Rezerwacja> getRezerwacje() {
        return rezerwacje;
    }

    public List<Wypozyczenie> getWypozyczenia() {
        return wypozyczenia;
    }

    public boolean prawoJazdyWazne(LocalDate doDnia) {
        return dataWaznosciPrawaJazdy != null && !dataWaznosciPrawaJazdy.isBefore(doDnia);
    }

    public void dodajWypozyczenie(Wypozyczenie wypozyczenie) {
        wypozyczenia.add(wypozyczenie);
        wypozyczenie.setKlient(this);
    }

    public void dodajRezerwacje(Rezerwacja rezerwacja) {
        rezerwacje.add(rezerwacja);
        rezerwacja.setKlient(this);
    }
}
