package com.krzxs.wypozyczalnia.controller;

import com.krzxs.wypozyczalnia.exception.BusinessException;
import com.krzxs.wypozyczalnia.model.Klient;
import com.krzxs.wypozyczalnia.service.KlientService;
import com.krzxs.wypozyczalnia.web.KlientForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/klienci")
public class KlientController {

    private final KlientService klientService;

    public KlientController(KlientService klientService) {
        this.klientService = klientService;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("klienci", klientService.lista());
        return "klienci";
    }

    @GetMapping("/rejestracja")
    public String formularz(Model model) {
        model.addAttribute("form", new KlientForm());
        return "klient-rejestracja";
    }

    @PostMapping("/rejestracja")
    public String zarejestruj(@ModelAttribute("form") KlientForm form, Model model) {
        try {
            Klient klient = klientService.zarejestruj(form);
            return "redirect:/klienci/" + klient.getId();
        } catch (BusinessException ex) {
            model.addAttribute("blad", ex.getMessage());
            return "klient-rejestracja";
        }
    }

    @GetMapping("/{id}")
    public String szczegoly(@PathVariable Long id, Model model) {
        model.addAttribute("klient", klientService.znajdz(id));
        return "klient-szczegoly";
    }
}
