package br.com.jfintech.mini_autorizador.repository;

import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<CartaoEntity, String> {
}
