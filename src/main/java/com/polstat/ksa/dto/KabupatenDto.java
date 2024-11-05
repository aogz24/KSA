package com.polstat.ksa.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KabupatenDto {
    private Long idKab;
    private String namaKabupaten;
    private Long idProv;
}
