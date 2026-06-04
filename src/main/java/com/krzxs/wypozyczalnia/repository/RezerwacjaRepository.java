package com.krzxs.wypozyczalnia.repository;

import com.krzxs.wypozyczalnia.model.Rezerwacja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RezerwacjaRepository extends JpaRepository<Rezerwacja, Long> {
}
