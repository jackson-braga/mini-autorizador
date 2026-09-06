package br.com.jfintech.mini_autorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;

public interface CartaoRepository extends JpaRepository<CartaoEntity, String> {
}
