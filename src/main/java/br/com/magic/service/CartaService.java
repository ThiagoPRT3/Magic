package br.com.magic.service;

import br.com.magic.domain.Carta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Carta.
 */
public interface CartaService {

    /**
     * Save a carta.
     * 
     * @param carta the entity to save
     * @return the persisted entity
     */
    Carta save(Carta carta);

    /**
     *  Get all the cartas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Carta> findAll(Pageable pageable);

    /**
     *  Get the "id" carta.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Carta findOne(Long id);

    /**
     *  Delete the "id" carta.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
