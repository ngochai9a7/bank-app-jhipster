package com.ngochai.bankapp.web.rest;

import com.ngochai.bankapp.domain.Branch;
import com.ngochai.bankapp.repository.BranchRepository;
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
 * REST controller for managing {@link com.ngochai.bankapp.domain.Branch}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BranchResource {

    private final Logger log = LoggerFactory.getLogger(BranchResource.class);

    private static final String ENTITY_NAME = "branch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BranchRepository branchRepository;

    public BranchResource(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    /**
     * {@code POST  /branches} : Create a new branch.
     *
     * @param branch the branch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new branch, or with status {@code 400 (Bad Request)} if the branch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/branches")
    public ResponseEntity<Branch> createBranch(@RequestBody Branch branch) throws URISyntaxException {
        log.debug("REST request to save Branch : {}", branch);
        if (branch.getId() != null) {
            throw new BadRequestAlertException("A new branch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Branch result = branchRepository.save(branch);
        return ResponseEntity
            .created(new URI("/api/branches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /branches/:id} : Updates an existing branch.
     *
     * @param id the id of the branch to save.
     * @param branch the branch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated branch,
     * or with status {@code 400 (Bad Request)} if the branch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the branch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/branches/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable(value = "id", required = false) final Long id, @RequestBody Branch branch)
        throws URISyntaxException {
        log.debug("REST request to update Branch : {}, {}", id, branch);
        if (branch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, branch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!branchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Branch result = branchRepository.save(branch);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, branch.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /branches/:id} : Partial updates given fields of an existing branch, field will ignore if it is null
     *
     * @param id the id of the branch to save.
     * @param branch the branch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated branch,
     * or with status {@code 400 (Bad Request)} if the branch is not valid,
     * or with status {@code 404 (Not Found)} if the branch is not found,
     * or with status {@code 500 (Internal Server Error)} if the branch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/branches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Branch> partialUpdateBranch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Branch branch
    ) throws URISyntaxException {
        log.debug("REST request to partial update Branch partially : {}, {}", id, branch);
        if (branch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, branch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!branchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Branch> result = branchRepository
            .findById(branch.getId())
            .map(existingBranch -> {
                if (branch.getName() != null) {
                    existingBranch.setName(branch.getName());
                }
                if (branch.getAddress() != null) {
                    existingBranch.setAddress(branch.getAddress());
                }
                if (branch.getPhone() != null) {
                    existingBranch.setPhone(branch.getPhone());
                }

                return existingBranch;
            })
            .map(branchRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, branch.getId().toString())
        );
    }

    /**
     * {@code GET  /branches} : get all the branches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of branches in body.
     */
    @GetMapping("/branches")
    public ResponseEntity<List<Branch>> getAllBranches(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Branches");
        Page<Branch> page = branchRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /branches/:id} : get the "id" branch.
     *
     * @param id the id of the branch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the branch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/branches/{id}")
    public ResponseEntity<Branch> getBranch(@PathVariable Long id) {
        log.debug("REST request to get Branch : {}", id);
        Optional<Branch> branch = branchRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(branch);
    }

    /**
     * {@code DELETE  /branches/:id} : delete the "id" branch.
     *
     * @param id the id of the branch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/branches/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        log.debug("REST request to delete Branch : {}", id);
        branchRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
