package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.MoneyTransactionValidator;

import java.time.LocalDate;
import java.time.YearMonth;

public class DepositByCreditCard extends Deposit {
    public YearMonth dueDate;
    public Long cardNumber;

    public static DepositByCreditCard create(User user, LocalDate date, double amount, YearMonth dueDate, Long cardNumber) {
        DepositByCreditCard instance = new DepositByCreditCard();

        return validateInstance(user, date, amount, dueDate, cardNumber, instance);
    }

    protected static DepositByCreditCard validateInstance(User user, LocalDate date, double amount, YearMonth dueDate, Long cardNumber, DepositByCreditCard instance) {
        makeValidations(user, date, amount, dueDate, cardNumber, instance);

        return instance;
    }

    protected static void makeValidations(User user, LocalDate date, double amount, YearMonth dueDate, Long cardNumber, DepositByCreditCard instance) {
        MoneyTransactionValidator validator = new MoneyTransactionValidator();

        instance.user = validator.validateUser(user);
        instance.date = validator.validateDate(date);
        instance.amount = validator.validateAmount(amount);
        instance.dueDate = validator.validateDueDate(dueDate);
        instance.cardNumber = validator.validateCreditCardNumber(cardNumber);
    }

    public YearMonth dueDate() { return dueDate; }

    public Long cardNumber() { return cardNumber; }
}
