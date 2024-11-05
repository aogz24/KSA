package com.polstat.ksa.repository;

import com.polstat.ksa.entity.InformasiKsa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformasiKsaRepository extends JpaRepository<InformasiKsa, Long> {
    @Query(value = "SELECT i.tanggalpendataan AS jadwal, i.id_kab AS idKab FROM ksainfo i", nativeQuery = true)
    List<Object[]> findJadwal();
    @Query("SELECT i FROM InformasiKsa i WHERE i.kabupaten.idKab = :idKab")
    List<InformasiKsa> findByIdKab(@Param("idKab") Long idKab);
}
