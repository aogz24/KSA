package com.polstat.ksa.service;

import com.polstat.ksa.auth.exception.InformasiKsaNotFoundException;
import com.polstat.ksa.dto.InformasiKsaDto;
import com.polstat.ksa.entity.InformasiKsa;
import com.polstat.ksa.entity.Kabupaten;
import com.polstat.ksa.mapper.InformasiKsaMapper;
import com.polstat.ksa.mapper.UserProfileMapper;
import com.polstat.ksa.repository.InformasiKsaRepository;
import com.polstat.ksa.repository.KabupatenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InformasiKsaService {
    private final InformasiKsaRepository informasiKsaRepository;
    private final KabupatenRepository kabupatenRepository;

    @Autowired
    public InformasiKsaService(InformasiKsaRepository informasiKsaRepository, KabupatenRepository kabupatenRepository) {
        this.informasiKsaRepository = informasiKsaRepository;
        this.kabupatenRepository = kabupatenRepository;
    }

    public List<InformasiKsaDto> getAllInformasiKsa() {
        List<InformasiKsa> informasiKsaList = informasiKsaRepository.findAll();
        return InformasiKsaMapper.mapToInformasiKsaDtoList(informasiKsaList);
    }

    public InformasiKsaDto getInformasiKsaById(Long id) {
        Optional<InformasiKsa> informasiKsaOptional = informasiKsaRepository.findById(id);

        if (informasiKsaOptional.isPresent()) {
            InformasiKsa informasiKsa = informasiKsaOptional.get();
            return InformasiKsaMapper.mapToInformasiKsaDto(informasiKsa);
        } else {
            throw new InformasiKsaNotFoundException("InformasiKsa not found with id: " + id);
        }
    }

    public InformasiKsaDto createOrUpdateInformasiKsa(InformasiKsaDto informasiKsaDto) {

        InformasiKsa informasiKsa = InformasiKsaMapper.mapToInformasiKsa(informasiKsaDto);

        informasiKsa = informasiKsaRepository.save(informasiKsa);

        return InformasiKsaMapper.mapToInformasiKsaDto(informasiKsa);
    }

    public void deleteInformasiKsa(Long id) {
        if (informasiKsaRepository.existsById(id)) {
            informasiKsaRepository.deleteById(id);
        } else {
            throw new InformasiKsaNotFoundException("InformasiKsa not found with id: " + id);
        }
    }

}

