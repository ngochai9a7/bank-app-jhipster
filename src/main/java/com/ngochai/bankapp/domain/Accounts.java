package com.ngochai.bankapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Accounts.
 */
@Entity
@Table(name = "accounts")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "opening_date")
    private LocalDate openingDate;

    @Column(name = "balance", precision = 21, scale = 2)
    private BigDecimal balance;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private AccountType type;

    @OneToMany(mappedBy = "from")
    @JsonIgnoreProperties(value = { "type", "from", "to" }, allowSetters = true)
    private Set<Transaction> senders = new HashSet<>();

    @OneToMany(mappedBy = "to")
    @JsonIgnoreProperties(value = { "type", "from", "to" }, allowSetters = true)
    private Set<Transaction> receivers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Accounts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOpeningDate() {
        return this.openingDate;
    }

    public Accounts openingDate(LocalDate openingDate) {
        this.setOpeningDate(openingDate);
        return this;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public Accounts balance(BigDecimal balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Accounts customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Accounts branch(Branch branch) {
        this.setBranch(branch);
        return this;
    }

    public AccountType getType() {
        return this.type;
    }

    public void setType(AccountType accountType) {
        this.type = accountType;
    }

    public Accounts type(AccountType accountType) {
        this.setType(accountType);
        return this;
    }

    public Set<Transaction> getSenders() {
        return this.senders;
    }

    public void setSenders(Set<Transaction> transactions) {
        if (this.senders != null) {
            this.senders.forEach(i -> i.setFrom(null));
        }
        if (transactions != null) {
            transactions.forEach(i -> i.setFrom(this));
        }
        this.senders = transactions;
    }

    public Accounts senders(Set<Transaction> transactions) {
        this.setSenders(transactions);
        return this;
    }

    public Accounts addSender(Transaction transaction) {
        this.senders.add(transaction);
        transaction.setFrom(this);
        return this;
    }

    public Accounts removeSender(Transaction transaction) {
        this.senders.remove(transaction);
        transaction.setFrom(null);
        return this;
    }

    public Set<Transaction> getReceivers() {
        return this.receivers;
    }

    public void setReceivers(Set<Transaction> transactions) {
        if (this.receivers != null) {
            this.receivers.forEach(i -> i.setTo(null));
        }
        if (transactions != null) {
            transactions.forEach(i -> i.setTo(this));
        }
        this.receivers = transactions;
    }

    public Accounts receivers(Set<Transaction> transactions) {
        this.setReceivers(transactions);
        return this;
    }

    public Accounts addReceiver(Transaction transaction) {
        this.receivers.add(transaction);
        transaction.setTo(this);
        return this;
    }

    public Accounts removeReceiver(Transaction transaction) {
        this.receivers.remove(transaction);
        transaction.setTo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accounts)) {
            return false;
        }
        return id != null && id.equals(((Accounts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accounts{" +
            "id=" + getId() +
            ", openingDate='" + getOpeningDate() + "'" +
            ", balance=" + getBalance() +
            "}";
    }
}
