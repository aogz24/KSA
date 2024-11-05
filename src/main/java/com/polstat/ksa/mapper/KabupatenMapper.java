package com.polstat.ksa.mapper;

import com.polstat.ksa.dto.KabupatenDto;
import com.polstat.ksa.entity.Kabupaten;
import com.polstat.ksa.entity.Provinsi;

public class KabupatenMapper {
    public static Kabupaten mapToKabupaten(KabupatenDto kabupatenDto) {
        return Kabupaten.builder()
                .idKab(kabupatenDto.getIdKab())
                .namaKab(kabupatenDto.getNamaKabupaten())
                .provinsi(Provinsi.builder().idProv(kabupatenDto.getIdProv()).build())
                .build();
    }

    public static KabupatenDto mapToKabupatenDto(Kabupaten kabupaten) {
        return KabupatenDto.builder()
                .idKab(kabupaten.getIdKab())
                .namaKabupaten(kabupaten.getNamaKab())
                .idProv(kabupaten.getProvinsi().getIdProv())
                .build();
    }
}

