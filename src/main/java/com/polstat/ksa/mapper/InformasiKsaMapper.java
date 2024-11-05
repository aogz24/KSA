package com.polstat.ksa.mapper;

import com.polstat.ksa.dto.InformasiKsaDto;
import com.polstat.ksa.entity.InformasiKsa;
import org.springframework.stereotype.Component;

import com.polstat.ksa.dto.InformasiKsaDto;
import com.polstat.ksa.entity.InformasiKsa;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class InformasiKsaMapper {
    public static InformasiKsaDto mapToInformasiKsaDto(InformasiKsa informasiKsa) {
        InformasiKsaDto informasiKsaDto = new InformasiKsaDto();
        BeanUtils.copyProperties(informasiKsa, informasiKsaDto);
        return informasiKsaDto;
    }

    public static List<InformasiKsaDto> mapToInformasiKsaDtoList(List<InformasiKsa> informasiKsaList) {
        List<InformasiKsaDto> informasiKsaDtoList = new ArrayList<>();
        for (InformasiKsa informasiKsa : informasiKsaList) {
            informasiKsaDtoList.add(mapToInformasiKsaDto(informasiKsa));
        }
        return informasiKsaDtoList;
    }

    public static InformasiKsa mapToInformasiKsa(InformasiKsaDto informasiKsaDto) {
        InformasiKsa informasiKsa = new InformasiKsa();
        BeanUtils.copyProperties(informasiKsaDto, informasiKsa);
        return informasiKsa;
    }
}


