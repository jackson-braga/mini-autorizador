package br.com.jfintech.mini_autorizador.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "transacao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_cartao", referencedColumnName = "numero_cartao", nullable = false)
    private CartaoEntity cartao;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "data_hora_transacao", nullable = false)
    private LocalDateTime dataHoraTransacao;

    @PrePersist
    public void prePersist() {
        dataHoraTransacao = LocalDateTime.now(ZoneId.systemDefault());
    }
}
