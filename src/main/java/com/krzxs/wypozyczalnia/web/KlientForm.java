package com.krzxs.wypozyczalnia.web;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class KlientForm {

    private String imie;
    private String nazwisko;
    private String email;
    private String telefon;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataUrodzenia;

    private String numerPrawaJazdy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataWaznosciPrawaJazdy;

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

    public String getNumerPrawaJazdy() {
        return numerPrawaJazdy;
    }

    public void setNumerPrawaJazdy(String numerPrawaJazdy) {
        this.numerPrawaJazdy = numerPrawaJazdy;
    }

    public LocalDate getDataWaznosciPrawaJazdy() {
        return dataWaznosciPrawaJazdy;
    }

    public void setDataWaznosciPrawaJazdy(LocalDate dataWaznosciPrawaJazdy) {
        this.dataWaznosciPrawaJazdy = dataWaznosciPrawaJazdy;
    }
}
