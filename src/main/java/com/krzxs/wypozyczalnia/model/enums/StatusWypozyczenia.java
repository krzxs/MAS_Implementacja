package com.krzxs.wypozyczalnia.model.enums;

public enum StatusWypozyczenia {
    AKTYWNE("Aktywne"),
    ZAKONCZONE("Zakończone"),
    ANULOWANE("Anulowane"),
    PRZETERMINOWANE("Przeterminowane");

    private final String opis;

    StatusWypozyczenia(String opis) {
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
