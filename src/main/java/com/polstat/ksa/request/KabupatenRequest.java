package com.polstat.ksa.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KabupatenRequest {
    private Long idKab;
    private String namaKab;
    private Long idProv;

}

