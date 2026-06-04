package com.krzxs.wypozyczalnia.model.enums;

public enum StatusSzkody {
    ZGLOSZONA("Zgłoszona"),
    W_NAPRAWIE("W naprawie"),
    NAPRAWIONA("Naprawiona");

    private final String opis;

    StatusSzkody(String opis) {
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
