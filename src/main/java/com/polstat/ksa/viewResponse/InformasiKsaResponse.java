package com.polstat.ksa.viewResponse;

import com.polstat.ksa.dto.KabupatenDto;
import lombok.Getter;
import lombok.Setter;

public class InformasiKsaResponse {
    @Getter
    private String jadwal;
    @Getter
    @Setter
    private String provinsi;
    private KabupatenDto kabupaten;


    public InformasiKsaResponse() {
    }

    public InformasiKsaResponse(String jadwal, KabupatenDto kabupaten, String provinsi) {
        this.jadwal = jadwal;
        this.kabupaten = kabupaten;
        this.provinsi = provinsi;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public KabupatenDto getIdKab() {
        return this.kabupaten;
    }

    public void setIdKab(KabupatenDto kabupaten) {
        this.kabupaten=kabupaten;
    }
}
