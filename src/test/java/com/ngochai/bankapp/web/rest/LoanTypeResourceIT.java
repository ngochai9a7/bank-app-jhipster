package com.ngochai.bankapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ngochai.bankapp.IntegrationTest;
import com.ngochai.bankapp.domain.LoanType;
import com.ngochai.bankapp.repository.LoanTypeRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LoanTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoanTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/loan-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoanTypeRepository loanTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoanTypeMockMvc;

    private LoanType loanType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanType createEntity(EntityManager em) {
        LoanType loanType = new LoanType().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return loanType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanType createUpdatedEntity(EntityManager em) {
        LoanType loanType = new LoanType().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return loanType;
    }

    @BeforeEach
    public void initTest() {
        loanType = createEntity(em);
    }

    @Test
    @Transactional
    void createLoanType() throws Exception {
        int databaseSizeBeforeCreate = loanTypeRepository.findAll().size();
        // Create the LoanType
        restLoanTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanType)))
            .andExpect(status().isCreated());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LoanType testLoanType = loanTypeList.get(loanTypeList.size() - 1);
        assertThat(testLoanType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLoanType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createLoanTypeWithExistingId() throws Exception {
        // Create the LoanType with an existing ID
        loanType.setId(1L);

        int databaseSizeBeforeCreate = loanTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanType)))
            .andExpect(status().isBadRequest());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoanTypes() throws Exception {
        // Initialize the database
        loanTypeRepository.saveAndFlush(loanType);

        // Get all the loanTypeList
        restLoanTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getLoanType() throws Exception {
        // Initialize the database
        loanTypeRepository.saveAndFlush(loanType);

        // Get the loanType
        restLoanTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, loanType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loanType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingLoanType() throws Exception {
        // Get the loanType
        restLoanTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLoanType() throws Exception {
        // Initialize the database
        loanTypeRepository.saveAndFlush(loanType);

        int databaseSizeBeforeUpdate = loanTypeRepository.findAll().size();

        // Update the loanType
        LoanType updatedLoanType = loanTypeRepository.findById(loanType.getId()).get();
        // Disconnect from session so that the updates on updatedLoanType are not directly saved in db
        em.detach(updatedLoanType);
        updatedLoanType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restLoanTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLoanType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLoanType))
            )
            .andExpect(status().isOk());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeUpdate);
        LoanType testLoanType = loanTypeList.get(loanTypeList.size() - 1);
        assertThat(testLoanType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLoanType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingLoanType() throws Exception {
        int databaseSizeBeforeUpdate = loanTypeRepository.findAll().size();
        loanType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanType))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoanType() throws Exception {
        int databaseSizeBeforeUpdate = loanTypeRepository.findAll().size();
        loanType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanType))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoanType() throws Exception {
        int databaseSizeBeforeUpdate = loanTypeRepository.findAll().size();
        loanType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoanTypeWithPatch() throws Exception {
        // Initialize the database
        loanTypeRepository.saveAndFlush(loanType);

        int databaseSizeBeforeUpdate = loanTypeRepository.findAll().size();

        // Update the loanType using partial update
        LoanType partialUpdatedLoanType = new LoanType();
        partialUpdatedLoanType.setId(loanType.getId());

        partialUpdatedLoanType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restLoanTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanType))
            )
            .andExpect(status().isOk());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeUpdate);
        LoanType testLoanType = loanTypeList.get(loanTypeList.size() - 1);
        assertThat(testLoanType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLoanType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateLoanTypeWithPatch() throws Exception {
        // Initialize the database
        loanTypeRepository.saveAndFlush(loanType);

        int databaseSizeBeforeUpdate = loanTypeRepository.findAll().size();

        // Update the loanType using partial update
        LoanType partialUpdatedLoanType = new LoanType();
        partialUpdatedLoanType.setId(loanType.getId());

        partialUpdatedLoanType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restLoanTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanType))
            )
            .andExpect(status().isOk());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeUpdate);
        LoanType testLoanType = loanTypeList.get(loanTypeList.size() - 1);
        assertThat(testLoanType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLoanType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingLoanType() throws Exception {
        int databaseSizeBeforeUpdate = loanTypeRepository.findAll().size();
        loanType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loanType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanType))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoanType() throws Exception {
        int databaseSizeBeforeUpdate = loanTypeRepository.findAll().size();
        loanType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanType))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoanType() throws Exception {
        int databaseSizeBeforeUpdate = loanTypeRepository.findAll().size();
        loanType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(loanType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoanType() throws Exception {
        // Initialize the database
        loanTypeRepository.saveAndFlush(loanType);

        int databaseSizeBeforeDelete = loanTypeRepository.findAll().size();

        // Delete the loanType
        restLoanTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, loanType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
