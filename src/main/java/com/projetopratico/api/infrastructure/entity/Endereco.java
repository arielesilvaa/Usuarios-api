package com.projetopratico.api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rua", length = 150)
    private String rua;

    @Column(name = "numero")
    private Long numero;

    // Aumentei o tamanho de complemento de 10 para 255
    @Column(name = "complemento", length = 255)
    private String complemento;

    @Column(name = "cidade", length = 150)
    private String cidade;

    @Column(name = "estado", length = 2)
    private String estado;

    @Column(name = "cep", length = 9)
    private String cep;

    @Column(name = "usuario_id")
    private Long usuario_id;
}