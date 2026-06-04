package com.krzxs.wypozyczalnia.model;

import com.krzxs.wypozyczalnia.model.enums.TypNadwozia;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Entity
public class SamochodOsobowy extends Pojazd {

    private int liczbaMiejsc;

    @Enumerated(EnumType.STRING)
    private TypNadwozia typNadwozia;

    public SamochodOsobowy() {
        super();
    }

    public SamochodOsobowy(String numerRejestracyjny, String marka, String model, int rokProdukcji, int przebieg, BigDecimal stawkaDzienna, int liczbaMiejsc, TypNadwozia typNadwozia) {
        super(numerRejestracyjny, marka, model, rokProdukcji, przebieg, stawkaDzienna);
        this.liczbaMiejsc = liczbaMiejsc;
        this.typNadwozia = typNadwozia;
    }

    @Override
    public String getTypPojazdu() {
        return "Samochód osobowy";
    }

    public int getLiczbaMiejsc() {
        return liczbaMiejsc;
    }

    public void setLiczbaMiejsc(int liczbaMiejsc) {
        this.liczbaMiejsc = liczbaMiejsc;
    }

    public TypNadwozia getTypNadwozia() {
        return typNadwozia;
    }

    public void setTypNadwozia(TypNadwozia typNadwozia) {
        this.typNadwozia = typNadwozia;
    }
}
