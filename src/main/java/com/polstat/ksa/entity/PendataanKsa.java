package com.polstat.ksa.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dataksa")
public class PendataanKsa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String faseTanam;

    @Column(nullable = false)
    private String foto;

    @OneToOne
    private InformasiKsa informasiKsa;
}
