package br.com.jfintech.mini_autorizador.repository;

import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface CartaoRepository extends JpaRepository<CartaoEntity, String> {

    @Query("select c.saldo from CartaoEntity c where c.numeroCartao = :numeroCartao")
    Optional<BigDecimal> findSaldoByNumeroCartao(@Param("numeroCartao") String numeroCartao);
}
