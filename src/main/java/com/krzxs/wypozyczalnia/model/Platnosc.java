package com.krzxs.wypozyczalnia.model;

import com.krzxs.wypozyczalnia.model.enums.MetodaPlatnosci;
import com.krzxs.wypozyczalnia.model.enums.StatusPlatnosci;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Platnosc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal kwota;
    private LocalDateTime dataPlatnosci;

    @Enumerated(EnumType.STRING)
    private MetodaPlatnosci metoda;

    @Enumerated(EnumType.STRING)
    private StatusPlatnosci status;

    @ManyToOne
    @JoinColumn(name = "wypozyczenie_id")
    private Wypozyczenie wypozyczenie;

    public Platnosc() {
    }

    public Platnosc(BigDecimal kwota, LocalDateTime dataPlatnosci, MetodaPlatnosci metoda, StatusPlatnosci status) {
        this.kwota = kwota;
        this.dataPlatnosci = dataPlatnosci;
        this.metoda = metoda;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getKwota() {
        return kwota;
    }

    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }

    public LocalDateTime getDataPlatnosci() {
        return dataPlatnosci;
    }

    public void setDataPlatnosci(LocalDateTime dataPlatnosci) {
        this.dataPlatnosci = dataPlatnosci;
    }

    public MetodaPlatnosci getMetoda() {
        return metoda;
    }

    public void setMetoda(MetodaPlatnosci metoda) {
        this.metoda = metoda;
    }

    public StatusPlatnosci getStatus() {
        return status;
    }

    public void setStatus(StatusPlatnosci status) {
        this.status = status;
    }

    public Wypozyczenie getWypozyczenie() {
        return wypozyczenie;
    }

    public void setWypozyczenie(Wypozyczenie wypozyczenie) {
        this.wypozyczenie = wypozyczenie;
    }
}
