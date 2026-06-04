package com.krzxs.wypozyczalnia.model;

import com.krzxs.wypozyczalnia.model.enums.StatusSzkody;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Szkoda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String opis;
    private LocalDate dataZgloszenia;
    private BigDecimal kosztNaprawy;

    @Enumerated(EnumType.STRING)
    private StatusSzkody status = StatusSzkody.ZGLOSZONA;

    @ManyToOne
    @JoinColumn(name = "wypozyczenie_id")
    private Wypozyczenie wypozyczenie;

    public Szkoda() {
    }

    public Szkoda(String opis, LocalDate dataZgloszenia, BigDecimal kosztNaprawy, StatusSzkody status) {
        this.opis = opis;
        this.dataZgloszenia = dataZgloszenia;
        this.kosztNaprawy = kosztNaprawy;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDate getDataZgloszenia() {
        return dataZgloszenia;
    }

    public void setDataZgloszenia(LocalDate dataZgloszenia) {
        this.dataZgloszenia = dataZgloszenia;
    }

    public BigDecimal getKosztNaprawy() {
        return kosztNaprawy;
    }

    public void setKosztNaprawy(BigDecimal kosztNaprawy) {
        this.kosztNaprawy = kosztNaprawy;
    }

    public StatusSzkody getStatus() {
        return status;
    }

    public void setStatus(StatusSzkody status) {
        this.status = status;
    }

    public Wypozyczenie getWypozyczenie() {
        return wypozyczenie;
    }

    public void setWypozyczenie(Wypozyczenie wypozyczenie) {
        this.wypozyczenie = wypozyczenie;
    }
}
