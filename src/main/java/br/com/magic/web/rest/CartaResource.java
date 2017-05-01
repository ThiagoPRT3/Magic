package br.com.magic.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.magic.domain.Carta;
import br.com.magic.service.CartaService;
import br.com.magic.web.rest.util.HeaderUtil;
import br.com.magic.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing Carta.
 */
@RestController
@RequestMapping("/api")
public class CartaResource {

    private final Logger log = LoggerFactory.getLogger(CartaResource.class);
        
    @Inject
    private CartaService cartaService;
    
    /**
     * POST  /cartas : Create a new carta.
     *
     * @param carta the carta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carta, or with status 400 (Bad Request) if the carta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cartas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carta> createCarta(@Valid @RequestBody Carta carta) throws URISyntaxException {
        log.debug("REST request to save Carta : {}", carta);
        if (carta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("carta", "idexists", "A new carta cannot already have an ID")).body(null);
        }
        Carta result = cartaService.save(carta);
        return ResponseEntity.created(new URI("/api/cartas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("carta", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cartas : Updates an existing carta.
     *
     * @param carta the carta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carta,
     * or with status 400 (Bad Request) if the carta is not valid,
     * or with status 500 (Internal Server Error) if the carta couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cartas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carta> updateCarta(@Valid @RequestBody Carta carta) throws URISyntaxException {
        log.debug("REST request to update Carta : {}", carta);
        if (carta.getId() == null) {
            return createCarta(carta);
        }
        Carta result = cartaService.save(carta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("carta", carta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cartas : get all the cartas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cartas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cartas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Carta>> getAllCartas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cartas");
        Page<Carta> page = cartaService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cartas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cartas/:id : get the "id" carta.
     *
     * @param id the id of the carta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carta, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cartas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carta> getCarta(@PathVariable Long id) {
        log.debug("REST request to get Carta : {}", id);
        Carta carta = cartaService.findOne(id);
        return Optional.ofNullable(carta)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cartas/:id : delete the "id" carta.
     *
     * @param id the id of the carta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cartas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCarta(@PathVariable Long id) {
        log.debug("REST request to delete Carta : {}", id);
        cartaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carta", id.toString())).build();
    }

}
