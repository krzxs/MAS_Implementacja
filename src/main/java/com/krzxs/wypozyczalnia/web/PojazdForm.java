package com.krzxs.wypozyczalnia.web;

import com.krzxs.wypozyczalnia.model.enums.TypNadwozia;

import java.math.BigDecimal;

public class PojazdForm {

    // OSOBOWY, DOSTAWCZY, MOTOCYKL
    private String typ;

    private String numerRejestracyjny;
    private String marka;
    private String model;
    private Integer rokProdukcji;
    private Integer przebieg;
    private BigDecimal stawkaDzienna;

    private Long kategoriaId;
    private Long oddzialId;

    // Samochód osobowy
    private Integer liczbaMiejsc;
    private TypNadwozia typNadwozia;

    // Samochód dostawczy
    private Double ladownosc;
    private Double pojemnoscLadunkowa;

    // Motocykl
    private Integer pojemnoscSilnika;

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getNumerRejestracyjny() {
        return numerRejestracyjny;
    }

    public void setNumerRejestracyjny(String numerRejestracyjny) {
        this.numerRejestracyjny = numerRejestracyjny;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getRokProdukcji() {
        return rokProdukcji;
    }

    public void setRokProdukcji(Integer rokProdukcji) {
        this.rokProdukcji = rokProdukcji;
    }

    public Integer getPrzebieg() {
        return przebieg;
    }

    public void setPrzebieg(Integer przebieg) {
        this.przebieg = przebieg;
    }

    public BigDecimal getStawkaDzienna() {
        return stawkaDzienna;
    }

    public void setStawkaDzienna(BigDecimal stawkaDzienna) {
        this.stawkaDzienna = stawkaDzienna;
    }

    public Long getKategoriaId() {
        return kategoriaId;
    }

    public void setKategoriaId(Long kategoriaId) {
        this.kategoriaId = kategoriaId;
    }

    public Long getOddzialId() {
        return oddzialId;
    }

    public void setOddzialId(Long oddzialId) {
        this.oddzialId = oddzialId;
    }

    public Integer getLiczbaMiejsc() {
        return liczbaMiejsc;
    }

    public void setLiczbaMiejsc(Integer liczbaMiejsc) {
        this.liczbaMiejsc = liczbaMiejsc;
    }

    public TypNadwozia getTypNadwozia() {
        return typNadwozia;
    }

    public void setTypNadwozia(TypNadwozia typNadwozia) {
        this.typNadwozia = typNadwozia;
    }

    public Double getLadownosc() {
        return ladownosc;
    }

    public void setLadownosc(Double ladownosc) {
        this.ladownosc = ladownosc;
    }

    public Double getPojemnoscLadunkowa() {
        return pojemnoscLadunkowa;
    }

    public void setPojemnoscLadunkowa(Double pojemnoscLadunkowa) {
        this.pojemnoscLadunkowa = pojemnoscLadunkowa;
    }

    public Integer getPojemnoscSilnika() {
        return pojemnoscSilnika;
    }

    public void setPojemnoscSilnika(Integer pojemnoscSilnika) {
        this.pojemnoscSilnika = pojemnoscSilnika;
    }
}
