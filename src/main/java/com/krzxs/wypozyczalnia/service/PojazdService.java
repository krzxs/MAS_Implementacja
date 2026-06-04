package com.krzxs.wypozyczalnia.service;

import com.krzxs.wypozyczalnia.exception.BusinessException;
import com.krzxs.wypozyczalnia.model.KategoriaPojazdu;
import com.krzxs.wypozyczalnia.model.Motocykl;
import com.krzxs.wypozyczalnia.model.Oddzial;
import com.krzxs.wypozyczalnia.model.Pojazd;
import com.krzxs.wypozyczalnia.model.SamochodDostawczy;
import com.krzxs.wypozyczalnia.model.SamochodOsobowy;
import com.krzxs.wypozyczalnia.model.enums.StatusPojazdu;
import com.krzxs.wypozyczalnia.repository.KategoriaPojazduRepository;
import com.krzxs.wypozyczalnia.repository.OddzialRepository;
import com.krzxs.wypozyczalnia.repository.PojazdRepository;
import com.krzxs.wypozyczalnia.web.PojazdForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PojazdService {

    private final PojazdRepository pojazdRepository;
    private final OddzialRepository oddzialRepository;
    private final KategoriaPojazduRepository kategoriaPojazduRepository;

    public PojazdService(PojazdRepository pojazdRepository, OddzialRepository oddzialRepository, KategoriaPojazduRepository kategoriaPojazduRepository) {
        this.pojazdRepository = pojazdRepository;
        this.oddzialRepository = oddzialRepository;
        this.kategoriaPojazduRepository = kategoriaPojazduRepository;
    }

    @Transactional(readOnly = true)
    public List<Pojazd> lista() {
        return pojazdRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Pojazd> wedlugOddzialu(Long oddzialId) {
        Oddzial oddzial = oddzialRepository.findById(oddzialId)
                .orElseThrow(() -> new BusinessException("Nie znaleziono oddziału."));
        return oddzial.getFlota();
    }

    @Transactional(readOnly = true)
    public List<Oddzial> oddzialy() {
        return oddzialRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<KategoriaPojazdu> kategorie() {
        return kategoriaPojazduRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pojazd znajdz(Long id) {
        return pojazdRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Nie znaleziono pojazdu."));
    }

    @Transactional
    public void skierujDoSerwisu(Long id) {
        Pojazd pojazd = znajdz(id);
        if (pojazd.getStatus() == StatusPojazdu.WYPOZYCZONY) {
            throw new BusinessException("Nie można skierować do serwisu wypożyczonego pojazdu.");
        }
        pojazd.skierujDoSerwisu();
        pojazdRepository.save(pojazd);
    }

    @Transactional
    public void przywrocZSerwisu(Long id) {
        Pojazd pojazd = znajdz(id);
        if (pojazd.getStatus() != StatusPojazdu.W_SERWISIE) {
            throw new BusinessException("Pojazd nie znajduje się w serwisie.");
        }
        pojazd.przywrocZSerwisu();
        pojazdRepository.save(pojazd);
    }

    @Transactional
    public Pojazd dodaj(PojazdForm form) {
        walidujWspolne(form);
        Pojazd pojazd = switch (bezpiecznyTyp(form.getTyp())) {
            case "OSOBOWY" -> {
                walidujOsobowy(form);
                yield new SamochodOsobowy(form.getNumerRejestracyjny().trim(), form.getMarka().trim(), form.getModel().trim(),
                        form.getRokProdukcji(), form.getPrzebieg(), form.getStawkaDzienna(),
                        form.getLiczbaMiejsc(), form.getTypNadwozia());
            }
            case "DOSTAWCZY" -> {
                walidujDostawczy(form);
                yield new SamochodDostawczy(form.getNumerRejestracyjny().trim(), form.getMarka().trim(), form.getModel().trim(),
                        form.getRokProdukcji(), form.getPrzebieg(), form.getStawkaDzienna(),
                        form.getLadownosc(), form.getPojemnoscLadunkowa());
            }
            case "MOTOCYKL" -> {
                walidujMotocykl(form);
                yield new Motocykl(form.getNumerRejestracyjny().trim(), form.getMarka().trim(), form.getModel().trim(),
                        form.getRokProdukcji(), form.getPrzebieg(), form.getStawkaDzienna(),
                        form.getPojemnoscSilnika());
            }
            default -> throw new BusinessException("Nieprawidłowy typ pojazdu.");
        };
        przypiszKategorieIOddzial(pojazd, form);
        return pojazdRepository.save(pojazd);
    }

    @Transactional
    public Pojazd aktualizuj(Long id, PojazdForm form) {
        Pojazd pojazd = znajdz(id);
        walidujWspolne(form);

        pojazd.setNumerRejestracyjny(form.getNumerRejestracyjny().trim());
        pojazd.setMarka(form.getMarka().trim());
        pojazd.setModel(form.getModel().trim());
        pojazd.setRokProdukcji(form.getRokProdukcji());
        pojazd.setPrzebieg(form.getPrzebieg());
        pojazd.setStawkaDzienna(form.getStawkaDzienna());
        przypiszKategorieIOddzial(pojazd, form);

        if (pojazd instanceof SamochodOsobowy osobowy) {
            walidujOsobowy(form);
            osobowy.setLiczbaMiejsc(form.getLiczbaMiejsc());
            osobowy.setTypNadwozia(form.getTypNadwozia());
        } else if (pojazd instanceof SamochodDostawczy dostawczy) {
            walidujDostawczy(form);
            dostawczy.setLadownosc(form.getLadownosc());
            dostawczy.setPojemnoscLadunkowa(form.getPojemnoscLadunkowa());
        } else if (pojazd instanceof Motocykl motocykl) {
            walidujMotocykl(form);
            motocykl.setPojemnoscSilnika(form.getPojemnoscSilnika());
        }

        return pojazdRepository.save(pojazd);
    }

    @Transactional
    public void usun(Long id) {
        Pojazd pojazd = znajdz(id);
        if (pojazd.getStatus() == StatusPojazdu.WYPOZYCZONY) {
            throw new BusinessException("Nie można usunąć aktualnie wypożyczonego pojazdu.");
        }
        if (pojazd.maPowiazania()) {
            throw new BusinessException("Nie można usunąć pojazdu powiązanego z wypożyczeniami lub rezerwacjami. Wycofaj go z floty.");
        }
        pojazdRepository.delete(pojazd);
    }

    @Transactional
    public void wycofaj(Long id) {
        Pojazd pojazd = znajdz(id);
        if (pojazd.getStatus() == StatusPojazdu.WYPOZYCZONY) {
            throw new BusinessException("Nie można wycofać aktualnie wypożyczonego pojazdu.");
        }
        pojazd.setStatus(StatusPojazdu.WYCOFANY);
        pojazdRepository.save(pojazd);
    }

    @Transactional(readOnly = true)
    public PojazdForm formularzEdycji(Long id) {
        Pojazd pojazd = znajdz(id);
        PojazdForm form = new PojazdForm();
        form.setNumerRejestracyjny(pojazd.getNumerRejestracyjny());
        form.setMarka(pojazd.getMarka());
        form.setModel(pojazd.getModel());
        form.setRokProdukcji(pojazd.getRokProdukcji());
        form.setPrzebieg(pojazd.getPrzebieg());
        form.setStawkaDzienna(pojazd.getStawkaDzienna());
        form.setKategoriaId(pojazd.getKategoria() != null ? pojazd.getKategoria().getId() : null);
        form.setOddzialId(pojazd.getOddzial() != null ? pojazd.getOddzial().getId() : null);

        if (pojazd instanceof SamochodOsobowy osobowy) {
            form.setTyp("OSOBOWY");
            form.setLiczbaMiejsc(osobowy.getLiczbaMiejsc());
            form.setTypNadwozia(osobowy.getTypNadwozia());
        } else if (pojazd instanceof SamochodDostawczy dostawczy) {
            form.setTyp("DOSTAWCZY");
            form.setLadownosc(dostawczy.getLadownosc());
            form.setPojemnoscLadunkowa(dostawczy.getPojemnoscLadunkowa());
        } else if (pojazd instanceof Motocykl motocykl) {
            form.setTyp("MOTOCYKL");
            form.setPojemnoscSilnika(motocykl.getPojemnoscSilnika());
        }
        return form;
    }

    private void przypiszKategorieIOddzial(Pojazd pojazd, PojazdForm form) {
        KategoriaPojazdu kategoria = kategoriaPojazduRepository.findById(form.getKategoriaId())
                .orElseThrow(() -> new BusinessException("Nie znaleziono kategorii pojazdu."));
        Oddzial oddzial = oddzialRepository.findById(form.getOddzialId())
                .orElseThrow(() -> new BusinessException("Nie znaleziono oddziału."));
        pojazd.setKategoria(kategoria);
        pojazd.setOddzial(oddzial);
    }

    private String bezpiecznyTyp(String typ) {
        return typ == null ? "" : typ.trim().toUpperCase();
    }

    private void walidujWspolne(PojazdForm form) {
        if (form.getNumerRejestracyjny() == null || form.getNumerRejestracyjny().isBlank()) {
            throw new BusinessException("Numer rejestracyjny jest wymagany.");
        }
        if (form.getMarka() == null || form.getMarka().isBlank()) {
            throw new BusinessException("Marka jest wymagana.");
        }
        if (form.getModel() == null || form.getModel().isBlank()) {
            throw new BusinessException("Model jest wymagany.");
        }
        int biezacyRok = LocalDate.now().getYear();
        if (form.getRokProdukcji() == null || form.getRokProdukcji() < 1950 || form.getRokProdukcji() > biezacyRok + 1) {
            throw new BusinessException("Podaj prawidłowy rok produkcji (1950 - " + (biezacyRok + 1) + ").");
        }
        if (form.getPrzebieg() == null || form.getPrzebieg() < 0) {
            throw new BusinessException("Przebieg nie może być ujemny.");
        }
        if (form.getStawkaDzienna() == null || form.getStawkaDzienna().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Stawka dzienna musi być większa od zera.");
        }
        if (form.getKategoriaId() == null) {
            throw new BusinessException("Wybierz kategorię pojazdu.");
        }
        if (form.getOddzialId() == null) {
            throw new BusinessException("Wybierz oddział.");
        }
    }

    private void walidujOsobowy(PojazdForm form) {
        if (form.getLiczbaMiejsc() == null || form.getLiczbaMiejsc() < 1) {
            throw new BusinessException("Podaj liczbę miejsc.");
        }
        if (form.getTypNadwozia() == null) {
            throw new BusinessException("Wybierz typ nadwozia.");
        }
    }

    private void walidujDostawczy(PojazdForm form) {
        if (form.getLadownosc() == null || form.getLadownosc() <= 0) {
            throw new BusinessException("Podaj ładowność (kg).");
        }
        if (form.getPojemnoscLadunkowa() == null || form.getPojemnoscLadunkowa() <= 0) {
            throw new BusinessException("Podaj pojemność ładunkową (m³).");
        }
    }

    private void walidujMotocykl(PojazdForm form) {
        if (form.getPojemnoscSilnika() == null || form.getPojemnoscSilnika() <= 0) {
            throw new BusinessException("Podaj pojemność silnika (cm³).");
        }
    }
}
