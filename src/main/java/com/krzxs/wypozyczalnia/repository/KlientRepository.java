package com.krzxs.wypozyczalnia.repository;

import com.krzxs.wypozyczalnia.model.Klient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KlientRepository extends JpaRepository<Klient, Long> {
}
