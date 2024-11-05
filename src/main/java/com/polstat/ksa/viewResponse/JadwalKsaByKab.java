package com.polstat.ksa.viewResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class JadwalKsaByKab {
    private Date tanggalPendataan;
    private Double lintang;
    private Double bujur;
    private String segmen;
}
