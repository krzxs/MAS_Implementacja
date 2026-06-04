package com.krzxs.wypozyczalnia.controller;

import com.krzxs.wypozyczalnia.service.PojazdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/flota/{id}/serwis")
    public String serwis(@PathVariable Long id) {
        pojazdService.skierujDoSerwisu(id);
        return "redirect:/flota";
    }
}
