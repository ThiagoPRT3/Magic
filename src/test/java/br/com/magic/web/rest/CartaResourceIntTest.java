package br.com.magic.web.rest;

import br.com.magic.MagicApp;
import br.com.magic.domain.Carta;
import br.com.magic.repository.CartaRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.magic.domain.enumeration.Raridade;

/**
 * Test class for the CartaResource REST controller.
 *
 * @see CartaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MagicApp.class)
@WebAppConfiguration
@IntegrationTest
public class CartaResourceIntTest {

    private static final String DEFAULT_CARTA_NOME_BR = "AAAAA";
    private static final String UPDATED_CARTA_NOME_BR = "BBBBB";
    private static final String DEFAULT_CARTA_NAME_ING = "AAAAA";
    private static final String UPDATED_CARTA_NAME_ING = "BBBBB";
    private static final String DEFAULT_EDICAO = "AAAAA";
    private static final String UPDATED_EDICAO = "BBBBB";
    private static final String DEFAULT_TIPO = "AAAAA";
    private static final String UPDATED_TIPO = "BBBBB";

    private static final Raridade DEFAULT_RARIDADE = Raridade.COMUM;
    private static final Raridade UPDATED_RARIDADE = Raridade.INCOMUM;
    private static final String DEFAULT_DESCRICAO = "AAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBB";
    private static final String DEFAULT_ATAQUE = "AAAAA";
    private static final String UPDATED_ATAQUE = "BBBBB";
    private static final String DEFAULT_DEFESA = "AAAAA";
    private static final String UPDATED_DEFESA = "BBBBB";

    @Inject
    private CartaRepository cartaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCartaMockMvc;

    private Carta carta;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CartaResource cartaResource = new CartaResource();
        ReflectionTestUtils.setField(cartaResource, "cartaRepository", cartaRepository);
        this.restCartaMockMvc = MockMvcBuilders.standaloneSetup(cartaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        carta = new Carta();
        carta.setCartaNomeBr(DEFAULT_CARTA_NOME_BR);
        carta.setCartaNameIng(DEFAULT_CARTA_NAME_ING);
        carta.setEdicao(DEFAULT_EDICAO);
        carta.setTipo(DEFAULT_TIPO);
        carta.setRaridade(DEFAULT_RARIDADE);
        carta.setDescricao(DEFAULT_DESCRICAO);
        carta.setAtaque(DEFAULT_ATAQUE);
        carta.setDefesa(DEFAULT_DEFESA);
    }

    @Test
    @Transactional
    public void createCarta() throws Exception {
        int databaseSizeBeforeCreate = cartaRepository.findAll().size();

        // Create the Carta

        restCartaMockMvc.perform(post("/api/cartas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carta)))
                .andExpect(status().isCreated());

        // Validate the Carta in the database
        List<Carta> cartas = cartaRepository.findAll();
        assertThat(cartas).hasSize(databaseSizeBeforeCreate + 1);
        Carta testCarta = cartas.get(cartas.size() - 1);
        assertThat(testCarta.getCartaNomeBr()).isEqualTo(DEFAULT_CARTA_NOME_BR);
        assertThat(testCarta.getCartaNameIng()).isEqualTo(DEFAULT_CARTA_NAME_ING);
        assertThat(testCarta.getEdicao()).isEqualTo(DEFAULT_EDICAO);
        assertThat(testCarta.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testCarta.getRaridade()).isEqualTo(DEFAULT_RARIDADE);
        assertThat(testCarta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCarta.getAtaque()).isEqualTo(DEFAULT_ATAQUE);
        assertThat(testCarta.getDefesa()).isEqualTo(DEFAULT_DEFESA);
    }

    @Test
    @Transactional
    public void checkCartaNomeBrIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaRepository.findAll().size();
        // set the field null
        carta.setCartaNomeBr(null);

        // Create the Carta, which fails.

        restCartaMockMvc.perform(post("/api/cartas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carta)))
                .andExpect(status().isBadRequest());

        List<Carta> cartas = cartaRepository.findAll();
        assertThat(cartas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCartaNameIngIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaRepository.findAll().size();
        // set the field null
        carta.setCartaNameIng(null);

        // Create the Carta, which fails.

        restCartaMockMvc.perform(post("/api/cartas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carta)))
                .andExpect(status().isBadRequest());

        List<Carta> cartas = cartaRepository.findAll();
        assertThat(cartas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEdicaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaRepository.findAll().size();
        // set the field null
        carta.setEdicao(null);

        // Create the Carta, which fails.

        restCartaMockMvc.perform(post("/api/cartas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carta)))
                .andExpect(status().isBadRequest());

        List<Carta> cartas = cartaRepository.findAll();
        assertThat(cartas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaRepository.findAll().size();
        // set the field null
        carta.setTipo(null);

        // Create the Carta, which fails.

        restCartaMockMvc.perform(post("/api/cartas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carta)))
                .andExpect(status().isBadRequest());

        List<Carta> cartas = cartaRepository.findAll();
        assertThat(cartas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRaridadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaRepository.findAll().size();
        // set the field null
        carta.setRaridade(null);

        // Create the Carta, which fails.

        restCartaMockMvc.perform(post("/api/cartas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carta)))
                .andExpect(status().isBadRequest());

        List<Carta> cartas = cartaRepository.findAll();
        assertThat(cartas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCartas() throws Exception {
        // Initialize the database
        cartaRepository.saveAndFlush(carta);

        // Get all the cartas
        restCartaMockMvc.perform(get("/api/cartas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(carta.getId().intValue())))
                .andExpect(jsonPath("$.[*].cartaNomeBr").value(hasItem(DEFAULT_CARTA_NOME_BR.toString())))
                .andExpect(jsonPath("$.[*].cartaNameIng").value(hasItem(DEFAULT_CARTA_NAME_ING.toString())))
                .andExpect(jsonPath("$.[*].edicao").value(hasItem(DEFAULT_EDICAO.toString())))
                .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
                .andExpect(jsonPath("$.[*].raridade").value(hasItem(DEFAULT_RARIDADE.toString())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
                .andExpect(jsonPath("$.[*].ataque").value(hasItem(DEFAULT_ATAQUE.toString())))
                .andExpect(jsonPath("$.[*].defesa").value(hasItem(DEFAULT_DEFESA.toString())));
    }

    @Test
    @Transactional
    public void getCarta() throws Exception {
        // Initialize the database
        cartaRepository.saveAndFlush(carta);

        // Get the carta
        restCartaMockMvc.perform(get("/api/cartas/{id}", carta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(carta.getId().intValue()))
            .andExpect(jsonPath("$.cartaNomeBr").value(DEFAULT_CARTA_NOME_BR.toString()))
            .andExpect(jsonPath("$.cartaNameIng").value(DEFAULT_CARTA_NAME_ING.toString()))
            .andExpect(jsonPath("$.edicao").value(DEFAULT_EDICAO.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.raridade").value(DEFAULT_RARIDADE.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.ataque").value(DEFAULT_ATAQUE.toString()))
            .andExpect(jsonPath("$.defesa").value(DEFAULT_DEFESA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarta() throws Exception {
        // Get the carta
        restCartaMockMvc.perform(get("/api/cartas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarta() throws Exception {
        // Initialize the database
        cartaRepository.saveAndFlush(carta);
        int databaseSizeBeforeUpdate = cartaRepository.findAll().size();

        // Update the carta
        Carta updatedCarta = new Carta();
        updatedCarta.setId(carta.getId());
        updatedCarta.setCartaNomeBr(UPDATED_CARTA_NOME_BR);
        updatedCarta.setCartaNameIng(UPDATED_CARTA_NAME_ING);
        updatedCarta.setEdicao(UPDATED_EDICAO);
        updatedCarta.setTipo(UPDATED_TIPO);
        updatedCarta.setRaridade(UPDATED_RARIDADE);
        updatedCarta.setDescricao(UPDATED_DESCRICAO);
        updatedCarta.setAtaque(UPDATED_ATAQUE);
        updatedCarta.setDefesa(UPDATED_DEFESA);

        restCartaMockMvc.perform(put("/api/cartas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCarta)))
                .andExpect(status().isOk());

        // Validate the Carta in the database
        List<Carta> cartas = cartaRepository.findAll();
        assertThat(cartas).hasSize(databaseSizeBeforeUpdate);
        Carta testCarta = cartas.get(cartas.size() - 1);
        assertThat(testCarta.getCartaNomeBr()).isEqualTo(UPDATED_CARTA_NOME_BR);
        assertThat(testCarta.getCartaNameIng()).isEqualTo(UPDATED_CARTA_NAME_ING);
        assertThat(testCarta.getEdicao()).isEqualTo(UPDATED_EDICAO);
        assertThat(testCarta.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testCarta.getRaridade()).isEqualTo(UPDATED_RARIDADE);
        assertThat(testCarta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCarta.getAtaque()).isEqualTo(UPDATED_ATAQUE);
        assertThat(testCarta.getDefesa()).isEqualTo(UPDATED_DEFESA);
    }

    @Test
    @Transactional
    public void deleteCarta() throws Exception {
        // Initialize the database
        cartaRepository.saveAndFlush(carta);
        int databaseSizeBeforeDelete = cartaRepository.findAll().size();

        // Get the carta
        restCartaMockMvc.perform(delete("/api/cartas/{id}", carta.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Carta> cartas = cartaRepository.findAll();
        assertThat(cartas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
