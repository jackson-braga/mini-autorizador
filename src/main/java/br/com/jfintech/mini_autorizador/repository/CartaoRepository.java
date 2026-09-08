package br.com.jfintech.mini_autorizador.repository;

import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface CartaoRepository extends JpaRepository<CartaoEntity, String> {

//    @Lock(LockModeType.PESSIMISTIC_WRITE) // Não funciona utilizar no MySQL
    @Query("select c from CartaoEntity c where c.numeroCartao = :numeroCartao")
    Optional<CartaoEntity> findByIdForUpdate(@Param("numeroCartao") String numeroCartao);

    @Query("select c.saldo from CartaoEntity c where c.numeroCartao = :numeroCartao")
    Optional<BigDecimal> findSaldoByNumeroCartao(@Param("numeroCartao") String numeroCartao);

    @Modifying
    @Transactional
    @Query("update CartaoEntity c set c.saldo = :saldo where c.numeroCartao = :numeroCartao")
    int atualizarSaldoPorNumeroCartao(@Param("numeroCartao") String numeroCartao, @Param("saldo") BigDecimal saldo);
}
