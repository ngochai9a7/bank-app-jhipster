package com.ngochai.bankapp.web.rest;

import com.ngochai.bankapp.domain.LoanType;
import com.ngochai.bankapp.repository.LoanTypeRepository;
import com.ngochai.bankapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ngochai.bankapp.domain.LoanType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LoanTypeResource {

    private final Logger log = LoggerFactory.getLogger(LoanTypeResource.class);

    private static final String ENTITY_NAME = "loanType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanTypeRepository loanTypeRepository;

    public LoanTypeResource(LoanTypeRepository loanTypeRepository) {
        this.loanTypeRepository = loanTypeRepository;
    }

    /**
     * {@code POST  /loan-types} : Create a new loanType.
     *
     * @param loanType the loanType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanType, or with status {@code 400 (Bad Request)} if the loanType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-types")
    public ResponseEntity<LoanType> createLoanType(@RequestBody LoanType loanType) throws URISyntaxException {
        log.debug("REST request to save LoanType : {}", loanType);
        if (loanType.getId() != null) {
            throw new BadRequestAlertException("A new loanType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanType result = loanTypeRepository.save(loanType);
        return ResponseEntity
            .created(new URI("/api/loan-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-types/:id} : Updates an existing loanType.
     *
     * @param id the id of the loanType to save.
     * @param loanType the loanType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanType,
     * or with status {@code 400 (Bad Request)} if the loanType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-types/{id}")
    public ResponseEntity<LoanType> updateLoanType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanType loanType
    ) throws URISyntaxException {
        log.debug("REST request to update LoanType : {}, {}", id, loanType);
        if (loanType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanType result = loanTypeRepository.save(loanType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loan-types/:id} : Partial updates given fields of an existing loanType, field will ignore if it is null
     *
     * @param id the id of the loanType to save.
     * @param loanType the loanType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanType,
     * or with status {@code 400 (Bad Request)} if the loanType is not valid,
     * or with status {@code 404 (Not Found)} if the loanType is not found,
     * or with status {@code 500 (Internal Server Error)} if the loanType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loan-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoanType> partialUpdateLoanType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanType loanType
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoanType partially : {}, {}", id, loanType);
        if (loanType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoanType> result = loanTypeRepository
            .findById(loanType.getId())
            .map(existingLoanType -> {
                if (loanType.getName() != null) {
                    existingLoanType.setName(loanType.getName());
                }
                if (loanType.getDescription() != null) {
                    existingLoanType.setDescription(loanType.getDescription());
                }

                return existingLoanType;
            })
            .map(loanTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanType.getId().toString())
        );
    }

    /**
     * {@code GET  /loan-types} : get all the loanTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanTypes in body.
     */
    @GetMapping("/loan-types")
    public ResponseEntity<List<LoanType>> getAllLoanTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LoanTypes");
        Page<LoanType> page = loanTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loan-types/:id} : get the "id" loanType.
     *
     * @param id the id of the loanType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-types/{id}")
    public ResponseEntity<LoanType> getLoanType(@PathVariable Long id) {
        log.debug("REST request to get LoanType : {}", id);
        Optional<LoanType> loanType = loanTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(loanType);
    }

    /**
     * {@code DELETE  /loan-types/:id} : delete the "id" loanType.
     *
     * @param id the id of the loanType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-types/{id}")
    public ResponseEntity<Void> deleteLoanType(@PathVariable Long id) {
        log.debug("REST request to delete LoanType : {}", id);
        loanTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
