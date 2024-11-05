package com.polstat.ksa.repository;

import com.polstat.ksa.entity.Kabupaten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KabupatenRepository extends JpaRepository<Kabupaten, Long> {
    @Query("SELECT k FROM Kabupaten k WHERE LOWER(k.namaKab) = LOWER(:namaKabupaten)")
    Kabupaten findByNamaKabupatenIgnoreCase(String namaKabupaten);

    @Query("SELECT k FROM Kabupaten k WHERE k.provinsi.idProv = :idProv")
    List<Kabupaten> findByProvinsiId(@Param("idProv") Long idProv);
}
