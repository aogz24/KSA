package com.polstat.ksa.service;

import com.polstat.ksa.entity.Kabupaten;
import com.polstat.ksa.entity.Provinsi;
import com.polstat.ksa.repository.KabupatenRepository;
import com.polstat.ksa.repository.ProvinsiRepository;
import com.polstat.ksa.request.KabupatenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KabupatenService {
    @Autowired
    private KabupatenRepository kabupatenRepository;

    public List<Kabupaten> getAllKabupaten() {
        return kabupatenRepository.findAll();
    }

    public Kabupaten getKabupatenById(Long id) {
        return kabupatenRepository.findById(id).orElse(null);
    }

    public Kabupaten createOrUpdateKabupaten(Kabupaten kabupaten) {
        return kabupatenRepository.save(kabupaten);
    }

}
