package com.ngochai.bankapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ngochai.bankapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransactionTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionType.class);
        TransactionType transactionType1 = new TransactionType();
        transactionType1.setId(1L);
        TransactionType transactionType2 = new TransactionType();
        transactionType2.setId(transactionType1.getId());
        assertThat(transactionType1).isEqualTo(transactionType2);
        transactionType2.setId(2L);
        assertThat(transactionType1).isNotEqualTo(transactionType2);
        transactionType1.setId(null);
        assertThat(transactionType1).isNotEqualTo(transactionType2);
    }
}
