package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.MoneyTransactionValidator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.YearMonth;

@Entity
@DiscriminatorValue("DepositByCreditCard")
public class DepositByCreditCard extends Deposit {
    public YearMonth dueDate;
    public String cardNumber;

    public static DepositByCreditCard create(User user, LocalDate date, double amount, YearMonth dueDate, String cardNumber) {
        MoneyTransactionValidator validator = new MoneyTransactionValidator();
        DepositByCreditCard instance = new DepositByCreditCard();

        validateInstance(user, date, amount, instance);

        instance.dueDate = validator.validateDueDate(dueDate);
        instance.cardNumber = validator.validateCreditCardNumber(cardNumber);

        return instance;
    }

    public YearMonth dueDate() { return dueDate; }

    public String cardNumber() { return cardNumber; }
}
