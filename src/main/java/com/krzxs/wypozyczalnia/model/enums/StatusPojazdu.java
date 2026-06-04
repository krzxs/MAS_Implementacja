package com.krzxs.wypozyczalnia.model.enums;

public enum StatusPojazdu {
    DOSTEPNY("Dostępny"),
    ZAREZERWOWANY("Zarezerwowany"),
    WYPOZYCZONY("Wypożyczony"),
    W_SERWISIE("W serwisie"),
    WYCOFANY("Wycofany");

    private final String opis;

    StatusPojazdu(String opis) {
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
