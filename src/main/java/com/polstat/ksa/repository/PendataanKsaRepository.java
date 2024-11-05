package com.polstat.ksa.repository;

import com.polstat.ksa.entity.PendataanKsa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendataanKsaRepository extends JpaRepository<PendataanKsa, Long> {
    @Query(value = "SELECT i.informasi_ksa_id AS id, i.fase_tanam AS faseTanam FROM dataksa i", nativeQuery = true)
    List<Object[]> cariInfo();
}

