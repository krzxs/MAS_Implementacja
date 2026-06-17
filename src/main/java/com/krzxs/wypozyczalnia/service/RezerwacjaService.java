package com.krzxs.wypozyczalnia.service;

import com.krzxs.wypozyczalnia.exception.BusinessException;
import com.krzxs.wypozyczalnia.model.Klient;
import com.krzxs.wypozyczalnia.model.Pojazd;
import com.krzxs.wypozyczalnia.model.Rezerwacja;
import com.krzxs.wypozyczalnia.model.enums.StatusPojazdu;
import com.krzxs.wypozyczalnia.model.enums.StatusRezerwacji;
import com.krzxs.wypozyczalnia.repository.KlientRepository;
import com.krzxs.wypozyczalnia.repository.PojazdRepository;
import com.krzxs.wypozyczalnia.repository.RezerwacjaRepository;
import com.krzxs.wypozyczalnia.web.RezerwacjaForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class RezerwacjaService {

    private final RezerwacjaRepository rezerwacjaRepository;
    private final KlientRepository klientRepository;
    private final PojazdRepository pojazdRepository;

    public RezerwacjaService(RezerwacjaRepository rezerwacjaRepository, KlientRepository klientRepository, PojazdRepository pojazdRepository) {
        this.rezerwacjaRepository = rezerwacjaRepository;
        this.klientRepository = klientRepository;
        this.pojazdRepository = pojazdRepository;
    }

    @Transactional(readOnly = true)
    public List<Rezerwacja> lista() {
        return rezerwacjaRepository.findAll();
    }

    @Transactional
    public Rezerwacja zarezerwuj(RezerwacjaForm form) {
        Klient klient = klientRepository.findById(form.getKlientId())
                .orElseThrow(() -> new BusinessException("Nie wybrano klienta."));
        Pojazd pojazd = pojazdRepository.findById(form.getPojazdId())
                .orElseThrow(() -> new BusinessException("Nie wybrano pojazdu."));

        if (form.getDataOd() == null || form.getDataDo() == null) {
            throw new BusinessException("Podaj planowaną datę odbioru i zwrotu.");
        }
        if (form.getDataOd().isAfter(form.getDataDo())) {
            throw new BusinessException("Data odbioru nie może być późniejsza niż data zwrotu.");
        }
        if (form.getDataOd().isBefore(LocalDate.now())) {
            throw new BusinessException("Data odbioru nie może być w przeszłości.");
        }
        if (!klient.prawoJazdyWazne(form.getDataDo())) {
            throw new BusinessException("Prawo jazdy klienta jest nieważne w okresie rezerwacji.");
        }
        if (!pojazd.isDostepny(form.getDataOd(), form.getDataDo())) {
            throw new BusinessException("Pojazd jest niedostępny w wybranym okresie.");
        }

        Rezerwacja rezerwacja = new Rezerwacja(generujNumer(), LocalDateTime.now(), form.getDataOd(), form.getDataDo());
        rezerwacja.potwierdz();
        klient.dodajRezerwacje(rezerwacja);
        pojazd.dodajRezerwacje(rezerwacja);
        pojazd.setStatus(StatusPojazdu.ZAREZERWOWANY);

        return rezerwacjaRepository.save(rezerwacja);
    }

    @Transactional
    public void anuluj(Long id) {
        Rezerwacja rezerwacja = rezerwacjaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Nie znaleziono rezerwacji."));

        if (rezerwacja.getStatus() == StatusRezerwacji.ANULOWANA) {
            throw new BusinessException("Rezerwacja jest już anulowana.");
        }
        if (rezerwacja.getStatus() == StatusRezerwacji.ZREALIZOWANA) {
            throw new BusinessException("Nie można anulować zrealizowanej rezerwacji.");
        }

        rezerwacja.anuluj();

        Pojazd pojazd = rezerwacja.getPojazd();
        if (pojazd != null && pojazd.getStatus() == StatusPojazdu.ZAREZERWOWANY && brakAktywnychRezerwacji(pojazd)) {
            pojazd.setStatus(StatusPojazdu.DOSTEPNY);
        }

        rezerwacjaRepository.save(rezerwacja);
    }

    private boolean brakAktywnychRezerwacji(Pojazd pojazd) {
        return pojazd.getRezerwacje().stream()
                .noneMatch(r -> r.getStatus() == StatusRezerwacji.POTWIERDZONA || r.getStatus() == StatusRezerwacji.OCZEKUJACA);
    }

    private String generujNumer() {
        String uuid = UUID.randomUUID().toString();
        String numer = Base64.getEncoder().encodeToString(uuid.getBytes()).toUpperCase().substring(0, 8);
        return String.format("REZ/%d/%s", LocalDate.now().getYear(), numer);
    }
}
