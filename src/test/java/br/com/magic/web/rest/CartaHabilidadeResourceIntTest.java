package br.com.magic.web.rest;

import br.com.magic.MagicApp;
import br.com.magic.domain.CartaHabilidade;
import br.com.magic.repository.CartaHabilidadeRepository;

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


/**
 * Test class for the CartaHabilidadeResource REST controller.
 *
 * @see CartaHabilidadeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MagicApp.class)
@WebAppConfiguration
@IntegrationTest
public class CartaHabilidadeResourceIntTest {

    private static final String DEFAULT_CARTA_NOME = "AAAAA";
    private static final String UPDATED_CARTA_NOME = "BBBBB";
    private static final String DEFAULT_HABILIDADE_NOME = "AAAAA";
    private static final String UPDATED_HABILIDADE_NOME = "BBBBB";

    @Inject
    private CartaHabilidadeRepository cartaHabilidadeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCartaHabilidadeMockMvc;

    private CartaHabilidade cartaHabilidade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CartaHabilidadeResource cartaHabilidadeResource = new CartaHabilidadeResource();
        ReflectionTestUtils.setField(cartaHabilidadeResource, "cartaHabilidadeRepository", cartaHabilidadeRepository);
        this.restCartaHabilidadeMockMvc = MockMvcBuilders.standaloneSetup(cartaHabilidadeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cartaHabilidade = new CartaHabilidade();
        cartaHabilidade.setCartaNome(DEFAULT_CARTA_NOME);
        cartaHabilidade.setHabilidadeNome(DEFAULT_HABILIDADE_NOME);
    }

    @Test
    @Transactional
    public void createCartaHabilidade() throws Exception {
        int databaseSizeBeforeCreate = cartaHabilidadeRepository.findAll().size();

        // Create the CartaHabilidade

        restCartaHabilidadeMockMvc.perform(post("/api/carta-habilidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartaHabilidade)))
                .andExpect(status().isCreated());

        // Validate the CartaHabilidade in the database
        List<CartaHabilidade> cartaHabilidades = cartaHabilidadeRepository.findAll();
        assertThat(cartaHabilidades).hasSize(databaseSizeBeforeCreate + 1);
        CartaHabilidade testCartaHabilidade = cartaHabilidades.get(cartaHabilidades.size() - 1);
        assertThat(testCartaHabilidade.getCartaNome()).isEqualTo(DEFAULT_CARTA_NOME);
        assertThat(testCartaHabilidade.getHabilidadeNome()).isEqualTo(DEFAULT_HABILIDADE_NOME);
    }

    @Test
    @Transactional
    public void checkCartaNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaHabilidadeRepository.findAll().size();
        // set the field null
        cartaHabilidade.setCartaNome(null);

        // Create the CartaHabilidade, which fails.

        restCartaHabilidadeMockMvc.perform(post("/api/carta-habilidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartaHabilidade)))
                .andExpect(status().isBadRequest());

        List<CartaHabilidade> cartaHabilidades = cartaHabilidadeRepository.findAll();
        assertThat(cartaHabilidades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHabilidadeNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaHabilidadeRepository.findAll().size();
        // set the field null
        cartaHabilidade.setHabilidadeNome(null);

        // Create the CartaHabilidade, which fails.

        restCartaHabilidadeMockMvc.perform(post("/api/carta-habilidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartaHabilidade)))
                .andExpect(status().isBadRequest());

        List<CartaHabilidade> cartaHabilidades = cartaHabilidadeRepository.findAll();
        assertThat(cartaHabilidades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCartaHabilidades() throws Exception {
        // Initialize the database
        cartaHabilidadeRepository.saveAndFlush(cartaHabilidade);

        // Get all the cartaHabilidades
        restCartaHabilidadeMockMvc.perform(get("/api/carta-habilidades?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cartaHabilidade.getId().intValue())))
                .andExpect(jsonPath("$.[*].cartaNome").value(hasItem(DEFAULT_CARTA_NOME.toString())))
                .andExpect(jsonPath("$.[*].habilidadeNome").value(hasItem(DEFAULT_HABILIDADE_NOME.toString())));
    }

    @Test
    @Transactional
    public void getCartaHabilidade() throws Exception {
        // Initialize the database
        cartaHabilidadeRepository.saveAndFlush(cartaHabilidade);

        // Get the cartaHabilidade
        restCartaHabilidadeMockMvc.perform(get("/api/carta-habilidades/{id}", cartaHabilidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cartaHabilidade.getId().intValue()))
            .andExpect(jsonPath("$.cartaNome").value(DEFAULT_CARTA_NOME.toString()))
            .andExpect(jsonPath("$.habilidadeNome").value(DEFAULT_HABILIDADE_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCartaHabilidade() throws Exception {
        // Get the cartaHabilidade
        restCartaHabilidadeMockMvc.perform(get("/api/carta-habilidades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCartaHabilidade() throws Exception {
        // Initialize the database
        cartaHabilidadeRepository.saveAndFlush(cartaHabilidade);
        int databaseSizeBeforeUpdate = cartaHabilidadeRepository.findAll().size();

        // Update the cartaHabilidade
        CartaHabilidade updatedCartaHabilidade = new CartaHabilidade();
        updatedCartaHabilidade.setId(cartaHabilidade.getId());
        updatedCartaHabilidade.setCartaNome(UPDATED_CARTA_NOME);
        updatedCartaHabilidade.setHabilidadeNome(UPDATED_HABILIDADE_NOME);

        restCartaHabilidadeMockMvc.perform(put("/api/carta-habilidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCartaHabilidade)))
                .andExpect(status().isOk());

        // Validate the CartaHabilidade in the database
        List<CartaHabilidade> cartaHabilidades = cartaHabilidadeRepository.findAll();
        assertThat(cartaHabilidades).hasSize(databaseSizeBeforeUpdate);
        CartaHabilidade testCartaHabilidade = cartaHabilidades.get(cartaHabilidades.size() - 1);
        assertThat(testCartaHabilidade.getCartaNome()).isEqualTo(UPDATED_CARTA_NOME);
        assertThat(testCartaHabilidade.getHabilidadeNome()).isEqualTo(UPDATED_HABILIDADE_NOME);
    }

    @Test
    @Transactional
    public void deleteCartaHabilidade() throws Exception {
        // Initialize the database
        cartaHabilidadeRepository.saveAndFlush(cartaHabilidade);
        int databaseSizeBeforeDelete = cartaHabilidadeRepository.findAll().size();

        // Get the cartaHabilidade
        restCartaHabilidadeMockMvc.perform(delete("/api/carta-habilidades/{id}", cartaHabilidade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CartaHabilidade> cartaHabilidades = cartaHabilidadeRepository.findAll();
        assertThat(cartaHabilidades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
