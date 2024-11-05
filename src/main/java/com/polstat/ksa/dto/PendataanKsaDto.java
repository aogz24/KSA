package com.polstat.ksa.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PendataanKsaDto {
    private Long id;
    private String faseTanam;
    private String foto;
    private Long idInfo;
}

