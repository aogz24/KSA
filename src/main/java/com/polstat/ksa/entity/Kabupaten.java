package com.polstat.ksa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "kabupaten")
public class Kabupaten {
    @Id
    private Long idKab;

    @Column(nullable = false)
    private String namaKab;

    @ManyToOne
    @JoinColumn(name = "idProv", unique = false)
    @JsonIgnore
    private Provinsi provinsi;

    @OneToMany(mappedBy = "kabupaten")
    private List<UserProfile> userProfileList;

    @OneToMany(mappedBy = "kabupaten")
    @JsonBackReference
    private List<InformasiKsa> informasiKsaList;
}
