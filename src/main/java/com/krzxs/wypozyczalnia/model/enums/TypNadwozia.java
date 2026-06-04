package com.krzxs.wypozyczalnia.model.enums;

public enum TypNadwozia {
    SEDAN("Sedan"),
    HATCHBACK("Hatchback"),
    KOMBI("Kombi"),
    SUV("SUV");

    private final String opis;

    TypNadwozia(String opis) {
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
