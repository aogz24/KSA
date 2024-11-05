package com.polstat.ksa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "provinsi")
public class Provinsi {
    @Id
    private Long idProv;

    @Column(nullable = false)
    private String namaProvinsi;

    @OneToMany(mappedBy = "provinsi")
    private List<Kabupaten> kabupatenList;
}
