package com.krzxs.wypozyczalnia.model;

import com.krzxs.wypozyczalnia.model.enums.StatusWypozyczenia;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Wypozyczenie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numerWypozyczenia;
    private LocalDate dataOd;
    private LocalDate dataDo;

    @Enumerated(EnumType.STRING)
    private StatusWypozyczenia status = StatusWypozyczenia.AKTYWNE;

    @ManyToOne
    @JoinColumn(name = "klient_id")
    private Klient klient;

    @ManyToOne
    @JoinColumn(name = "pojazd_id")
    private Pojazd pojazd;

    @ManyToOne
    @JoinColumn(name = "pracownik_id")
    private Pracownik pracownik;

    @ManyToOne
    @JoinColumn(name = "ubezpieczenie_id")
    private Ubezpieczenie ubezpieczenie;

    @OneToMany(mappedBy = "wypozyczenie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Platnosc> platnosci = new ArrayList<>();

    @OneToMany(mappedBy = "wypozyczenie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Szkoda> szkody = new ArrayList<>();

    @OneToMany(mappedBy = "wypozyczenie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WypozyczenieDodatek> wypozyczenieDodatki = new ArrayList<>();

    public Wypozyczenie() {
    }

    public Wypozyczenie(String numerWypozyczenia, LocalDate dataOd, LocalDate dataDo, StatusWypozyczenia status) {
        this.numerWypozyczenia = numerWypozyczenia;
        this.dataOd = dataOd;
        this.dataDo = dataDo;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getNumerWypozyczenia() {
        return numerWypozyczenia;
    }

    public void setNumerWypozyczenia(String numerWypozyczenia) {
        this.numerWypozyczenia = numerWypozyczenia;
    }

    public LocalDate getDataOd() {
        return dataOd;
    }

    public void setDataOd(LocalDate dataOd) {
        this.dataOd = dataOd;
    }

    public LocalDate getDataDo() {
        return dataDo;
    }

    public void setDataDo(LocalDate dataDo) {
        this.dataDo = dataDo;
    }

    public StatusWypozyczenia getStatus() {
        return status;
    }

    public void setStatus(StatusWypozyczenia status) {
        this.status = status;
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    public Pojazd getPojazd() {
        return pojazd;
    }

    public void setPojazd(Pojazd pojazd) {
        this.pojazd = pojazd;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public Ubezpieczenie getUbezpieczenie() {
        return ubezpieczenie;
    }

    public void setUbezpieczenie(Ubezpieczenie ubezpieczenie) {
        this.ubezpieczenie = ubezpieczenie;
    }

    public List<Platnosc> getPlatnosci() {
        return platnosci;
    }

    public List<Szkoda> getSzkody() {
        return szkody;
    }

    public List<WypozyczenieDodatek> getWypozyczenieDodatki() {
        return wypozyczenieDodatki;
    }

    public long getLiczbaDni() {
        if (dataOd == null || dataDo == null) {
            return 1;
        }
        long dni = ChronoUnit.DAYS.between(dataOd, dataDo);
        return dni <= 0 ? 1 : dni;
    }

    public BigDecimal getKosztCalkowity() {
        BigDecimal dni = BigDecimal.valueOf(getLiczbaDni());
        BigDecimal bazowy = pojazd.getStawkaDzienna()
                .multiply(pojazd.getKategoria().getMnoznikCeny())
                .multiply(dni);
        BigDecimal kosztDodatkow = wypozyczenieDodatki.stream()
                .map(WypozyczenieDodatek::getKoszt)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal kosztUbezpieczenia = (ubezpieczenie != null)
                ? ubezpieczenie.getSkladkaDzienna().multiply(dni)
                : BigDecimal.ZERO;
        return bazowy.add(kosztDodatkow).add(kosztUbezpieczenia);
    }

    public void dodajDodatek(Dodatek dodatek, int ilosc) {
        WypozyczenieDodatek pozycja = new WypozyczenieDodatek(this, dodatek, ilosc);
        wypozyczenieDodatki.add(pozycja);
    }

    public void dodajPlatnosc(Platnosc platnosc) {
        platnosci.add(platnosc);
        platnosc.setWypozyczenie(this);
    }

    public void dodajSzkode(Szkoda szkoda) {
        szkody.add(szkoda);
        szkoda.setWypozyczenie(this);
    }

    public void zakoncz() {
        this.status = StatusWypozyczenia.ZAKONCZONE;
    }
}
