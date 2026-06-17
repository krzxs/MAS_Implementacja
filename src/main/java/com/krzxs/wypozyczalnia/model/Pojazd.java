package com.krzxs.wypozyczalnia.model;

import com.krzxs.wypozyczalnia.model.enums.StatusPojazdu;
import com.krzxs.wypozyczalnia.model.enums.StatusRezerwacji;
import com.krzxs.wypozyczalnia.model.enums.StatusWypozyczenia;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pojazd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numerRejestracyjny;
    private String marka;
    private String model;
    private int rokProdukcji;
    private int przebieg;
    private BigDecimal stawkaDzienna;

    @Enumerated(EnumType.STRING)
    private StatusPojazdu status = StatusPojazdu.DOSTEPNY;

    @ManyToOne
    @JoinColumn(name = "kategoria_id")
    private KategoriaPojazdu kategoria;

    @ManyToOne
    @JoinColumn(name = "oddzial_id")
    private Oddzial oddzial;

    @OneToMany(mappedBy = "pojazd")
    private List<Wypozyczenie> wypozyczenia = new ArrayList<>();

    @OneToMany(mappedBy = "pojazd")
    private List<Rezerwacja> rezerwacje = new ArrayList<>();

    protected Pojazd() {
    }

    protected Pojazd(String numerRejestracyjny, String marka, String model, int rokProdukcji, int przebieg, BigDecimal stawkaDzienna) {
        this.numerRejestracyjny = numerRejestracyjny;
        this.marka = marka;
        this.model = model;
        this.rokProdukcji = rokProdukcji;
        this.przebieg = przebieg;
        this.stawkaDzienna = stawkaDzienna;
    }

    public abstract String getTypPojazdu();

    public Long getId() {
        return id;
    }

    public String getNumerRejestracyjny() {
        return numerRejestracyjny;
    }

    public void setNumerRejestracyjny(String numerRejestracyjny) {
        this.numerRejestracyjny = numerRejestracyjny;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getRokProdukcji() {
        return rokProdukcji;
    }

    public void setRokProdukcji(int rokProdukcji) {
        this.rokProdukcji = rokProdukcji;
    }

    public int getPrzebieg() {
        return przebieg;
    }

    public void setPrzebieg(int przebieg) {
        this.przebieg = przebieg;
    }

    public BigDecimal getStawkaDzienna() {
        return stawkaDzienna;
    }

    public void setStawkaDzienna(BigDecimal stawkaDzienna) {
        this.stawkaDzienna = stawkaDzienna;
    }

    public StatusPojazdu getStatus() {
        return status;
    }

    public void setStatus(StatusPojazdu status) {
        this.status = status;
    }

    public KategoriaPojazdu getKategoria() {
        return kategoria;
    }

    public void setKategoria(KategoriaPojazdu kategoria) {
        this.kategoria = kategoria;
    }

    public Oddzial getOddzial() {
        return oddzial;
    }

    public void setOddzial(Oddzial oddzial) {
        this.oddzial = oddzial;
    }

    public List<Wypozyczenie> getWypozyczenia() {
        return wypozyczenia;
    }

    public List<Rezerwacja> getRezerwacje() {
        return rezerwacje;
    }

    public boolean isDostepny(LocalDate dataOd, LocalDate dataDo) {
        if (status == StatusPojazdu.WYCOFANY || status == StatusPojazdu.W_SERWISIE) {
            return false;
        }
        for (Wypozyczenie w : wypozyczenia) {
            if (w.getStatus() == StatusWypozyczenia.AKTYWNE && okresyNachodza(dataOd, dataDo, w.getDataOd(), w.getDataDo())) {
                return false;
            }
        }
        for (Rezerwacja r : rezerwacje) {
            if (r.getStatus() == StatusRezerwacji.POTWIERDZONA && okresyNachodza(dataOd, dataDo, r.getDataOd(), r.getDataDo())) {
                return false;
            }
        }
        return true;
    }

    private boolean okresyNachodza(LocalDate a1, LocalDate a2, LocalDate b1, LocalDate b2) {
        return !a1.isAfter(b2) && !b1.isAfter(a2);
    }

    public void dodajWypozyczenie(Wypozyczenie wypozyczenie) {
        wypozyczenia.add(wypozyczenie);
    }

    public void dodajRezerwacje(Rezerwacja rezerwacja) {
        rezerwacje.add(rezerwacja);
        rezerwacja.setPojazd(this);
    }

    public void skierujDoSerwisu() {
        this.status = StatusPojazdu.W_SERWISIE;
    }

    public void przywrocZSerwisu() {
        this.status = StatusPojazdu.DOSTEPNY;
    }

    public boolean maPowiazania() {
        return !wypozyczenia.isEmpty() || !rezerwacje.isEmpty();
    }
}
