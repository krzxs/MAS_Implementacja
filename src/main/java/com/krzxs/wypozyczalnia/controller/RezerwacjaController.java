package com.krzxs.wypozyczalnia.controller;

import com.krzxs.wypozyczalnia.exception.BusinessException;
import com.krzxs.wypozyczalnia.model.Pojazd;
import com.krzxs.wypozyczalnia.model.Rezerwacja;
import com.krzxs.wypozyczalnia.service.KlientService;
import com.krzxs.wypozyczalnia.service.PojazdService;
import com.krzxs.wypozyczalnia.service.RezerwacjaService;
import com.krzxs.wypozyczalnia.web.RezerwacjaForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/rezerwacja")
public class RezerwacjaController {

    private final RezerwacjaService rezerwacjaService;
    private final PojazdService pojazdService;
    private final KlientService klientService;

    public RezerwacjaController(RezerwacjaService rezerwacjaService, PojazdService pojazdService, KlientService klientService) {
        this.rezerwacjaService = rezerwacjaService;
        this.pojazdService = pojazdService;
        this.klientService = klientService;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("rezerwacje", rezerwacjaService.lista());
        return "rezerwacje";
    }

    @GetMapping("/nowa")
    public String nowa(@RequestParam Long pojazdId, Model model) {
        RezerwacjaForm form = new RezerwacjaForm();
        form.setPojazdId(pojazdId);
        form.setDataOd(LocalDate.now().plusDays(1));
        form.setDataDo(LocalDate.now().plusDays(4));
        model.addAttribute("form", form);
        wypelnijDane(model, pojazdId);
        return "rezerwacja-nowa";
    }

    @PostMapping
    public String utworz(@ModelAttribute("form") RezerwacjaForm form, Model model) {
        try {
            Rezerwacja rezerwacja = rezerwacjaService.zarezerwuj(form);
            return "redirect:/rezerwacja?zarezerwowano=" + rezerwacja.getNumerRezerwacji();
        } catch (BusinessException ex) {
            model.addAttribute("blad", ex.getMessage());
            wypelnijDane(model, form.getPojazdId());
            return "rezerwacja-nowa";
        }
    }

    @PostMapping("/{id}/anuluj")
    public String anuluj(@PathVariable Long id, RedirectAttributes ra) {
        try {
            rezerwacjaService.anuluj(id);
            ra.addFlashAttribute("komunikat", "Rezerwacja została anulowana.");
        } catch (BusinessException ex) {
            ra.addFlashAttribute("blad", ex.getMessage());
        }
        return "redirect:/rezerwacja";
    }

    private void wypelnijDane(Model model, Long pojazdId) {
        Pojazd pojazd = pojazdService.znajdz(pojazdId);
        model.addAttribute("pojazd", pojazd);
        model.addAttribute("klienci", klientService.lista());
    }
}
