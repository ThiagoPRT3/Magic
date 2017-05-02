package br.com.magic.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.magic.domain.CartaHabilidade;
import br.com.magic.repository.CartaHabilidadeRepository;
import br.com.magic.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CartaHabilidade.
 */
@RestController
@RequestMapping("/api")
public class CartaHabilidadeResource {

    private final Logger log = LoggerFactory.getLogger(CartaHabilidadeResource.class);
        
    @Inject
    private CartaHabilidadeRepository cartaHabilidadeRepository;
    
    /**
     * POST  /carta-habilidades : Create a new cartaHabilidade.
     *
     * @param cartaHabilidade the cartaHabilidade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cartaHabilidade, or with status 400 (Bad Request) if the cartaHabilidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/carta-habilidades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CartaHabilidade> createCartaHabilidade(@Valid @RequestBody CartaHabilidade cartaHabilidade) throws URISyntaxException {
        log.debug("REST request to save CartaHabilidade : {}", cartaHabilidade);
        if (cartaHabilidade.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cartaHabilidade", "idexists", "A new cartaHabilidade cannot already have an ID")).body(null);
        }
        CartaHabilidade result = cartaHabilidadeRepository.save(cartaHabilidade);
        return ResponseEntity.created(new URI("/api/carta-habilidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cartaHabilidade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carta-habilidades : Updates an existing cartaHabilidade.
     *
     * @param cartaHabilidade the cartaHabilidade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cartaHabilidade,
     * or with status 400 (Bad Request) if the cartaHabilidade is not valid,
     * or with status 500 (Internal Server Error) if the cartaHabilidade couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/carta-habilidades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CartaHabilidade> updateCartaHabilidade(@Valid @RequestBody CartaHabilidade cartaHabilidade) throws URISyntaxException {
        log.debug("REST request to update CartaHabilidade : {}", cartaHabilidade);
        if (cartaHabilidade.getId() == null) {
            return createCartaHabilidade(cartaHabilidade);
        }
        CartaHabilidade result = cartaHabilidadeRepository.save(cartaHabilidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cartaHabilidade", cartaHabilidade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carta-habilidades : get all the cartaHabilidades.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cartaHabilidades in body
     */
    @RequestMapping(value = "/carta-habilidades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CartaHabilidade> getAllCartaHabilidades() {
        log.debug("REST request to get all CartaHabilidades");
        List<CartaHabilidade> cartaHabilidades = cartaHabilidadeRepository.findAll();
        return cartaHabilidades;
    }

    /**
     * GET  /carta-habilidades/:id : get the "id" cartaHabilidade.
     *
     * @param id the id of the cartaHabilidade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cartaHabilidade, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/carta-habilidades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CartaHabilidade> getCartaHabilidade(@PathVariable Long id) {
        log.debug("REST request to get CartaHabilidade : {}", id);
        CartaHabilidade cartaHabilidade = cartaHabilidadeRepository.findOne(id);
        return Optional.ofNullable(cartaHabilidade)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /carta-habilidades/:id : delete the "id" cartaHabilidade.
     *
     * @param id the id of the cartaHabilidade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/carta-habilidades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCartaHabilidade(@PathVariable Long id) {
        log.debug("REST request to delete CartaHabilidade : {}", id);
        cartaHabilidadeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cartaHabilidade", id.toString())).build();
    }

}
