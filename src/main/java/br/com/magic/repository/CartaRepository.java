package br.com.magic.repository;

import br.com.magic.domain.Carta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Carta entity.
 */
@SuppressWarnings("unused")
public interface CartaRepository extends JpaRepository<Carta,Long> {

}
