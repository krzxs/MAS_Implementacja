package com.krzxs.wypozyczalnia.service;

import com.krzxs.wypozyczalnia.exception.BusinessException;
import com.krzxs.wypozyczalnia.model.Dodatek;
import com.krzxs.wypozyczalnia.model.Klient;
import com.krzxs.wypozyczalnia.model.Platnosc;
import com.krzxs.wypozyczalnia.model.Pojazd;
import com.krzxs.wypozyczalnia.model.Pracownik;
import com.krzxs.wypozyczalnia.model.Szkoda;
import com.krzxs.wypozyczalnia.model.Ubezpieczenie;
import com.krzxs.wypozyczalnia.model.Wypozyczenie;
import com.krzxs.wypozyczalnia.model.enums.MetodaPlatnosci;
import com.krzxs.wypozyczalnia.model.enums.StatusPlatnosci;
import com.krzxs.wypozyczalnia.model.enums.StatusPojazdu;
import com.krzxs.wypozyczalnia.model.enums.StatusSzkody;
import com.krzxs.wypozyczalnia.model.enums.StatusWypozyczenia;
import com.krzxs.wypozyczalnia.repository.DodatekRepository;
import com.krzxs.wypozyczalnia.repository.KlientRepository;
import com.krzxs.wypozyczalnia.repository.PojazdRepository;
import com.krzxs.wypozyczalnia.repository.PracownikRepository;
import com.krzxs.wypozyczalnia.repository.UbezpieczenieRepository;
import com.krzxs.wypozyczalnia.repository.WypozyczenieRepository;
import com.krzxs.wypozyczalnia.web.WypozyczenieForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class WypozyczenieService {

    private final WypozyczenieRepository wypozyczenieRepository;
    private final KlientRepository klientRepository;
    private final PojazdRepository pojazdRepository;
    private final PracownikRepository pracownikRepository;
    private final DodatekRepository dodatekRepository;
    private final UbezpieczenieRepository ubezpieczenieRepository;

    public WypozyczenieService(WypozyczenieRepository wypozyczenieRepository, KlientRepository klientRepository, PojazdRepository pojazdRepository, PracownikRepository pracownikRepository, DodatekRepository dodatekRepository, UbezpieczenieRepository ubezpieczenieRepository) {
        this.wypozyczenieRepository = wypozyczenieRepository;
        this.klientRepository = klientRepository;
        this.pojazdRepository = pojazdRepository;
        this.pracownikRepository = pracownikRepository;
        this.dodatekRepository = dodatekRepository;
        this.ubezpieczenieRepository = ubezpieczenieRepository;
    }

    @Transactional
    public Wypozyczenie wypozycz(WypozyczenieForm form, String numerPracownika) {
        Klient klient = klientRepository.findById(form.getKlientId())
                .orElseThrow(() -> new BusinessException("Nie wybrano klienta."));
        Pojazd pojazd = pojazdRepository.findById(form.getPojazdId())
                .orElseThrow(() -> new BusinessException("Nie wybrano pojazdu."));
        Pracownik pracownik = pracownikRepository.findByNumerPracownika(numerPracownika)
                .orElseThrow(() -> new BusinessException("Brak zalogowanego pracownika obsługującego."));

        if (form.getDataOd() == null || form.getDataDo() == null) {
            throw new BusinessException("Podaj datę początkową i końcową wypożyczenia.");
        }
        if (form.getDataOd().isAfter(form.getDataDo())) {
            throw new BusinessException("Data początkowa nie może być późniejsza niż data końcowa.");
        }
        if (form.getDataOd().isBefore(LocalDate.now())) {
            throw new BusinessException("Data początkowa nie może być w przeszłości.");
        }
        if (!klient.prawoJazdyWazne(form.getDataDo())) {
            throw new BusinessException("Prawo jazdy klienta jest nieważne w okresie wypożyczenia.");
        }
        if (!pojazd.isDostepny(form.getDataOd(), form.getDataDo())) {
            throw new BusinessException("Pojazd jest niedostępny w wybranym okresie.");
        }

        Wypozyczenie wypozyczenie = new Wypozyczenie(generujNumer(), form.getDataOd(), form.getDataDo(), StatusWypozyczenia.AKTYWNE);

        klient.dodajWypozyczenie(wypozyczenie);
        pojazd.dodajWypozyczenie(wypozyczenie);
        wypozyczenie.setPojazd(pojazd);
        pracownik.dodajObsluga(wypozyczenie);

        if (form.getDodatkiIds() != null) {
            for (Long dodatekId : form.getDodatkiIds()) {
                Dodatek dodatek = dodatekRepository.findById(dodatekId)
                        .orElseThrow(() -> new BusinessException("Nie znaleziono dodatku."));
                wypozyczenie.dodajDodatek(dodatek, 1);
            }
        }

        if (form.getUbezpieczenieId() != null && form.getUbezpieczenieId() > 0) {
            Ubezpieczenie ubezpieczenie = ubezpieczenieRepository.findById(form.getUbezpieczenieId())
                    .orElseThrow(() -> new BusinessException("Nie znaleziono pakietu ubezpieczenia."));
            wypozyczenie.setUbezpieczenie(ubezpieczenie);
        }

        MetodaPlatnosci metoda = (form.getMetodaPlatnosci() != null) ? form.getMetodaPlatnosci() : MetodaPlatnosci.KARTA;
        Platnosc platnosc = new Platnosc(wypozyczenie.getKosztCalkowity(), LocalDateTime.now(), metoda, StatusPlatnosci.OPLACONA);
        wypozyczenie.dodajPlatnosc(platnosc);

        pojazd.setStatus(StatusPojazdu.WYPOZYCZONY);

        return wypozyczenieRepository.save(wypozyczenie);
    }

    @Transactional(readOnly = true)
    public Wypozyczenie znajdz(Long id) {
        Wypozyczenie wypozyczenie = wypozyczenieRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Nie znaleziono wypożyczenia."));
        wypozyczenie.getWypozyczenieDodatki().size();
        wypozyczenie.getPlatnosci().size();
        wypozyczenie.getSzkody().size();
        return wypozyczenie;
    }

    @Transactional
    public void zwroc(Long id, boolean szkoda, String opisSzkody) {
        Wypozyczenie wypozyczenie = wypozyczenieRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Nie znaleziono wypożyczenia."));
        wypozyczenie.zakoncz();
        Pojazd pojazd = wypozyczenie.getPojazd();
        if (szkoda) {
            Szkoda zgloszona = new Szkoda((opisSzkody == null || opisSzkody.isBlank()) ? "Szkoda zgłoszona przy zwrocie" : opisSzkody, LocalDate.now(), BigDecimal.ZERO, StatusSzkody.ZGLOSZONA);
            wypozyczenie.dodajSzkode(zgloszona);
            pojazd.skierujDoSerwisu();
        } else {
            pojazd.setStatus(StatusPojazdu.DOSTEPNY);
        }
        wypozyczenieRepository.save(wypozyczenie);
    }

    private String generujNumer() {
        String uuid = UUID.randomUUID().toString();
        String numer = Base64.getEncoder().encodeToString(uuid.getBytes()).toUpperCase().substring(0, 8);
        return String.format("WYP/%d/%s", LocalDate.now().getYear(), numer);
    }
}
