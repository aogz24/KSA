package com.polstat.ksa.repository;

import com.polstat.ksa.entity.Provinsi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProvinsiRepository extends JpaRepository<Provinsi, Long> {
    @Query("SELECT p FROM Provinsi p WHERE LOWER(p.namaProvinsi) = LOWER(:namaProvinsi)")
    Provinsi findByNamaProvinsi(@Param("namaProvinsi") String namaProvinsi);
}

