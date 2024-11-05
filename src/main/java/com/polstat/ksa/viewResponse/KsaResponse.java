package com.polstat.ksa.viewResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KsaResponse {
    private String nKab;
    private String nProv;
    private Double lintang;
    private Double bujur;
    private String segmen;
    private Date tanggalPendataan;
    private String faseTanam;
}
