package com.krzxs.wypozyczalnia.web;

import com.krzxs.wypozyczalnia.model.enums.MetodaPlatnosci;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WypozyczenieForm {

    private Long pojazdId;
    private Long klientId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataOd;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataDo;

    private List<Long> dodatkiIds = new ArrayList<>();

    private Long ubezpieczenieId = 0L;

    private MetodaPlatnosci metodaPlatnosci = MetodaPlatnosci.KARTA;

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

    public List<Long> getDodatkiIds() {
        return dodatkiIds;
    }

    public void setDodatkiIds(List<Long> dodatkiIds) {
        this.dodatkiIds = dodatkiIds;
    }

    public Long getUbezpieczenieId() {
        return ubezpieczenieId;
    }

    public void setUbezpieczenieId(Long ubezpieczenieId) {
        this.ubezpieczenieId = ubezpieczenieId;
    }

    public MetodaPlatnosci getMetodaPlatnosci() {
        return metodaPlatnosci;
    }

    public void setMetodaPlatnosci(MetodaPlatnosci metodaPlatnosci) {
        this.metodaPlatnosci = metodaPlatnosci;
    }
}
