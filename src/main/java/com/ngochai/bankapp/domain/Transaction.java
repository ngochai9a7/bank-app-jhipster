package com.ngochai.bankapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "datetime")
    private LocalDate datetime;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    private TransactionType type;

    @ManyToOne
    @JsonIgnoreProperties(value = { "customer", "branch", "type", "senders", "receivers" }, allowSetters = true)
    private Accounts from;

    @ManyToOne
    @JsonIgnoreProperties(value = { "customer", "branch", "type", "senders", "receivers" }, allowSetters = true)
    private Accounts to;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatetime() {
        return this.datetime;
    }

    public Transaction datetime(LocalDate datetime) {
        this.setDatetime(datetime);
        return this;
    }

    public void setDatetime(LocalDate datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Transaction amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return this.type;
    }

    public void setType(TransactionType transactionType) {
        this.type = transactionType;
    }

    public Transaction type(TransactionType transactionType) {
        this.setType(transactionType);
        return this;
    }

    public Accounts getFrom() {
        return this.from;
    }

    public void setFrom(Accounts accounts) {
        this.from = accounts;
    }

    public Transaction from(Accounts accounts) {
        this.setFrom(accounts);
        return this;
    }

    public Accounts getTo() {
        return this.to;
    }

    public void setTo(Accounts accounts) {
        this.to = accounts;
    }

    public Transaction to(Accounts accounts) {
        this.setTo(accounts);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", datetime='" + getDatetime() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
