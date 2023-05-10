package com.ngochai.bankapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ngochai.bankapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Loan.class);
        Loan loan1 = new Loan();
        loan1.setId(1L);
        Loan loan2 = new Loan();
        loan2.setId(loan1.getId());
        assertThat(loan1).isEqualTo(loan2);
        loan2.setId(2L);
        assertThat(loan1).isNotEqualTo(loan2);
        loan1.setId(null);
        assertThat(loan1).isNotEqualTo(loan2);
    }
}
