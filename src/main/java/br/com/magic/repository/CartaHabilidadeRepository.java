package br.com.magic.repository;

import br.com.magic.domain.CartaHabilidade;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CartaHabilidade entity.
 */
@SuppressWarnings("unused")
public interface CartaHabilidadeRepository extends JpaRepository<CartaHabilidade,Long> {

}
