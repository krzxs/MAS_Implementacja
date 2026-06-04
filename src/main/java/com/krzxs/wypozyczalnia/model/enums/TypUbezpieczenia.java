package com.krzxs.wypozyczalnia.model.enums;

public enum TypUbezpieczenia {
    OC("OC"),
    AC("AC"),
    PELNE("Pełne");

    private final String opis;

    TypUbezpieczenia(String opis) {
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
