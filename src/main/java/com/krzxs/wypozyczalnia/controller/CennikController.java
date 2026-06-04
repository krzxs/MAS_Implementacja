package com.krzxs.wypozyczalnia.controller;

import com.krzxs.wypozyczalnia.service.KategoriaPojazduService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/cennik")
public class CennikController {

    private final KategoriaPojazduService kategoriaPojazduService;

    public CennikController(KategoriaPojazduService kategoriaPojazduService) {
        this.kategoriaPojazduService = kategoriaPojazduService;
    }

    @GetMapping
    public String cennik(Model model) {
        model.addAttribute("kategorie", kategoriaPojazduService.lista());
        return "cennik";
    }

    @PostMapping("/{id}")
    public String aktualizuj(@PathVariable Long id, @RequestParam BigDecimal mnoznikCeny) {
        kategoriaPojazduService.aktualizuj(id, mnoznikCeny);
        return "redirect:/cennik?zapisano";
    }
}
