package com.krzxs.wypozyczalnia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class WypozyczenieDodatek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wypozyczenie_id")
    private Wypozyczenie wypozyczenie;

    @ManyToOne
    @JoinColumn(name = "dodatek_id")
    private Dodatek dodatek;

    private int ilosc;

    public WypozyczenieDodatek() {
    }

    public WypozyczenieDodatek(Wypozyczenie wypozyczenie, Dodatek dodatek, int ilosc) {
        this.wypozyczenie = wypozyczenie;
        this.dodatek = dodatek;
        this.ilosc = ilosc;
    }

    public Long getId() {
        return id;
    }

    public Wypozyczenie getWypozyczenie() {
        return wypozyczenie;
    }

    public void setWypozyczenie(Wypozyczenie wypozyczenie) {
        this.wypozyczenie = wypozyczenie;
    }

    public Dodatek getDodatek() {
        return dodatek;
    }

    public void setDodatek(Dodatek dodatek) {
        this.dodatek = dodatek;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public BigDecimal getKoszt() {
        long dni = (wypozyczenie != null) ? wypozyczenie.getLiczbaDni() : 1;
        return dodatek.getCenaZaDzien()
                .multiply(BigDecimal.valueOf(ilosc))
                .multiply(BigDecimal.valueOf(dni));
    }
}
