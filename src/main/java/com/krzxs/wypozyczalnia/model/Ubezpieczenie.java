package com.krzxs.wypozyczalnia.model;

import com.krzxs.wypozyczalnia.model.enums.TypUbezpieczenia;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ubezpieczenie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypUbezpieczenia typ;

    private String zakres;
    private BigDecimal skladkaDzienna;

    @OneToMany(mappedBy = "ubezpieczenie")
    private List<Wypozyczenie> wypozyczenia = new ArrayList<>();

    public Ubezpieczenie() {
    }

    public Ubezpieczenie(TypUbezpieczenia typ, String zakres, BigDecimal skladkaDzienna) {
        this.typ = typ;
        this.zakres = zakres;
        this.skladkaDzienna = skladkaDzienna;
    }

    public Long getId() {
        return id;
    }

    public TypUbezpieczenia getTyp() {
        return typ;
    }

    public void setTyp(TypUbezpieczenia typ) {
        this.typ = typ;
    }

    public String getZakres() {
        return zakres;
    }

    public void setZakres(String zakres) {
        this.zakres = zakres;
    }

    public BigDecimal getSkladkaDzienna() {
        return skladkaDzienna;
    }

    public void setSkladkaDzienna(BigDecimal skladkaDzienna) {
        this.skladkaDzienna = skladkaDzienna;
    }

    public List<Wypozyczenie> getWypozyczenia() {
        return wypozyczenia;
    }
}