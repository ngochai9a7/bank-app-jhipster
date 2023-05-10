package com.ngochai.bankapp.repository;

import com.ngochai.bankapp.domain.Transaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Transaction entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    default Optional<Transaction> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Transaction> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Transaction> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct transaction from Transaction transaction left join fetch transaction.type",
        countQuery = "select count(distinct transaction) from Transaction transaction"
    )
    Page<Transaction> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct transaction from Transaction transaction left join fetch transaction.type")
    List<Transaction> findAllWithToOneRelationships();

    @Query("select transaction from Transaction transaction left join fetch transaction.type where transaction.id =:id")
    Optional<Transaction> findOneWithToOneRelationships(@Param("id") Long id);
}
