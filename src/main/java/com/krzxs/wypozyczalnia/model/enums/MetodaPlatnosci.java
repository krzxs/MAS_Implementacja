package com.krzxs.wypozyczalnia.model.enums;

public enum MetodaPlatnosci {
    GOTOWKA("Gotówka"),
    KARTA("Karta płatnicza"),
    PRZELEW("Przelew");

    private final String opis;

    MetodaPlatnosci(String opis) {
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
