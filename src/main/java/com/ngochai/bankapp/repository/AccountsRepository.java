package com.ngochai.bankapp.repository;

import com.ngochai.bankapp.domain.Accounts;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Accounts entity.
 */
@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    default Optional<Accounts> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Accounts> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Accounts> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct accounts from Accounts accounts left join fetch accounts.customer left join fetch accounts.branch left join fetch accounts.type",
        countQuery = "select count(distinct accounts) from Accounts accounts"
    )
    Page<Accounts> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct accounts from Accounts accounts left join fetch accounts.customer left join fetch accounts.branch left join fetch accounts.type"
    )
    List<Accounts> findAllWithToOneRelationships();

    @Query(
        "select accounts from Accounts accounts left join fetch accounts.customer left join fetch accounts.branch left join fetch accounts.type where accounts.id =:id"
    )
    Optional<Accounts> findOneWithToOneRelationships(@Param("id") Long id);
}
