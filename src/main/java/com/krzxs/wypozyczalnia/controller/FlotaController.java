package com.krzxs.wypozyczalnia.controller;

import com.krzxs.wypozyczalnia.exception.BusinessException;
import com.krzxs.wypozyczalnia.model.enums.TypNadwozia;
import com.krzxs.wypozyczalnia.service.PojazdService;
import com.krzxs.wypozyczalnia.web.PojazdForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FlotaController {

    private final PojazdService pojazdService;

    public FlotaController(PojazdService pojazdService) {
        this.pojazdService = pojazdService;
    }

    @GetMapping("/flota")
    public String flota(@RequestParam(required = false) Long oddzialId, Model model) {
        model.addAttribute("pojazdy",
                oddzialId == null ? pojazdService.lista() : pojazdService.wedlugOddzialu(oddzialId));
        model.addAttribute("oddzialy", pojazdService.oddzialy());
        model.addAttribute("wybranyOddzial", oddzialId);
        return "flota";
    }

    @GetMapping("/flota/nowy")
    public String nowy(Model model) {
        PojazdForm form = new PojazdForm();
        form.setTyp("OSOBOWY");
        model.addAttribute("form", form);
        model.addAttribute("edycja", false);
        wypelnijDane(model);
        return "pojazd-formularz";
    }

    @PostMapping("/flota")
    public String utworz(@ModelAttribute("form") PojazdForm form, Model model) {
        try {
            pojazdService.dodaj(form);
            return "redirect:/flota?dodano";
        } catch (BusinessException ex) {
            model.addAttribute("blad", ex.getMessage());
            model.addAttribute("edycja", false);
            wypelnijDane(model);
            return "pojazd-formularz";
        }
    }

    @GetMapping("/flota/{id}/edycja")
    public String edycja(@PathVariable Long id, Model model) {
        model.addAttribute("form", pojazdService.formularzEdycji(id));
        model.addAttribute("edycja", true);
        model.addAttribute("pojazdId", id);
        wypelnijDane(model);
        return "pojazd-formularz";
    }

    @PostMapping("/flota/{id}")
    public String zapisz(@PathVariable Long id, @ModelAttribute("form") PojazdForm form, Model model) {
        try {
            pojazdService.aktualizuj(id, form);
            return "redirect:/flota?zapisano";
        } catch (BusinessException ex) {
            model.addAttribute("blad", ex.getMessage());
            model.addAttribute("edycja", true);
            model.addAttribute("pojazdId", id);
            wypelnijDane(model);
            return "pojazd-formularz";
        }
    }

    @PostMapping("/flota/{id}/serwis")
    public String serwis(@PathVariable Long id, RedirectAttributes ra) {
        try {
            pojazdService.skierujDoSerwisu(id);
        } catch (BusinessException ex) {
            ra.addFlashAttribute("blad", ex.getMessage());
        }
        return "redirect:/flota";
    }

    @PostMapping("/flota/{id}/powrot")
    public String powrot(@PathVariable Long id, RedirectAttributes ra) {
        try {
            pojazdService.przywrocZSerwisu(id);
        } catch (BusinessException ex) {
            ra.addFlashAttribute("blad", ex.getMessage());
        }
        return "redirect:/flota";
    }

    @PostMapping("/flota/{id}/wycofaj")
    public String wycofaj(@PathVariable Long id, RedirectAttributes ra) {
        try {
            pojazdService.wycofaj(id);
        } catch (BusinessException ex) {
            ra.addFlashAttribute("blad", ex.getMessage());
        }
        return "redirect:/flota";
    }

    @PostMapping("/flota/{id}/usun")
    public String usun(@PathVariable Long id, RedirectAttributes ra) {
        try {
            pojazdService.usun(id);
            ra.addFlashAttribute("komunikat", "Pojazd został usunięty.");
        } catch (BusinessException ex) {
            ra.addFlashAttribute("blad", ex.getMessage());
        }
        return "redirect:/flota";
    }

    private void wypelnijDane(Model model) {
        model.addAttribute("kategorie", pojazdService.kategorie());
        model.addAttribute("oddzialy", pojazdService.oddzialy());
        model.addAttribute("typyNadwozia", TypNadwozia.values());
    }
}
