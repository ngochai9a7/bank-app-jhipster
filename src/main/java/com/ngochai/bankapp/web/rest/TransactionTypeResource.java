package com.ngochai.bankapp.web.rest;

import com.ngochai.bankapp.domain.TransactionType;
import com.ngochai.bankapp.repository.TransactionTypeRepository;
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
 * REST controller for managing {@link com.ngochai.bankapp.domain.TransactionType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransactionTypeResource {

    private final Logger log = LoggerFactory.getLogger(TransactionTypeResource.class);

    private static final String ENTITY_NAME = "transactionType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionTypeRepository transactionTypeRepository;

    public TransactionTypeResource(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    /**
     * {@code POST  /transaction-types} : Create a new transactionType.
     *
     * @param transactionType the transactionType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionType, or with status {@code 400 (Bad Request)} if the transactionType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-types")
    public ResponseEntity<TransactionType> createTransactionType(@RequestBody TransactionType transactionType) throws URISyntaxException {
        log.debug("REST request to save TransactionType : {}", transactionType);
        if (transactionType.getId() != null) {
            throw new BadRequestAlertException("A new transactionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionType result = transactionTypeRepository.save(transactionType);
        return ResponseEntity
            .created(new URI("/api/transaction-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-types/:id} : Updates an existing transactionType.
     *
     * @param id the id of the transactionType to save.
     * @param transactionType the transactionType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionType,
     * or with status {@code 400 (Bad Request)} if the transactionType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-types/{id}")
    public ResponseEntity<TransactionType> updateTransactionType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransactionType transactionType
    ) throws URISyntaxException {
        log.debug("REST request to update TransactionType : {}, {}", id, transactionType);
        if (transactionType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransactionType result = transactionTypeRepository.save(transactionType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transaction-types/:id} : Partial updates given fields of an existing transactionType, field will ignore if it is null
     *
     * @param id the id of the transactionType to save.
     * @param transactionType the transactionType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionType,
     * or with status {@code 400 (Bad Request)} if the transactionType is not valid,
     * or with status {@code 404 (Not Found)} if the transactionType is not found,
     * or with status {@code 500 (Internal Server Error)} if the transactionType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transaction-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransactionType> partialUpdateTransactionType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransactionType transactionType
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransactionType partially : {}, {}", id, transactionType);
        if (transactionType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransactionType> result = transactionTypeRepository
            .findById(transactionType.getId())
            .map(existingTransactionType -> {
                if (transactionType.getName() != null) {
                    existingTransactionType.setName(transactionType.getName());
                }

                return existingTransactionType;
            })
            .map(transactionTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionType.getId().toString())
        );
    }

    /**
     * {@code GET  /transaction-types} : get all the transactionTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionTypes in body.
     */
    @GetMapping("/transaction-types")
    public ResponseEntity<List<TransactionType>> getAllTransactionTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TransactionTypes");
        Page<TransactionType> page = transactionTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transaction-types/:id} : get the "id" transactionType.
     *
     * @param id the id of the transactionType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-types/{id}")
    public ResponseEntity<TransactionType> getTransactionType(@PathVariable Long id) {
        log.debug("REST request to get TransactionType : {}", id);
        Optional<TransactionType> transactionType = transactionTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transactionType);
    }

    /**
     * {@code DELETE  /transaction-types/:id} : delete the "id" transactionType.
     *
     * @param id the id of the transactionType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-types/{id}")
    public ResponseEntity<Void> deleteTransactionType(@PathVariable Long id) {
        log.debug("REST request to delete TransactionType : {}", id);
        transactionTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
