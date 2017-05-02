package br.com.magic.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.magic.domain.Habilidade;
import br.com.magic.repository.HabilidadeRepository;
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
 * REST controller for managing Habilidade.
 */
@RestController
@RequestMapping("/api")
public class HabilidadeResource {

    private final Logger log = LoggerFactory.getLogger(HabilidadeResource.class);
        
    @Inject
    private HabilidadeRepository habilidadeRepository;
    
    /**
     * POST  /habilidades : Create a new habilidade.
     *
     * @param habilidade the habilidade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new habilidade, or with status 400 (Bad Request) if the habilidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/habilidades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Habilidade> createHabilidade(@Valid @RequestBody Habilidade habilidade) throws URISyntaxException {
        log.debug("REST request to save Habilidade : {}", habilidade);
        if (habilidade.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("habilidade", "idexists", "A new habilidade cannot already have an ID")).body(null);
        }
        Habilidade result = habilidadeRepository.save(habilidade);
        return ResponseEntity.created(new URI("/api/habilidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("habilidade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /habilidades : Updates an existing habilidade.
     *
     * @param habilidade the habilidade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated habilidade,
     * or with status 400 (Bad Request) if the habilidade is not valid,
     * or with status 500 (Internal Server Error) if the habilidade couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/habilidades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Habilidade> updateHabilidade(@Valid @RequestBody Habilidade habilidade) throws URISyntaxException {
        log.debug("REST request to update Habilidade : {}", habilidade);
        if (habilidade.getId() == null) {
            return createHabilidade(habilidade);
        }
        Habilidade result = habilidadeRepository.save(habilidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("habilidade", habilidade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /habilidades : get all the habilidades.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of habilidades in body
     */
    @RequestMapping(value = "/habilidades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Habilidade> getAllHabilidades() {
        log.debug("REST request to get all Habilidades");
        List<Habilidade> habilidades = habilidadeRepository.findAll();
        return habilidades;
    }

    /**
     * GET  /habilidades/:id : get the "id" habilidade.
     *
     * @param id the id of the habilidade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the habilidade, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/habilidades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Habilidade> getHabilidade(@PathVariable Long id) {
        log.debug("REST request to get Habilidade : {}", id);
        Habilidade habilidade = habilidadeRepository.findOne(id);
        return Optional.ofNullable(habilidade)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /habilidades/:id : delete the "id" habilidade.
     *
     * @param id the id of the habilidade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/habilidades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHabilidade(@PathVariable Long id) {
        log.debug("REST request to delete Habilidade : {}", id);
        habilidadeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("habilidade", id.toString())).build();
    }

}
