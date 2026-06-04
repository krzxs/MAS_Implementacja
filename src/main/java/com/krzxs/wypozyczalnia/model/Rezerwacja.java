package com.krzxs.wypozyczalnia.model;

import com.krzxs.wypozyczalnia.model.enums.StatusRezerwacji;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Rezerwacja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numerRezerwacji;
    private LocalDateTime dataUtworzenia;
    private LocalDate planowanaDataOdbioru;
    private LocalDate planowanaDataZwrotu;

    @Enumerated(EnumType.STRING)
    private StatusRezerwacji status = StatusRezerwacji.OCZEKUJACA;

    @ManyToOne
    @JoinColumn(name = "klient_id")
    private Klient klient;

    @ManyToOne
    @JoinColumn(name = "pojazd_id")
    private Pojazd pojazd;

    @OneToOne(mappedBy = "rezerwacja")
    private Wypozyczenie wypozyczenie;

    public Rezerwacja() {
    }

    public Rezerwacja(String numerRezerwacji, LocalDateTime dataUtworzenia, LocalDate planowanaDataOdbioru, LocalDate planowanaDataZwrotu) {
        this.numerRezerwacji = numerRezerwacji;
        this.dataUtworzenia = dataUtworzenia;
        this.planowanaDataOdbioru = planowanaDataOdbioru;
        this.planowanaDataZwrotu = planowanaDataZwrotu;
    }

    public Long getId() {
        return id;
    }

    public String getNumerRezerwacji() {
        return numerRezerwacji;
    }

    public void setNumerRezerwacji(String numerRezerwacji) {
        this.numerRezerwacji = numerRezerwacji;
    }

    public LocalDateTime getDataUtworzenia() {
        return dataUtworzenia;
    }

    public void setDataUtworzenia(LocalDateTime dataUtworzenia) {
        this.dataUtworzenia = dataUtworzenia;
    }

    public LocalDate getPlanowanaDataOdbioru() {
        return planowanaDataOdbioru;
    }

    public void setPlanowanaDataOdbioru(LocalDate d) {
        this.planowanaDataOdbioru = d;
    }

    public LocalDate getPlanowanaDataZwrotu() {
        return planowanaDataZwrotu;
    }

    public void setPlanowanaDataZwrotu(LocalDate d) {
        this.planowanaDataZwrotu = d;
    }

    public StatusRezerwacji getStatus() {
        return status;
    }

    public void setStatus(StatusRezerwacji status) {
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

    public Wypozyczenie getWypozyczenie() {
        return wypozyczenie;
    }

    public void setWypozyczenie(Wypozyczenie wypozyczenie) {
        this.wypozyczenie = wypozyczenie;
    }

    public void potwierdz() {
        this.status = StatusRezerwacji.POTWIERDZONA;
    }

    public void anuluj() {
        this.status = StatusRezerwacji.ANULOWANA;
    }
}
