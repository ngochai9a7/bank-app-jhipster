package com.ngochai.bankapp.web.rest;

import com.ngochai.bankapp.domain.Loan;
import com.ngochai.bankapp.repository.LoanRepository;
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
 * REST controller for managing {@link com.ngochai.bankapp.domain.Loan}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LoanResource {

    private final Logger log = LoggerFactory.getLogger(LoanResource.class);

    private static final String ENTITY_NAME = "loan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanRepository loanRepository;

    public LoanResource(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    /**
     * {@code POST  /loans} : Create a new loan.
     *
     * @param loan the loan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loan, or with status {@code 400 (Bad Request)} if the loan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loans")
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) throws URISyntaxException {
        log.debug("REST request to save Loan : {}", loan);
        if (loan.getId() != null) {
            throw new BadRequestAlertException("A new loan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Loan result = loanRepository.save(loan);
        return ResponseEntity
            .created(new URI("/api/loans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loans/:id} : Updates an existing loan.
     *
     * @param id the id of the loan to save.
     * @param loan the loan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loan,
     * or with status {@code 400 (Bad Request)} if the loan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loans/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable(value = "id", required = false) final Long id, @RequestBody Loan loan)
        throws URISyntaxException {
        log.debug("REST request to update Loan : {}, {}", id, loan);
        if (loan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Loan result = loanRepository.save(loan);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loan.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loans/:id} : Partial updates given fields of an existing loan, field will ignore if it is null
     *
     * @param id the id of the loan to save.
     * @param loan the loan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loan,
     * or with status {@code 400 (Bad Request)} if the loan is not valid,
     * or with status {@code 404 (Not Found)} if the loan is not found,
     * or with status {@code 500 (Internal Server Error)} if the loan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loans/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Loan> partialUpdateLoan(@PathVariable(value = "id", required = false) final Long id, @RequestBody Loan loan)
        throws URISyntaxException {
        log.debug("REST request to partial update Loan partially : {}, {}", id, loan);
        if (loan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Loan> result = loanRepository
            .findById(loan.getId())
            .map(existingLoan -> {
                if (loan.getAmount() != null) {
                    existingLoan.setAmount(loan.getAmount());
                }

                return existingLoan;
            })
            .map(loanRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loan.getId().toString())
        );
    }

    /**
     * {@code GET  /loans} : get all the loans.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loans in body.
     */
    @GetMapping("/loans")
    public ResponseEntity<List<Loan>> getAllLoans(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Loans");
        Page<Loan> page;
        if (eagerload) {
            page = loanRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = loanRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loans/:id} : get the "id" loan.
     *
     * @param id the id of the loan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loans/{id}")
    public ResponseEntity<Loan> getLoan(@PathVariable Long id) {
        log.debug("REST request to get Loan : {}", id);
        Optional<Loan> loan = loanRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(loan);
    }

    /**
     * {@code DELETE  /loans/:id} : delete the "id" loan.
     *
     * @param id the id of the loan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loans/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        log.debug("REST request to delete Loan : {}", id);
        loanRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
