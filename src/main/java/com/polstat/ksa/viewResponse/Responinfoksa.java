package com.polstat.ksa.viewResponse;

import com.polstat.ksa.dto.InformasiKsaDto;
import com.polstat.ksa.dto.KabupatenDto;
import com.polstat.ksa.dto.ProvinsiDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Responinfoksa{
    private String faseTanam;
    private InformasiKsaDto informasiKsaDto;
    private KabupatenDto kabupatenDto;
    private ProvinsiDto provinsiDto;
}
