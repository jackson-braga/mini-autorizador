package br.com.jfintech.mini_autorizador.repository;

import br.com.jfintech.mini_autorizador.model.entity.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, Long> {
}
