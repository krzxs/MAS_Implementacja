package com.krzxs.wypozyczalnia.config;

import com.krzxs.wypozyczalnia.model.Dodatek;
import com.krzxs.wypozyczalnia.model.KategoriaPojazdu;
import com.krzxs.wypozyczalnia.model.Klient;
import com.krzxs.wypozyczalnia.model.Motocykl;
import com.krzxs.wypozyczalnia.model.Oddzial;
import com.krzxs.wypozyczalnia.model.Osoba;
import com.krzxs.wypozyczalnia.model.Platnosc;
import com.krzxs.wypozyczalnia.model.Pracownik;
import com.krzxs.wypozyczalnia.model.SamochodDostawczy;
import com.krzxs.wypozyczalnia.model.SamochodOsobowy;
import com.krzxs.wypozyczalnia.model.Ubezpieczenie;
import com.krzxs.wypozyczalnia.model.Wypozyczenie;
import com.krzxs.wypozyczalnia.model.enums.MetodaPlatnosci;
import com.krzxs.wypozyczalnia.model.enums.Stanowisko;
import com.krzxs.wypozyczalnia.model.enums.StatusPlatnosci;
import com.krzxs.wypozyczalnia.model.enums.StatusWypozyczenia;
import com.krzxs.wypozyczalnia.model.enums.TypNadwozia;
import com.krzxs.wypozyczalnia.model.enums.TypUbezpieczenia;
import com.krzxs.wypozyczalnia.repository.DodatekRepository;
import com.krzxs.wypozyczalnia.repository.KategoriaPojazduRepository;
import com.krzxs.wypozyczalnia.repository.OddzialRepository;
import com.krzxs.wypozyczalnia.repository.OsobaRepository;
import com.krzxs.wypozyczalnia.repository.PojazdRepository;
import com.krzxs.wypozyczalnia.repository.UbezpieczenieRepository;
import com.krzxs.wypozyczalnia.repository.WypozyczenieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataSeeder implements CommandLineRunner {

    private final KategoriaPojazduRepository kategoriaRepository;
    private final OddzialRepository oddzialRepository;
    private final DodatekRepository dodatekRepository;
    private final UbezpieczenieRepository ubezpieczenieRepository;
    private final OsobaRepository osobaRepository;
    private final PojazdRepository pojazdRepository;
    private final WypozyczenieRepository wypozyczenieRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(KategoriaPojazduRepository kategoriaRepository, OddzialRepository oddzialRepository, DodatekRepository dodatekRepository, UbezpieczenieRepository ubezpieczenieRepository, OsobaRepository osobaRepository, PojazdRepository pojazdRepository, WypozyczenieRepository wypozyczenieRepository, PasswordEncoder passwordEncoder) {
        this.kategoriaRepository = kategoriaRepository;
        this.oddzialRepository = oddzialRepository;
        this.dodatekRepository = dodatekRepository;
        this.ubezpieczenieRepository = ubezpieczenieRepository;
        this.osobaRepository = osobaRepository;
        this.pojazdRepository = pojazdRepository;
        this.wypozyczenieRepository = wypozyczenieRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (osobaRepository.count() > 0) {
            return;
        }

        KategoriaPojazdu ekonomiczna = new KategoriaPojazdu("Ekonomiczna", "Małe, oszczędne samochody", new BigDecimal("1.0"));
        KategoriaPojazdu premium = new KategoriaPojazdu("Premium", "Samochody klasy wyższej", new BigDecimal("1.8"));
        KategoriaPojazdu dostawcza = new KategoriaPojazdu("Dostawcza", "Pojazdy dostawcze", new BigDecimal("1.3"));
        KategoriaPojazdu motocyklowa = new KategoriaPojazdu("Motocykl", "Jednoślady", new BigDecimal("0.8"));
        kategoriaRepository.saveAll(List.of(ekonomiczna, premium, dostawcza, motocyklowa));

        Oddzial centrum = new Oddzial("Warszawa-Centrum", "ul. Marszałkowska 1", "Warszawa");
        Oddzial port = new Oddzial("Gdynia-Główna", "ul. Portowa 5", "Gdynia");
        oddzialRepository.saveAll(List.of(centrum, port));

        Dodatek gps = new Dodatek("GPS", "Nawigacja satelitarna", new BigDecimal("15"));
        Dodatek fotelik = new Dodatek("Fotelik dziecięcy", "Fotelik 9-36 kg", new BigDecimal("10"));
        Dodatek drugiKierowca = new Dodatek("Dodatkowy kierowca", "Drugi uprawniony kierowca", new BigDecimal("20"));
        Dodatek lancuchy = new Dodatek("Łańcuchy śniegowe", "Komplet łańcuchów śniegowych", new BigDecimal("8"));
        dodatekRepository.saveAll(List.of(gps, fotelik, drugiKierowca, lancuchy));

        Ubezpieczenie oc = new Ubezpieczenie(TypUbezpieczenia.OC, "Odpowiedzialność cywilna", new BigDecimal("0"));
        Ubezpieczenie ac = new Ubezpieczenie(TypUbezpieczenia.AC, "Autocasco", new BigDecimal("40"));
        Ubezpieczenie pelne = new Ubezpieczenie(TypUbezpieczenia.PELNE, "Pełna ochrona", new BigDecimal("70"));
        ubezpieczenieRepository.saveAll(List.of(oc, ac, pelne));

        Osoba osobaMarek = new Osoba("Marek", "Nowak", "marek.nowak@gmail.pl", "123456789", LocalDate.of(1990, 5, 12));
        Pracownik konsultant = new Pracownik("0001", Stanowisko.KONSULTANT, LocalDate.of(2020, 1, 15));
        konsultant.setOddzial(centrum);
        konsultant.setPin(passwordEncoder.encode("1234"));
        osobaMarek.przypiszPracownika(konsultant);
        osobaRepository.save(osobaMarek);

        Osoba osobaEwa = new Osoba("Ewa", "Kowalska", "ewa.kowalska@gmail.pl", "987654321", LocalDate.of(1985, 9, 3));
        Pracownik kierownik = new Pracownik("9999", Stanowisko.KIEROWNIK, LocalDate.of(2018, 6, 1));
        kierownik.setOddzial(port);
        kierownik.setPin(passwordEncoder.encode("4321"));
        osobaEwa.przypiszPracownika(kierownik);
        osobaRepository.save(osobaEwa);

        Klient jan = zarejestrujKlienta("Jan", "Kowalski", "jan.kowalski@example.com", "700300400",
                LocalDate.of(1988, 2, 20), "KL/ZJC1NJU3", "ABC123456", LocalDate.now().plusYears(3));
        zarejestrujKlienta("Anna", "Wójcik", "anna.wojcik@example.com", "700300401",
                LocalDate.of(1995, 11, 7), "KL/YZQYMDRM", "DEF654321", LocalDate.now().plusYears(2));
        zarejestrujKlienta("Piotr", "Wiśniewski", "piotr.wisniewski@example.com", "700300402",
                LocalDate.of(1979, 7, 30), "KL/OGY0ZJRJ", "GHI112233", LocalDate.now().plusYears(5));
        zarejestrujKlienta("Tomasz", "Lewandowski", "tomasz.lewandowski@example.com", "700300403",
                LocalDate.of(2000, 4, 18), "KL/MZAWN2IX", "JKL445566", LocalDate.now().minusMonths(1));

        SamochodOsobowy corolla = new SamochodOsobowy("WA 12345", "Toyota", "Corolla", 2022, 30000, new BigDecimal("120"), 5, TypNadwozia.SEDAN);
        corolla.setKategoria(ekonomiczna);
        corolla.setOddzial(centrum);
        SamochodOsobowy x5 = new SamochodOsobowy("WB 55512", "BMW", "X5", 2023, 15000, new BigDecimal("250"), 5, TypNadwozia.SUV);
        x5.setKategoria(premium);
        x5.setOddzial(centrum);
        SamochodDostawczy transit = new SamochodDostawczy("WC 09823", "Ford", "Transit", 2021, 80000, new BigDecimal("180"), 1200.0, 12.0);
        transit.setKategoria(dostawcza);
        transit.setOddzial(port);
        Motocykl cb500 = new Motocykl("WD 41200", "Honda", "CB500", 2022, 12000, new BigDecimal("90"), 500);
        cb500.setKategoria(motocyklowa);
        cb500.setOddzial(port);
        pojazdRepository.saveAll(List.of(corolla, x5, transit, cb500));

        Wypozyczenie historyczne = new Wypozyczenie(String.format("WYP/%d/H0W2EVIM", LocalDate.now().getYear()), LocalDate.now().minusDays(20), LocalDate.now().minusDays(16), StatusWypozyczenia.ZAKONCZONE);
        jan.dodajWypozyczenie(historyczne);
        corolla.dodajWypozyczenie(historyczne);
        historyczne.setPojazd(corolla);
        konsultant.dodajObsluga(historyczne);
        historyczne.setUbezpieczenie(ac);
        historyczne.dodajDodatek(gps, 1);
        Platnosc platnoscHist = new Platnosc(historyczne.getKosztCalkowity(), LocalDateTime.now().minusDays(20), MetodaPlatnosci.KARTA, StatusPlatnosci.OPLACONA);
        historyczne.dodajPlatnosc(platnoscHist);
        wypozyczenieRepository.save(historyczne);
    }

    private Klient zarejestrujKlienta(String imie, String nazwisko, String email, String telefon, LocalDate dataUrodzenia, String numerKlienta, String numerPrawaJazdy, LocalDate waznoscPrawaJazdy) {
        Osoba osoba = new Osoba(imie, nazwisko, email, telefon, dataUrodzenia);
        Klient klient = new Klient(numerKlienta, LocalDate.now().minusYears(1), numerPrawaJazdy, waznoscPrawaJazdy);
        osoba.przypiszKlienta(klient);
        osobaRepository.save(osoba);
        return klient;
    }
}
