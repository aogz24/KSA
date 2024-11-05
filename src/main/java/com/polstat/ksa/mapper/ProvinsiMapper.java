package com.polstat.ksa.mapper;

import com.polstat.ksa.entity.Provinsi;
import com.polstat.ksa.dto.ProvinsiDto;

public class ProvinsiMapper {
    public static Provinsi mapToProvinsi(ProvinsiDto provinsiDto) {
        return Provinsi.builder()
                .idProv(provinsiDto.getIdProv())
                .namaProvinsi(provinsiDto.getNamaProvinsi())
                .build();
    }

    public static ProvinsiDto mapToProvinsiDto(Provinsi provinsi) {
        return ProvinsiDto.builder()
                .idProv(provinsi.getIdProv())
                .namaProvinsi(provinsi.getNamaProvinsi())
                .build();
    }
}

