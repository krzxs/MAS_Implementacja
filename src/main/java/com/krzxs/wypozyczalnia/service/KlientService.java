package com.krzxs.wypozyczalnia.service;

import com.krzxs.wypozyczalnia.exception.BusinessException;
import com.krzxs.wypozyczalnia.model.Klient;
import com.krzxs.wypozyczalnia.model.Osoba;
import com.krzxs.wypozyczalnia.repository.KlientRepository;
import com.krzxs.wypozyczalnia.repository.OsobaRepository;
import com.krzxs.wypozyczalnia.web.KlientForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class KlientService {

    private final KlientRepository klientRepository;
    private final OsobaRepository osobaRepository;

    public KlientService(KlientRepository klientRepository, OsobaRepository osobaRepository) {
        this.klientRepository = klientRepository;
        this.osobaRepository = osobaRepository;
    }

    @Transactional(readOnly = true)
    public List<Klient> lista() {
        return klientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Klient znajdz(Long id) {
        return klientRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Nie znaleziono klienta."));
    }

    @Transactional
    public Klient zarejestruj(KlientForm form) {
        if (form.getImie() == null || form.getImie().isBlank() || form.getNazwisko() == null || form.getNazwisko().isBlank()) {
            throw new BusinessException("Imię i nazwisko są wymagane.");
        }
        if (form.getEmail() == null || form.getEmail().isBlank()) {
            throw new BusinessException("Email jest wymagany.");
        }
        if (form.getTelefon() == null || form.getTelefon().isBlank()) {
            throw new BusinessException("Telefon jest wymagany.");
        }
        if (form.getDataUrodzenia() == null) {
            throw new BusinessException("Data urodzenia jest wymagana.");
        }
        if (form.getNumerPrawaJazdy() == null || form.getNumerPrawaJazdy().isBlank()) {
            throw new BusinessException("Numer prawa jazdy jest wymagany.");
        }
        if (form.getDataWaznosciPrawaJazdy() == null) {
            throw new BusinessException("Data ważności prawa jazdy jest wymagana.");
        }

        Osoba osoba = new Osoba(form.getImie().trim(), form.getNazwisko().trim(), form.getEmail(), form.getTelefon(), form.getDataUrodzenia());
        Klient klient = new Klient(generujNumerKlienta(), LocalDate.now(), form.getNumerPrawaJazdy().trim(), form.getDataWaznosciPrawaJazdy());
        osoba.przypiszKlienta(klient);
        osobaRepository.save(osoba);
        return klient;
    }

    private String generujNumerKlienta() {
        String uuid = UUID.randomUUID().toString();
        String numer = Base64.getEncoder().encodeToString(uuid.getBytes()).toUpperCase().substring(0, 8);
        return String.format("KL/%s", numer);
    }
}
