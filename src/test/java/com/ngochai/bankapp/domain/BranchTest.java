package com.ngochai.bankapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ngochai.bankapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BranchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Branch.class);
        Branch branch1 = new Branch();
        branch1.setId(1L);
        Branch branch2 = new Branch();
        branch2.setId(branch1.getId());
        assertThat(branch1).isEqualTo(branch2);
        branch2.setId(2L);
        assertThat(branch1).isNotEqualTo(branch2);
        branch1.setId(null);
        assertThat(branch1).isNotEqualTo(branch2);
    }
}
