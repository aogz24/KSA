package com.polstat.ksa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformasiKsaDto {
    private Long id;
    private Date tanggalPendataan;
    private Double lintang;
    private Double bujur;
    private String segmen;
    private Long idKab;
}

