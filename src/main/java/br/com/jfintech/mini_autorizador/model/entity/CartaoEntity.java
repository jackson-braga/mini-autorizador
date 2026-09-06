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
@Table(name = "cartao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoEntity {

    @Id
    @Column(name = "numero_cartao", nullable = false, unique = true, length = 19)
    private String numeroCartao;

    @Column(name = "senha", nullable = false, length = 4)
    private String senha;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

    @Column(name = "data_hora_cadastro", nullable = false)
    private LocalDateTime dataHoraCadastro;

    @PrePersist
    public void prePersist() {
        dataHoraCadastro = LocalDateTime.now(ZoneId.systemDefault());
    }
}
