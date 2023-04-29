package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
@Entity
//@Audited
@Table(name = "movimentacoes", schema = "public")
//@AuditTable(value = "movimentacao_audit", schema = "audit")
public class Movimentacao extends AbstractEntity{
    @Getter @Setter
    @Column(name = "entrada", nullable = false)
    private LocalDate entrada;
    @Getter @Setter
    @Column(name = "saida", nullable = false)
    private LocalDate saida;
    @Getter @Setter
    @Column(name = "tempo_desconto", nullable = false)
    private LocalTime tempoDesconto;
    @Getter @Setter
    @Column(name = "tempo", nullable = false)
    private LocalTime tempo;
    @Getter @Setter
    @Column(name = "tempo_multao", nullable = false)
    private LocalTime tempoMulta;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "veiculo", nullable = false, unique = true)
    private Veiculo veiculo;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "condutor", nullable = false)
    private Condutor condutor;
    @Getter @Setter
    @Column(name = "valor_multa", nullable = false)
    private BigDecimal valorMulta;
    @Getter @Setter
    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;
    @Getter @Setter
    @Column(name = "valor_hora", nullable = false)
    private BigDecimal valorHora;
}
