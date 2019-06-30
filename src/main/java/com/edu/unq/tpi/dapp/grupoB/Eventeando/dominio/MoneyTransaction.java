package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.MoneyTransactionValidator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "transaction_type")
public abstract class MoneyTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    @ManyToOne
    protected User user;

    protected LocalDate date;
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

    public abstract double transactionalValue();
}
