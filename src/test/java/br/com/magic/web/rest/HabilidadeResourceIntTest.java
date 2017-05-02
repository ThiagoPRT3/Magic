package br.com.magic.web.rest;

import br.com.magic.MagicApp;
import br.com.magic.domain.Habilidade;
import br.com.magic.repository.HabilidadeRepository;

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
 * Test class for the HabilidadeResource REST controller.
 *
 * @see HabilidadeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MagicApp.class)
@WebAppConfiguration
@IntegrationTest
public class HabilidadeResourceIntTest {

    private static final String DEFAULT_HABILIDADE_NOME_BR = "AAAAA";
    private static final String UPDATED_HABILIDADE_NOME_BR = "BBBBB";
    private static final String DEFAULT_HABILIDADE_NAME_ING = "AAAAA";
    private static final String UPDATED_HABILIDADE_NAME_ING = "BBBBB";
    private static final String DEFAULT_DESCRICAO = "AAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBB";

    @Inject
    private HabilidadeRepository habilidadeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHabilidadeMockMvc;

    private Habilidade habilidade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HabilidadeResource habilidadeResource = new HabilidadeResource();
        ReflectionTestUtils.setField(habilidadeResource, "habilidadeRepository", habilidadeRepository);
        this.restHabilidadeMockMvc = MockMvcBuilders.standaloneSetup(habilidadeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        habilidade = new Habilidade();
        habilidade.setHabilidadeNomeBr(DEFAULT_HABILIDADE_NOME_BR);
        habilidade.setHabilidadeNameIng(DEFAULT_HABILIDADE_NAME_ING);
        habilidade.setDescricao(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createHabilidade() throws Exception {
        int databaseSizeBeforeCreate = habilidadeRepository.findAll().size();

        // Create the Habilidade

        restHabilidadeMockMvc.perform(post("/api/habilidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(habilidade)))
                .andExpect(status().isCreated());

        // Validate the Habilidade in the database
        List<Habilidade> habilidades = habilidadeRepository.findAll();
        assertThat(habilidades).hasSize(databaseSizeBeforeCreate + 1);
        Habilidade testHabilidade = habilidades.get(habilidades.size() - 1);
        assertThat(testHabilidade.getHabilidadeNomeBr()).isEqualTo(DEFAULT_HABILIDADE_NOME_BR);
        assertThat(testHabilidade.getHabilidadeNameIng()).isEqualTo(DEFAULT_HABILIDADE_NAME_ING);
        assertThat(testHabilidade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void checkHabilidadeNomeBrIsRequired() throws Exception {
        int databaseSizeBeforeTest = habilidadeRepository.findAll().size();
        // set the field null
        habilidade.setHabilidadeNomeBr(null);

        // Create the Habilidade, which fails.

        restHabilidadeMockMvc.perform(post("/api/habilidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(habilidade)))
                .andExpect(status().isBadRequest());

        List<Habilidade> habilidades = habilidadeRepository.findAll();
        assertThat(habilidades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHabilidadeNameIngIsRequired() throws Exception {
        int databaseSizeBeforeTest = habilidadeRepository.findAll().size();
        // set the field null
        habilidade.setHabilidadeNameIng(null);

        // Create the Habilidade, which fails.

        restHabilidadeMockMvc.perform(post("/api/habilidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(habilidade)))
                .andExpect(status().isBadRequest());

        List<Habilidade> habilidades = habilidadeRepository.findAll();
        assertThat(habilidades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = habilidadeRepository.findAll().size();
        // set the field null
        habilidade.setDescricao(null);

        // Create the Habilidade, which fails.

        restHabilidadeMockMvc.perform(post("/api/habilidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(habilidade)))
                .andExpect(status().isBadRequest());

        List<Habilidade> habilidades = habilidadeRepository.findAll();
        assertThat(habilidades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHabilidades() throws Exception {
        // Initialize the database
        habilidadeRepository.saveAndFlush(habilidade);

        // Get all the habilidades
        restHabilidadeMockMvc.perform(get("/api/habilidades?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(habilidade.getId().intValue())))
                .andExpect(jsonPath("$.[*].habilidadeNomeBr").value(hasItem(DEFAULT_HABILIDADE_NOME_BR.toString())))
                .andExpect(jsonPath("$.[*].habilidadeNameIng").value(hasItem(DEFAULT_HABILIDADE_NAME_ING.toString())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getHabilidade() throws Exception {
        // Initialize the database
        habilidadeRepository.saveAndFlush(habilidade);

        // Get the habilidade
        restHabilidadeMockMvc.perform(get("/api/habilidades/{id}", habilidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(habilidade.getId().intValue()))
            .andExpect(jsonPath("$.habilidadeNomeBr").value(DEFAULT_HABILIDADE_NOME_BR.toString()))
            .andExpect(jsonPath("$.habilidadeNameIng").value(DEFAULT_HABILIDADE_NAME_ING.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHabilidade() throws Exception {
        // Get the habilidade
        restHabilidadeMockMvc.perform(get("/api/habilidades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHabilidade() throws Exception {
        // Initialize the database
        habilidadeRepository.saveAndFlush(habilidade);
        int databaseSizeBeforeUpdate = habilidadeRepository.findAll().size();

        // Update the habilidade
        Habilidade updatedHabilidade = new Habilidade();
        updatedHabilidade.setId(habilidade.getId());
        updatedHabilidade.setHabilidadeNomeBr(UPDATED_HABILIDADE_NOME_BR);
        updatedHabilidade.setHabilidadeNameIng(UPDATED_HABILIDADE_NAME_ING);
        updatedHabilidade.setDescricao(UPDATED_DESCRICAO);

        restHabilidadeMockMvc.perform(put("/api/habilidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedHabilidade)))
                .andExpect(status().isOk());

        // Validate the Habilidade in the database
        List<Habilidade> habilidades = habilidadeRepository.findAll();
        assertThat(habilidades).hasSize(databaseSizeBeforeUpdate);
        Habilidade testHabilidade = habilidades.get(habilidades.size() - 1);
        assertThat(testHabilidade.getHabilidadeNomeBr()).isEqualTo(UPDATED_HABILIDADE_NOME_BR);
        assertThat(testHabilidade.getHabilidadeNameIng()).isEqualTo(UPDATED_HABILIDADE_NAME_ING);
        assertThat(testHabilidade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void deleteHabilidade() throws Exception {
        // Initialize the database
        habilidadeRepository.saveAndFlush(habilidade);
        int databaseSizeBeforeDelete = habilidadeRepository.findAll().size();

        // Get the habilidade
        restHabilidadeMockMvc.perform(delete("/api/habilidades/{id}", habilidade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Habilidade> habilidades = habilidadeRepository.findAll();
        assertThat(habilidades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
