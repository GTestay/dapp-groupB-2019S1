package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.MoneyTransactionValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "transaction_type")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DepositByCash.class, name = "DepositByCash"),
        @JsonSubTypes.Type(value = DepositByCreditCard.class, name = "DepositByCreditCard"),
        @JsonSubTypes.Type(value = Loan.class, name = "Loan"),
        @JsonSubTypes.Type(value = LoanPayment.class, name = "LoanPayment"),
        @JsonSubTypes.Type(value = Extraction.class, name = "Extraction"),
})
public abstract class MoneyTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @JsonProperty
    protected Long id;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonIgnore
    protected User user;

    @JsonProperty
    protected LocalDate date;
    @JsonProperty
    protected double amount;

    protected static MoneyTransaction validateInstance(User user, LocalDate date, double amount, MoneyTransaction instance) {
        MoneyTransactionValidator validator = new MoneyTransactionValidator();

        instance.user = validator.validateUser(user);
        instance.date = validator.validateDate(date);
        instance.amount = validator.validateAmount(amount);

        return instance;
    }

    public User user() { return user; }

    public LocalDate date() { return date; }

    public double amount() { return amount; }

    public Long id() { return id; }

    public abstract double transactionalValue();
}
