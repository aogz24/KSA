package com.polstat.ksa.service;

import com.polstat.ksa.dto.PendataanKsaDto;
import com.polstat.ksa.entity.PendataanKsa;
import java.util.List;

public interface PendataanKsaService {
    PendataanKsaDto getPendataanKsaById(Long id);
    List<PendataanKsaDto> getAllPendataanKsa();
    PendataanKsaDto createPendataanKsa(PendataanKsaDto pendataanKsaDto);
    PendataanKsaDto updatePendataanKsa(Long id, PendataanKsaDto pendataanKsaDto);
    void deletePendataanKsa(Long id);
}

