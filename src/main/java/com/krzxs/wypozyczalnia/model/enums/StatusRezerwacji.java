package com.krzxs.wypozyczalnia.model.enums;

public enum StatusRezerwacji {
    OCZEKUJACA("Oczekująca"),
    POTWIERDZONA("Potwierdzona"),
    ANULOWANA("Anulowana"),
    ZREALIZOWANA("Zrealizowana");

    private final String opis;

    StatusRezerwacji(String opis) {
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
