package br.com.magic.service.impl;

import br.com.magic.service.CartaService;
import br.com.magic.domain.Carta;
import br.com.magic.repository.CartaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Carta.
 */
@Service
@Transactional
public class CartaServiceImpl implements CartaService{

    private final Logger log = LoggerFactory.getLogger(CartaServiceImpl.class);
    
    @Inject
    private CartaRepository cartaRepository;
    
    /**
     * Save a carta.
     * 
     * @param carta the entity to save
     * @return the persisted entity
     */
    public Carta save(Carta carta) {
        log.debug("Request to save Carta : {}", carta);
        Carta result = cartaRepository.save(carta);
        return result;
    }

    /**
     *  Get all the cartas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Carta> findAll(Pageable pageable) {
        log.debug("Request to get all Cartas");
        Page<Carta> result = cartaRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one carta by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Carta findOne(Long id) {
        log.debug("Request to get Carta : {}", id);
        Carta carta = cartaRepository.findOne(id);
        return carta;
    }

    /**
     *  Delete the  carta by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Carta : {}", id);
        cartaRepository.delete(id);
    }
}
