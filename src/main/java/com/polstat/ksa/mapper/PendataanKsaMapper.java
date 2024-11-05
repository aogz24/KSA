package com.polstat.ksa.mapper;

import com.polstat.ksa.dto.PendataanKsaDto;
import com.polstat.ksa.entity.PendataanKsa;
import com.polstat.ksa.entity.InformasiKsa;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PendataanKsaMapper {

    public PendataanKsaDto pendataanKsaToDto(PendataanKsa pendataanKsa) {
        PendataanKsaDto pendataanKsaDto = new PendataanKsaDto();
        BeanUtils.copyProperties(pendataanKsa, pendataanKsaDto);
        if (pendataanKsa.getInformasiKsa() != null) {
            pendataanKsaDto.setIdInfo(pendataanKsa.getInformasiKsa().getId());
        }
        return pendataanKsaDto;
    }

    public PendataanKsa dtoToPendataanKsa(PendataanKsaDto pendataanKsaDto) {
        PendataanKsa pendataanKsa = new PendataanKsa();
        BeanUtils.copyProperties(pendataanKsaDto, pendataanKsa);
        if (pendataanKsaDto.getIdInfo() != null) {
            InformasiKsa informasiKsa = new InformasiKsa();
            informasiKsa.setId(pendataanKsaDto.getIdInfo());
            pendataanKsa.setInformasiKsa(informasiKsa);
        }
        return pendataanKsa;
    }
}
