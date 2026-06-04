package com.krzxs.wypozyczalnia.service;

import com.krzxs.wypozyczalnia.exception.BusinessException;
import com.krzxs.wypozyczalnia.model.Oddzial;
import com.krzxs.wypozyczalnia.model.Pojazd;
import com.krzxs.wypozyczalnia.repository.OddzialRepository;
import com.krzxs.wypozyczalnia.repository.PojazdRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PojazdService {

    private final PojazdRepository pojazdRepository;
    private final OddzialRepository oddzialRepository;

    public PojazdService(PojazdRepository pojazdRepository, OddzialRepository oddzialRepository) {
        this.pojazdRepository = pojazdRepository;
        this.oddzialRepository = oddzialRepository;
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
    public Pojazd znajdz(Long id) {
        return pojazdRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Nie znaleziono pojazdu."));
    }

    @Transactional
    public void skierujDoSerwisu(Long id) {
        Pojazd pojazd = znajdz(id);
        pojazd.skierujDoSerwisu();
        pojazdRepository.save(pojazd);
    }
}
