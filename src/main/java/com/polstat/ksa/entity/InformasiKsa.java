package com.polstat.ksa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ksainfo")
public class InformasiKsa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date tanggalpendataan;

    @Column(nullable = false)
    private Double lintang;

    @Column(nullable = false)
    private Double bujur;

    @Column(nullable = false)
    private String segmen;

    @ManyToOne
    @JoinColumn(name = "idKab")
    private Kabupaten kabupaten;

}