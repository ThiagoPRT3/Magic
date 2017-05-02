package br.com.magic.repository;

import br.com.magic.domain.Habilidade;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Habilidade entity.
 */
@SuppressWarnings("unused")
public interface HabilidadeRepository extends JpaRepository<Habilidade,Long> {

}
