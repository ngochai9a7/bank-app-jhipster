package com.ngochai.bankapp.web.rest;

import com.ngochai.bankapp.domain.Accounts;
import com.ngochai.bankapp.repository.AccountsRepository;
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
 * REST controller for managing {@link com.ngochai.bankapp.domain.Accounts}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AccountsResource {

    private final Logger log = LoggerFactory.getLogger(AccountsResource.class);

    private static final String ENTITY_NAME = "accounts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountsRepository accountsRepository;

    public AccountsResource(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    /**
     * {@code POST  /accounts} : Create a new accounts.
     *
     * @param accounts the accounts to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accounts, or with status {@code 400 (Bad Request)} if the accounts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accounts")
    public ResponseEntity<Accounts> createAccounts(@RequestBody Accounts accounts) throws URISyntaxException {
        log.debug("REST request to save Accounts : {}", accounts);
        if (accounts.getId() != null) {
            throw new BadRequestAlertException("A new accounts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Accounts result = accountsRepository.save(accounts);
        return ResponseEntity
            .created(new URI("/api/accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accounts/:id} : Updates an existing accounts.
     *
     * @param id the id of the accounts to save.
     * @param accounts the accounts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accounts,
     * or with status {@code 400 (Bad Request)} if the accounts is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accounts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accounts/{id}")
    public ResponseEntity<Accounts> updateAccounts(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Accounts accounts
    ) throws URISyntaxException {
        log.debug("REST request to update Accounts : {}, {}", id, accounts);
        if (accounts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accounts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Accounts result = accountsRepository.save(accounts);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accounts.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /accounts/:id} : Partial updates given fields of an existing accounts, field will ignore if it is null
     *
     * @param id the id of the accounts to save.
     * @param accounts the accounts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accounts,
     * or with status {@code 400 (Bad Request)} if the accounts is not valid,
     * or with status {@code 404 (Not Found)} if the accounts is not found,
     * or with status {@code 500 (Internal Server Error)} if the accounts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/accounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Accounts> partialUpdateAccounts(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Accounts accounts
    ) throws URISyntaxException {
        log.debug("REST request to partial update Accounts partially : {}, {}", id, accounts);
        if (accounts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accounts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Accounts> result = accountsRepository
            .findById(accounts.getId())
            .map(existingAccounts -> {
                if (accounts.getOpeningDate() != null) {
                    existingAccounts.setOpeningDate(accounts.getOpeningDate());
                }
                if (accounts.getBalance() != null) {
                    existingAccounts.setBalance(accounts.getBalance());
                }

                return existingAccounts;
            })
            .map(accountsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accounts.getId().toString())
        );
    }

    /**
     * {@code GET  /accounts} : get all the accounts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accounts in body.
     */
    @GetMapping("/accounts")
    public ResponseEntity<List<Accounts>> getAllAccounts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Accounts");
        Page<Accounts> page;
        if (eagerload) {
            page = accountsRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = accountsRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accounts/:id} : get the "id" accounts.
     *
     * @param id the id of the accounts to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accounts, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Accounts> getAccounts(@PathVariable Long id) {
        log.debug("REST request to get Accounts : {}", id);
        Optional<Accounts> accounts = accountsRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(accounts);
    }

    /**
     * {@code DELETE  /accounts/:id} : delete the "id" accounts.
     *
     * @param id the id of the accounts to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteAccounts(@PathVariable Long id) {
        log.debug("REST request to delete Accounts : {}", id);
        accountsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
