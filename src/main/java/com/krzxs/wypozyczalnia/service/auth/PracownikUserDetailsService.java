package com.krzxs.wypozyczalnia.service.auth;

import com.krzxs.wypozyczalnia.model.Pracownik;
import com.krzxs.wypozyczalnia.repository.PracownikRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PracownikUserDetailsService implements UserDetailsService {

    private final PracownikRepository pracownikRepository;

    public PracownikUserDetailsService(PracownikRepository pracownikRepository) {
        this.pracownikRepository = pracownikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String numerPracownika) throws UsernameNotFoundException {
        Pracownik pracownik = pracownikRepository.findByNumerPracownika(numerPracownika)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono uzytkownika: " + numerPracownika));
        if (pracownik.getPin() == null || pracownik.getStanowisko() == null) {
            throw new UsernameNotFoundException("Konto pracownika nie jest poprawnie skonfigurowane: " + numerPracownika);
        }
        return User.withUsername(pracownik.getNumerPracownika())
                .password(pracownik.getPin())
                .roles(pracownik.getStanowisko().name())
                .build();
    }
}
