package com.polstat.ksa.viewResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JadwalKsa {
    private Date tanggalPendataan;
    private String nProv;
    private String nKab;
    private String segmen;
    private Double bujur;
    private Double lintang;
}
