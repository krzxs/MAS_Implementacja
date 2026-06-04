package com.krzxs.wypozyczalnia.repository;

import com.krzxs.wypozyczalnia.model.Pracownik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PracownikRepository extends JpaRepository<Pracownik, Long> {

    Optional<Pracownik> findByNumerPracownika(String login);
}
