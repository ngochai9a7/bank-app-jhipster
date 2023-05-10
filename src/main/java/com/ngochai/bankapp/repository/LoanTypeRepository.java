package com.ngochai.bankapp.repository;

import com.ngochai.bankapp.domain.LoanType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {}
