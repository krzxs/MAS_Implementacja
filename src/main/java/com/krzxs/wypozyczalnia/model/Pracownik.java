package com.krzxs.wypozyczalnia.model;

import com.krzxs.wypozyczalnia.model.enums.Stanowisko;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pracownik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numerPracownika;

    @Enumerated(EnumType.STRING)
    private Stanowisko stanowisko;

    private LocalDate dataZatrudnienia;

    private String pin;

    @OneToOne(mappedBy = "rolaPracownika")
    private Osoba osoba;

    @ManyToOne
    @JoinColumn(name = "oddzial_id")
    private Oddzial oddzial;

    @OneToMany(mappedBy = "pracownik")
    private List<Wypozyczenie> obslugiwaneWypozyczenia = new ArrayList<>();

    public Pracownik() {
    }

    public Pracownik(String numerPracownika, Stanowisko stanowisko, LocalDate dataZatrudnienia) {
        this.numerPracownika = numerPracownika;
        this.stanowisko = stanowisko;
        this.dataZatrudnienia = dataZatrudnienia;
    }

    public Long getId() {
        return id;
    }

    public String getNumerPracownika() {
        return numerPracownika;
    }

    public void setNumerPracownika(String numerPracownika) {
        this.numerPracownika = numerPracownika;
    }

    public Stanowisko getStanowisko() {
        return stanowisko;
    }

    public void setStanowisko(Stanowisko stanowisko) {
        this.stanowisko = stanowisko;
    }

    public LocalDate getDataZatrudnienia() {
        return dataZatrudnienia;
    }

    public void setDataZatrudnienia(LocalDate dataZatrudnienia) {
        this.dataZatrudnienia = dataZatrudnienia;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    public Oddzial getOddzial() {
        return oddzial;
    }

    public void setOddzial(Oddzial oddzial) {
        this.oddzial = oddzial;
    }

    public List<Wypozyczenie> getObslugiwaneWypozyczenia() {
        return obslugiwaneWypozyczenia;
    }

    public void dodajObsluga(Wypozyczenie wypozyczenie) {
        obslugiwaneWypozyczenia.add(wypozyczenie);
        wypozyczenie.setPracownik(this);
    }
}
