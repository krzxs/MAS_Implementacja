package com.krzxs.wypozyczalnia.advice;

import com.krzxs.wypozyczalnia.model.Pracownik;
import com.krzxs.wypozyczalnia.repository.PracownikRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserAdvice {

    private final PracownikRepository pracownikRepository;

    public CurrentUserAdvice(PracownikRepository pracownikRepository) {
        this.pracownikRepository = pracownikRepository;
    }

    @ModelAttribute("zalogowany")
    @Transactional(readOnly = true)
    public Pracownik zalogowany() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!zalogowanyUzytkownik(auth)) {
            return null;
        }
        return pracownikRepository.findByNumerPracownika(auth.getName())
                .orElse(null);
    }

    @ModelAttribute("isKierownik")
    public boolean isKierownik() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return zalogowanyUzytkownik(auth) && auth.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_KIEROWNIK".equals(authority.getAuthority()));
    }

    @ModelAttribute("isKonsultant")
    public boolean isKonsultant() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return zalogowanyUzytkownik(auth) && auth.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_KONSULTANT".equals(authority.getAuthority()));
    }

    private boolean zalogowanyUzytkownik(Authentication auth) {
        return auth != null && auth.isAuthenticated() && !"anonymousUser".equals(String.valueOf(auth.getPrincipal()));
    }
}
