package com.polstat.ksa.controller;

import com.polstat.ksa.entity.Kabupaten;
import com.polstat.ksa.entity.Provinsi;
import com.polstat.ksa.repository.KabupatenRepository;
import com.polstat.ksa.repository.ProvinsiRepository;
import com.polstat.ksa.service.KabupatenService;
import com.polstat.ksa.service.ProvinsiService;
import com.polstat.ksa.viewResponse.KabupatenByProvinsi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kabupaten")
public class KabupatenController {
    @Autowired
    private KabupatenService kabupatenService;
    @Autowired
    private ProvinsiService provinsiService;
    private final ProvinsiRepository provinsiRepository;
    private final KabupatenRepository kabupatenRepository;

    public KabupatenController(ProvinsiRepository provinsiRepository, KabupatenRepository kabupatenRepository) {
        this.provinsiRepository = provinsiRepository;
        this.kabupatenRepository = kabupatenRepository;
    }

    @GetMapping("")
    public List<Kabupaten> getAllKabupaten() {
        return kabupatenService.getAllKabupaten();
    }

    @GetMapping("/{id}")
    public Kabupaten getKabupatenById(@PathVariable Long id) {
        return kabupatenService.getKabupatenById(id);
    }

    @PostMapping("/create")
    public Kabupaten createOrUpdateKabupaten(@RequestBody Map<String, String> requestBody) throws RuntimeException {
        Long idKab = Long.parseLong(requestBody.get("idKab"));
        String namaKabupaten = requestBody.get("namaKab");
        Long idProv = Long.parseLong(requestBody.get("idProv"));

        Kabupaten existingKabupaten = kabupatenService.getKabupatenById(idKab);

        if (existingKabupaten != null) {
            existingKabupaten.setNamaKab(namaKabupaten);

            Provinsi provinsi = provinsiService.getProvinsiById(idProv);

            if (provinsi != null) {
                existingKabupaten.setProvinsi(provinsi);
                return kabupatenService.createOrUpdateKabupaten(existingKabupaten);
            } else {
                throw new RuntimeException("Provinsi dengan ID " + idProv + " tidak ditemukan.");
            }
        } else {
            Kabupaten newKabupaten = new Kabupaten();
            newKabupaten.setIdKab(idKab);
            newKabupaten.setNamaKab(namaKabupaten);

            Provinsi provinsi = provinsiService.getProvinsiById(idProv);

            if (provinsi != null) {
                newKabupaten.setProvinsi(provinsi);
                return kabupatenService.createOrUpdateKabupaten(newKabupaten);
            } else {
                throw new RuntimeException("Provinsi dengan ID " + idProv + " tidak ditemukan.");
            }
        }
    }

    @PutMapping("/update/{idKab}")
    public Kabupaten updateKabupaten(@PathVariable Long idKab, @RequestBody Map<String, String> requestBody) throws RuntimeException {
        String namaKabupaten = requestBody.get("namaKab");
        Long idProv = Long.parseLong(requestBody.get("idProv"));

        Kabupaten existingKabupaten = kabupatenService.getKabupatenById(idKab);

        if (existingKabupaten != null) {
            existingKabupaten.setNamaKab(namaKabupaten);

            Provinsi provinsi = provinsiService.getProvinsiById(idProv);

            if (provinsi != null) {
                existingKabupaten.setProvinsi(provinsi);
                return kabupatenService.createOrUpdateKabupaten(existingKabupaten);
            } else {
                throw new RuntimeException("Provinsi dengan ID " + idProv + " tidak ditemukan.");
            }
        } else {
            throw new RuntimeException("Kabupaten dengan ID " + idKab + " tidak ditemukan.");
        }
    }


    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(RuntimeException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ResponseBody
        public String handleRuntimeException(RuntimeException ex) {
            return ex.getMessage();
        }
    }

    @GetMapping("/getkabbyprov/{nprov}")
    private ResponseEntity<?> getKabupatenByNamaProv(@PathVariable String nprov) {
        try {
            Provinsi prov = provinsiRepository.findByNamaProvinsi(nprov);
            if (prov == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provinsi not found");
            }
            List<Kabupaten> kab = kabupatenRepository.findByProvinsiId(prov.getIdProv());
            List<KabupatenByProvinsi> list = new ArrayList<>();
            for (Kabupaten kabupaten : kab) {
                KabupatenByProvinsi k = new KabupatenByProvinsi();
                k.setNKab(kabupaten.getNamaKab());
                k.setIdKab(kabupaten.getIdKab());
                list.add(k);
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
