package com.polstat.ksa.service;

import com.polstat.ksa.entity.Provinsi;
import com.polstat.ksa.repository.ProvinsiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinsiService {
    @Autowired
    private ProvinsiRepository provinsiRepository;

    public List<Provinsi> getAllProvinsi() {
        return provinsiRepository.findAll();
    }

    public Provinsi getProvinsiById(Long id) {
        return provinsiRepository.findById(id).orElse(null);
    }

    public Provinsi createOrUpdateProvinsi(Provinsi provinsi) {
        provinsi.setIdProv(provinsi.getIdProv());
        return provinsiRepository.save(provinsi);
    }

    public void deleteProvinsi(Long id) {
        provinsiRepository.deleteById(id);
    }
}
