package com.krzxs.wypozyczalnia.repository;

import com.krzxs.wypozyczalnia.model.Wypozyczenie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WypozyczenieRepository extends JpaRepository<Wypozyczenie, Long> {
}
