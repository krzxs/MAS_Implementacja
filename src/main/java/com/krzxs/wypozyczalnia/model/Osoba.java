package com.krzxs.wypozyczalnia.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.time.Period;

@Entity
public class Osoba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imie;
    private String nazwisko;
    private String email;
    private String telefon;
    private LocalDate dataUrodzenia;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "klient_id")
    private Klient rolaKlienta;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pracownik_id")
    private Pracownik rolaPracownika;

    public Osoba() {
    }

    public Osoba(String imie, String nazwisko, String email, String telefon, LocalDate dataUrodzenia) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.telefon = telefon;
        this.dataUrodzenia = dataUrodzenia;
    }

    public Long getId() {
        return id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public LocalDate getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(LocalDate dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public Klient getRolaKlienta() {
        return rolaKlienta;
    }

    public Pracownik getRolaPracownika() {
        return rolaPracownika;
    }

    public int getWiek() {
        if (dataUrodzenia == null) {
            return 0;
        }
        return Period.between(dataUrodzenia, LocalDate.now()).getYears();
    }

    public String getPelneImie() {
        return imie + " " + nazwisko;
    }

    public void przypiszKlienta(Klient klient) {
        this.rolaKlienta = klient;
        if (klient != null) {
            klient.setOsoba(this);
        }
    }

    public void przypiszPracownika(Pracownik pracownik) {
        this.rolaPracownika = pracownik;
        if (pracownik != null) {
            pracownik.setOsoba(this);
        }
    }
}
