package com.polstat.ksa.controller;

import com.polstat.ksa.entity.Provinsi;
import com.polstat.ksa.service.ProvinsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/provinsi")
public class ProvinsiController {
    @Autowired
    private ProvinsiService provinsiService;

    @GetMapping("")
    public List<Provinsi> getAllProvinsi() {
        return provinsiService.getAllProvinsi();
    }

    @GetMapping("/{id}")
    public Provinsi getProvinsiById(@PathVariable Long id) {
        return provinsiService.getProvinsiById(id);
    }


    @PostMapping("/create")
    public Provinsi createOrUpdateProvinsi(@RequestBody Map<String, String> requestBody) {
        Long idProv = Long.parseLong(requestBody.get("idProv"));
        String namaProvinsi = requestBody.get("namaProvinsi");
        Provinsi provinsi = new Provinsi();
        provinsi.setIdProv(idProv);
        provinsi.setNamaProvinsi(namaProvinsi);
        return provinsiService.createOrUpdateProvinsi(provinsi);
    }

    @PutMapping("/update/{idProv}")
    public Provinsi updateProvinsi(@PathVariable Long idProv, @RequestBody Map<String, String> requestBody) throws RuntimeException {
        String namaProvinsi = requestBody.get("namaProvinsi");
        System.out.println(namaProvinsi);

        Provinsi existingProvinsi = provinsiService.getProvinsiById(idProv);

        System.out.println(existingProvinsi.getIdProv());
        if (existingProvinsi != null) {
            existingProvinsi.setNamaProvinsi(namaProvinsi);
            existingProvinsi.setIdProv(idProv);
            return provinsiService.createOrUpdateProvinsi(existingProvinsi);
        } else {
            throw new RuntimeException("Provinsi dengan ID " + idProv + " tidak ditemukan.");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProvinsi(@PathVariable Long id) {
        provinsiService.deleteProvinsi(id);
        return ResponseEntity.ok("Berhasil dihapus");
    }
}
