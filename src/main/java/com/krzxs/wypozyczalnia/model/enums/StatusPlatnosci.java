package com.krzxs.wypozyczalnia.model.enums;

public enum StatusPlatnosci {
    OCZEKUJACA("Oczekująca"),
    OPLACONA("Opłacona"),
    ZWROCONA("Zwrócona");

    private final String opis;

    StatusPlatnosci(String opis) {
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
