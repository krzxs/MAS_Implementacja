package com.krzxs.wypozyczalnia.model.enums;

public enum Stanowisko {
    KONSULTANT("Konsultant"),
    KIEROWNIK("Kierownik");

    private final String opis;

    Stanowisko(String opis) {
        this.opis = opis;
    }

    public String getOpis() {
        return opis;
    }

    @Override
    public String toString() {
        return getOpis();
    }
}
