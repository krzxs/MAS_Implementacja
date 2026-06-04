package com.krzxs.wypozyczalnia.service;

import com.krzxs.wypozyczalnia.exception.BusinessException;
import com.krzxs.wypozyczalnia.model.KategoriaPojazdu;
import com.krzxs.wypozyczalnia.repository.KategoriaPojazduRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class KategoriaPojazduService {

    private final KategoriaPojazduRepository kategoriaPojazduRepository;

    public KategoriaPojazduService(KategoriaPojazduRepository kategoriaPojazduRepository) {
        this.kategoriaPojazduRepository = kategoriaPojazduRepository;
    }

    @Transactional(readOnly = true)
    public List<KategoriaPojazdu> lista() {
        return kategoriaPojazduRepository.findAll();
    }

    @Transactional
    public void aktualizuj(Long id, BigDecimal mnoznikCeny) {
        KategoriaPojazdu kategoria = kategoriaPojazduRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Nie znaleziono kategorii."));
        kategoria.setMnoznikCeny(mnoznikCeny);
        kategoriaPojazduRepository.save(kategoria);
    }
}
