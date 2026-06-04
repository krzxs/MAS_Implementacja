package com.krzxs.wypozyczalnia.controller;

import com.krzxs.wypozyczalnia.exception.BusinessException;
import com.krzxs.wypozyczalnia.model.Pojazd;
import com.krzxs.wypozyczalnia.model.Wypozyczenie;
import com.krzxs.wypozyczalnia.model.enums.MetodaPlatnosci;
import com.krzxs.wypozyczalnia.service.KatalogService;
import com.krzxs.wypozyczalnia.service.KlientService;
import com.krzxs.wypozyczalnia.service.PojazdService;
import com.krzxs.wypozyczalnia.service.WypozyczenieService;
import com.krzxs.wypozyczalnia.web.WypozyczenieForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/wypozyczenie")
public class WypozyczenieController {

    private final WypozyczenieService wypozyczenieService;
    private final PojazdService pojazdService;
    private final KlientService klientService;
    private final KatalogService katalogService;

    public WypozyczenieController(WypozyczenieService wypozyczenieService, PojazdService pojazdService, KlientService klientService, KatalogService katalogService) {
        this.wypozyczenieService = wypozyczenieService;
        this.pojazdService = pojazdService;
        this.klientService = klientService;
        this.katalogService = katalogService;
    }

    @GetMapping("/nowe")
    public String nowe(@RequestParam Long pojazdId, Model model) {
        WypozyczenieForm form = new WypozyczenieForm();
        form.setPojazdId(pojazdId);
        form.setDataOd(LocalDate.now());
        form.setDataDo(LocalDate.now().plusDays(3));
        model.addAttribute("form", form);
        wypelnijDane(model, pojazdId);
        return "wypozyczenie-nowe";
    }

    @PostMapping
    public String utworz(@ModelAttribute("form") WypozyczenieForm form, java.security.Principal principal, Model model) {
        try {
            Wypozyczenie wypozyczenie = wypozyczenieService.wypozycz(form, principal.getName());
            return "redirect:/wypozyczenie/" + wypozyczenie.getId();
        } catch (BusinessException ex) {
            model.addAttribute("blad", ex.getMessage());
            wypelnijDane(model, form.getPojazdId());
            return "wypozyczenie-nowe";
        }
    }

    @GetMapping("/{id}")
    public String szczegoly(@PathVariable Long id, Model model) {
        model.addAttribute("w", wypozyczenieService.znajdz(id));
        return "wypozyczenie-szczegoly";
    }

    @PostMapping("/{id}/zwrot")
    public String zwrot(@PathVariable Long id,
                        @RequestParam(defaultValue = "false") boolean szkoda,
                        @RequestParam(required = false) String opisSzkody) {
        wypozyczenieService.zwroc(id, szkoda, opisSzkody);
        return "redirect:/wypozyczenie/" + id;
    }

    private void wypelnijDane(Model model, Long pojazdId) {
        Pojazd pojazd = pojazdService.znajdz(pojazdId);
        model.addAttribute("pojazd", pojazd);
        model.addAttribute("klienci", klientService.lista());
        model.addAttribute("dodatki", katalogService.dodatki());
        model.addAttribute("ubezpieczenia", katalogService.ubezpieczenia());
        model.addAttribute("metody", MetodaPlatnosci.values());
    }
}
