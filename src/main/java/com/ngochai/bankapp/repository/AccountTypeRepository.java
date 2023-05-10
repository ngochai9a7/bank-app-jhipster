package com.ngochai.bankapp.repository;

import com.ngochai.bankapp.domain.AccountType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccountType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {}
