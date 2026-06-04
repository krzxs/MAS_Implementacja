package com.krzxs.wypozyczalnia.web;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class RezerwacjaForm {

    private Long pojazdId;
    private Long klientId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataOd;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataDo;

    public Long getPojazdId() {
        return pojazdId;
    }

    public void setPojazdId(Long pojazdId) {
        this.pojazdId = pojazdId;
    }

    public Long getKlientId() {
        return klientId;
    }

    public void setKlientId(Long klientId) {
        this.klientId = klientId;
    }

    public LocalDate getDataOd() {
        return dataOd;
    }

    public void setDataOd(LocalDate dataOd) {
        this.dataOd = dataOd;
    }

    public LocalDate getDataDo() {
        return dataDo;
    }

    public void setDataDo(LocalDate dataDo) {
        this.dataDo = dataDo;
    }
}
