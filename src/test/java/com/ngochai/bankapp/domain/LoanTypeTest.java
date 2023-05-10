package com.ngochai.bankapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ngochai.bankapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanType.class);
        LoanType loanType1 = new LoanType();
        loanType1.setId(1L);
        LoanType loanType2 = new LoanType();
        loanType2.setId(loanType1.getId());
        assertThat(loanType1).isEqualTo(loanType2);
        loanType2.setId(2L);
        assertThat(loanType1).isNotEqualTo(loanType2);
        loanType1.setId(null);
        assertThat(loanType1).isNotEqualTo(loanType2);
    }
}
