package br.com.uniamerica.estacionamento.entity;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
//Audited
@Table(name = "veiculos", schema = "public")
//@AuditTable(value = "veiculo_audit", schema = "audit")

public class Veiculo extends AbstractEntity {

    @Getter @Setter
    @Column(name = "placa", length = 10, nullable = false, unique = true)
    private String placa;
    @Getter @Setter
    @Column(name = "cor", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Cor cor;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "modelo", nullable = false)
    private Modelo modelo;
    @Getter @Setter
    @Column(name = "tipo", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Getter @Setter
    @Column(name = "ano", nullable = false)
    private LocalDate ano;
}
