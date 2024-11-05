package com.polstat.ksa.controller;

import com.polstat.ksa.dto.KabupatenDto;
import com.polstat.ksa.entity.InformasiKsa;
import com.polstat.ksa.entity.Kabupaten;
import com.polstat.ksa.repository.InformasiKsaRepository;
import com.polstat.ksa.repository.KabupatenRepository;
import com.polstat.ksa.viewResponse.InformasiKsaResponse;
import com.polstat.ksa.viewResponse.JadwalKsa;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ksa/informasiksa")
public class InformasiKsaController {

    @Autowired
    private InformasiKsaRepository informasiKsaRepository;

    @Autowired
    private KabupatenRepository kabupatenRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createInformasiKsa(@Valid @RequestBody Map<String, Object> requestBody) {
        try {
            if (!requestBody.containsKey("idKab") || !requestBody.containsKey("lintang") ||
                    !requestBody.containsKey("bujur") || !requestBody.containsKey("segmen") ||
                    !requestBody.containsKey("tanggalpendataan")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required fields in the request.");
            }

            Long idKab = Long.parseLong(requestBody.get("idKab").toString());
            Double lintang = Double.valueOf(requestBody.get("lintang").toString());
            Double bujur = Double.valueOf(requestBody.get("bujur").toString());
            String segmen = requestBody.get("segmen").toString();
            Date tanggalpendataan = Date.valueOf(requestBody.get("tanggalpendataan").toString());

            InformasiKsa informasiKsa = new InformasiKsa();
            informasiKsa.setTanggalpendataan(tanggalpendataan);
            informasiKsa.setLintang(lintang);
            informasiKsa.setBujur(bujur);
            informasiKsa.setSegmen(segmen);

            Kabupaten kabupaten = kabupatenRepository.findById(idKab)
                    .orElseThrow(() -> new ResourceNotFoundException("Kabupaten not found with id: " + idKab));
            informasiKsa.setKabupaten(kabupaten);

            informasiKsaRepository.save(informasiKsa);

            return ResponseEntity.ok(informasiKsa);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid numeric value in the request.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating InformasiKsa.");
        }
    }

    @GetMapping("/all")
    public List<InformasiKsa> getAllInformasiKsa() {
        return informasiKsaRepository.findAll();
    }

    @GetMapping("/jadwal")
    public ResponseEntity<?> getJadwal(){
        try {
            List<Object[]> result = informasiKsaRepository.findJadwal();

            List<InformasiKsaResponse> responseList = new ArrayList<>();

            for (Object[] row : result) {
            }

            for (Object[] row : result) {
                String jadwal = row[0].toString();
                Long idKab = (Long) row[1];
                Kabupaten kabupaten = kabupatenRepository.getReferenceById(idKab);

                KabupatenDto kabu = new KabupatenDto();
                kabu.setIdKab(kabupaten.getIdKab());
                kabu.setNamaKabupaten(kabupaten.getNamaKab());
                kabu.setIdProv(kabupaten.getProvinsi().getIdProv());

                InformasiKsaResponse response = new InformasiKsaResponse(jadwal, kabu, kabupaten.getProvinsi().getNamaProvinsi());
                responseList.add(response);
            }

            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching data.");
        }
    }

    @GetMapping("/jadwalKsa")
    public ResponseEntity<?> getJadwalView(){
        List<InformasiKsa> info = informasiKsaRepository.findAll();
        List<JadwalKsa> jadwal = new ArrayList<>();

        for(InformasiKsa list : info){
            JadwalKsa jadwalKsa = new JadwalKsa();
            jadwalKsa.setTanggalPendataan(list.getTanggalpendataan());
            jadwalKsa.setNProv(list.getKabupaten().getProvinsi().getNamaProvinsi());
            jadwalKsa.setNKab(list.getKabupaten().getNamaKab());
            jadwalKsa.setLintang(list.getLintang());
            jadwalKsa.setBujur(list.getBujur());
            jadwalKsa.setSegmen(list.getSegmen());
            jadwal.add(jadwalKsa);
        }

        return ResponseEntity.ok(jadwal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInformasiKsaById(@PathVariable Long id) {
        Optional<InformasiKsa> informasiKsa = informasiKsaRepository.findById(id);
        if (informasiKsa.isPresent()) {
            return ResponseEntity.ok(informasiKsa.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("InformasiKsa not found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInformasiKsa(@PathVariable Long id) {
        try {
            informasiKsaRepository.deleteById(id);
            return ResponseEntity.ok("InformasiKsa deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting InformasiKsa.");
        }
    }

}
