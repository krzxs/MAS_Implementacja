package com.krzxs.wypozyczalnia.service;

import com.krzxs.wypozyczalnia.model.Dodatek;
import com.krzxs.wypozyczalnia.model.Pracownik;
import com.krzxs.wypozyczalnia.model.Ubezpieczenie;
import com.krzxs.wypozyczalnia.repository.DodatekRepository;
import com.krzxs.wypozyczalnia.repository.PracownikRepository;
import com.krzxs.wypozyczalnia.repository.UbezpieczenieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KatalogService {

    private final DodatekRepository dodatekRepository;
    private final UbezpieczenieRepository ubezpieczenieRepository;
    private final PracownikRepository pracownikRepository;

    public KatalogService(DodatekRepository dodatekRepository, UbezpieczenieRepository ubezpieczenieRepository, PracownikRepository pracownikRepository) {
        this.dodatekRepository = dodatekRepository;
        this.ubezpieczenieRepository = ubezpieczenieRepository;
        this.pracownikRepository = pracownikRepository;
    }

    @Transactional(readOnly = true)
    public List<Dodatek> dodatki() {
        return dodatekRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Ubezpieczenie> ubezpieczenia() {
        return ubezpieczenieRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Pracownik> pracownicy() {
        return pracownikRepository.findAll();
    }
}
