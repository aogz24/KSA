package com.polstat.ksa.controller;

import com.polstat.ksa.dto.InformasiKsaDto;
import com.polstat.ksa.dto.KabupatenDto;
import com.polstat.ksa.dto.PendataanKsaDto;
import com.polstat.ksa.dto.ProvinsiDto;
import com.polstat.ksa.entity.InformasiKsa;
import com.polstat.ksa.entity.Kabupaten;
import com.polstat.ksa.entity.PendataanKsa;
import com.polstat.ksa.entity.Provinsi;
import com.polstat.ksa.repository.InformasiKsaRepository;
import com.polstat.ksa.repository.KabupatenRepository;
import com.polstat.ksa.repository.PendataanKsaRepository;
import com.polstat.ksa.service.PendataanKsaService;
import com.polstat.ksa.viewResponse.JadwalKsaByKab;
import com.polstat.ksa.viewResponse.KsaResponse;
import com.polstat.ksa.viewResponse.Responinfoksa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ksa/pendataanksa")
public class PendataanKsaController {

    private final PendataanKsaService pendataanKsaService;

    private final PendataanKsaRepository pendataanKsaRepository;
    private final InformasiKsaRepository informasiKsaRepository;

    private final KabupatenRepository kabupatenRepository;

    @Autowired
    public PendataanKsaController(PendataanKsaService pendataanKsaService, PendataanKsaRepository pendataanKsaRepository, InformasiKsaRepository informasiKsaRepository, KabupatenRepository kabupatenRepository) {
        this.pendataanKsaService = pendataanKsaService;
        this.pendataanKsaRepository = pendataanKsaRepository;
        this.informasiKsaRepository = informasiKsaRepository;
        this.kabupatenRepository = kabupatenRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PendataanKsaDto> getPendataanKsaById(@PathVariable Long id) {
        PendataanKsaDto pendataanKsaDto = pendataanKsaService.getPendataanKsaById(id);
        return ResponseEntity.ok(pendataanKsaDto);
    }

    @GetMapping
    public ResponseEntity<List<PendataanKsaDto>> getAllPendataanKsa() {
        List<PendataanKsaDto> pendataanKsaList = pendataanKsaService.getAllPendataanKsa();
        return ResponseEntity.ok(pendataanKsaList);
    }

    @PostMapping("/create")
    public ResponseEntity<PendataanKsaDto> createPendataanKsa(@RequestBody Map<String, String> requestMap) {
        String faseTanam = requestMap.get("faseTanam");
        String foto = requestMap.get("foto");
        String idInfoStr = requestMap.get("idInfo");


        if (faseTanam == null || foto == null || idInfoStr == null) {
            return ResponseEntity.badRequest().build();
        }


        Long idInfo;
        try {
            idInfo = Long.parseLong(idInfoStr);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

        PendataanKsaDto pendataanKsaDto = new PendataanKsaDto();
        pendataanKsaDto.setFaseTanam(faseTanam);
        pendataanKsaDto.setFoto(foto);
        pendataanKsaDto.setIdInfo(idInfo);

        PendataanKsaDto createdPendataanKsaDto = pendataanKsaService.createPendataanKsa(pendataanKsaDto);

        if (createdPendataanKsaDto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPendataanKsaDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PendataanKsaDto> updatePendataanKsa(
            @PathVariable Long id,
            @RequestBody PendataanKsaDto pendataanKsaDto
    ) {
        try {
            PendataanKsaDto updatedPendataanKsaDto = pendataanKsaService.updatePendataanKsa(id, pendataanKsaDto);
            if (updatedPendataanKsaDto != null) {
                return ResponseEntity.ok(updatedPendataanKsaDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePendataanKsa(@PathVariable Long id) {
        pendataanKsaService.deletePendataanKsa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/infoksa/{id}")
    public ResponseEntity<?> getInfoById(@PathVariable Long id){
        try {
            PendataanKsa pendataan = pendataanKsaRepository.getReferenceById(id);
            KsaResponse ksa = new KsaResponse();
            ksa.setNKab(pendataan.getInformasiKsa().getKabupaten().getNamaKab());
            ksa.setNProv(pendataan.getInformasiKsa().getKabupaten().getProvinsi().getNamaProvinsi());
            ksa.setLintang(pendataan.getInformasiKsa().getLintang());
            ksa.setBujur(pendataan.getInformasiKsa().getBujur());
            ksa.setSegmen(pendataan.getInformasiKsa().getSegmen());
            ksa.setTanggalPendataan(pendataan.getInformasiKsa().getTanggalpendataan());
            ksa.setFaseTanam(pendataan.getFaseTanam());
            return ResponseEntity.ok(ksa);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @GetMapping("/infoksa/bykab/{nkab}")
    public ResponseEntity<?> getJadwalByKabupaten(@PathVariable String nkab) {
        try {
            Kabupaten kabupaten = kabupatenRepository.findByNamaKabupatenIgnoreCase(nkab);

            if (kabupaten == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kabupaten not found");
            }

            List<InformasiKsa> list = informasiKsaRepository.findByIdKab(kabupaten.getIdKab());
            List<JadwalKsaByKab> jadwalKsaByKabs = new ArrayList<>();

            for (InformasiKsa info : list) {
                JadwalKsaByKab jadwal = new JadwalKsaByKab();
                jadwal.setTanggalPendataan(info.getTanggalpendataan());
                jadwal.setLintang(info.getLintang());
                jadwal.setBujur(info.getBujur());
                jadwal.setSegmen(info.getSegmen());
                jadwalKsaByKabs.add(jadwal);
            }

            return ResponseEntity.ok(jadwalKsaByKabs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }

    @GetMapping("/infoksa")
    public ResponseEntity<?> getinfo(){
        try {
            List<Object[]> result = pendataanKsaRepository.cariInfo();

            List<Responinfoksa> responseList = new ArrayList<>();

            for (Object[] row : result) {
                Long idInfo = (Long) row[0];
                String faseTanam = row[1].toString();
                
                InformasiKsa informasiKsa = informasiKsaRepository.getReferenceById(idInfo);
                InformasiKsaDto info = new InformasiKsaDto();
                info.setTanggalPendataan(informasiKsa.getTanggalpendataan());
                info.setSegmen(informasiKsa.getSegmen());
                info.setBujur(informasiKsa.getBujur());
                info.setLintang(informasiKsa.getLintang());
                info.setId(idInfo);
                info.setIdKab(informasiKsa.getKabupaten().getIdKab());

                Kabupaten kabupatenDto = informasiKsa.getKabupaten();
                KabupatenDto kab = new KabupatenDto();
                kab.setIdProv(kabupatenDto.getProvinsi().getIdProv());
                kab.setNamaKabupaten(kabupatenDto.getNamaKab());
                kab.setIdKab(kabupatenDto.getIdKab());

                Provinsi provinsi = kabupatenDto.getProvinsi();
                ProvinsiDto prov = new ProvinsiDto();
                prov.setIdProv(provinsi.getIdProv());
                prov.setNamaProvinsi(provinsi.getNamaProvinsi());

                Responinfoksa response = new Responinfoksa(faseTanam, info, kab, prov);
                responseList.add(response);
            }

            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching data.");
        }
    }

}
