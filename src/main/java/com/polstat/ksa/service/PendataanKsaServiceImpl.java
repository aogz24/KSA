package com.polstat.ksa.service;

import com.polstat.ksa.dto.PendataanKsaDto;
import com.polstat.ksa.entity.PendataanKsa;
import com.polstat.ksa.entity.InformasiKsa;
import com.polstat.ksa.mapper.PendataanKsaMapper;
import com.polstat.ksa.repository.InformasiKsaRepository;
import com.polstat.ksa.repository.PendataanKsaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PendataanKsaServiceImpl implements PendataanKsaService {

    private final PendataanKsaRepository pendataanKsaRepository;
    private final PendataanKsaMapper pendataanKsaMapper;
    private final InformasiKsaRepository informasiKsaRepository;

    @Autowired
    public PendataanKsaServiceImpl(
            PendataanKsaRepository pendataanKsaRepository,
            PendataanKsaMapper pendataanKsaMapper,
            InformasiKsaRepository informasiKsaRepository) {
        this.pendataanKsaRepository = pendataanKsaRepository;
        this.pendataanKsaMapper = pendataanKsaMapper;
        this.informasiKsaRepository = informasiKsaRepository;
    }

    @Override
    public PendataanKsaDto getPendataanKsaById(Long id) {
        Optional<PendataanKsa> pendataanKsaOptional = pendataanKsaRepository.findById(id);
        return pendataanKsaOptional.map(pendataanKsaMapper::pendataanKsaToDto).orElse(null);
    }

    @Override
    public List<PendataanKsaDto> getAllPendataanKsa() {
        List<PendataanKsa> pendataanKsaList = pendataanKsaRepository.findAll();
        return pendataanKsaList.stream()
                .map(pendataanKsaMapper::pendataanKsaToDto)
                .collect(Collectors.toList());
    }



    @Override
    public PendataanKsaDto createPendataanKsa(PendataanKsaDto pendataanKsaDto) {

        if (pendataanKsaDto.getFaseTanam() == null || pendataanKsaDto.getFoto() == null || pendataanKsaDto.getIdInfo() == null) {
            return null;
        }


        PendataanKsa pendataanKsa = pendataanKsaMapper.dtoToPendataanKsa(pendataanKsaDto);


        InformasiKsa informasiKsa = informasiKsaRepository.findById(pendataanKsaDto.getIdInfo()).orElse(null);
        if (informasiKsa == null) {
            return null;
        }

        pendataanKsa.setInformasiKsa(informasiKsa);


        PendataanKsa savedPendataanKsa = pendataanKsaRepository.save(pendataanKsa);

        return pendataanKsaMapper.pendataanKsaToDto(savedPendataanKsa);
    }

    @Override
    public PendataanKsaDto updatePendataanKsa(Long id, PendataanKsaDto pendataanKsaDto) {
        Optional<PendataanKsa> optionalPendataanKsa = pendataanKsaRepository.findById(id);

        if (optionalPendataanKsa.isPresent()) {
            PendataanKsa pendataanKsa = optionalPendataanKsa.get();
            pendataanKsa.setFaseTanam(pendataanKsaDto.getFaseTanam());
            pendataanKsa.setFoto(pendataanKsaDto.getFoto());
            PendataanKsa updatedPendataanKsa = pendataanKsaRepository.save(pendataanKsa);

            return pendataanKsaMapper.pendataanKsaToDto(updatedPendataanKsa);
        } else {
            return null;
        }
    }

    @Override
    public void deletePendataanKsa(Long id) {
        pendataanKsaRepository.deleteById(id);
    }
}
